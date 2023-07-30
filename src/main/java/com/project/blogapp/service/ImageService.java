package com.project.blogapp.service;

import com.cloudinary.Cloudinary;
import com.project.blogapp.constant.Constants;
import com.project.blogapp.constant.Messages;
import com.project.blogapp.dto.response.ImageResponse;
import com.project.blogapp.dto.response.UploadImageResponse;
import com.project.blogapp.entity.Image;
import com.project.blogapp.exception.EntityNotFoundException;
import com.project.blogapp.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    public ImageService(Cloudinary cloudinary, ImageRepository imageRepository) {
        this.cloudinary = cloudinary;
        this.imageRepository = imageRepository;
    }

    public ImageResponse getImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException(Messages.Image.NOT_FOUND));
        ImageResponse response = ImageResponse.convert(image);
        return response;
    }

    public UploadImageResponse uploadImage(MultipartFile file) {
        Image createdImage = saveImage(file);
        UploadImageResponse response = UploadImageResponse.convert(createdImage);
        return response;
    }

    public Image saveImage(MultipartFile file) {
        String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename()).toLowerCase();
        if (!checkIfValidExtension(filenameExtension)) {
            throw new RuntimeException(Messages.Image.INVALID_EXTENSION);
        }
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(file.getBytes(), Constants.CLOUDINARY_USER_PARAMS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image image = new Image(0L, uploadResult.get("url").toString(), uploadResult.get("public_id").toString());
        image = imageRepository.save(image);
        return image;
    }

    protected void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, Constants.CLOUDINARY_USER_PARAMS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageRepository.deleteByPublicId(publicId);
    }

    public Image findDefaultUserImage() {
        return imageRepository.findByUrl(Constants.DEFAULT_USER_IMAGE_URL)
                .orElseThrow(() -> new EntityNotFoundException(Messages.Image.NOT_FOUND));
    }

    protected boolean checkIfValidExtension(String extension) {
        if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
            return true;
        }
        return false;
    }
}
