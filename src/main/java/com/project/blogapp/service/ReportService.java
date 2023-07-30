package com.project.blogapp.service;

import com.cloudinary.Cloudinary;
import com.project.blogapp.constant.Constants;
import com.project.blogapp.dto.UserReportDto;
import com.project.blogapp.entity.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableScheduling
public class ReportService {

    private final UserService userService;

    private final Cloudinary cloudinary;

    public ReportService(UserService userService, Cloudinary cloudinary) {
        this.userService = userService;
        this.cloudinary = cloudinary;
    }

    @Scheduled(cron = "@monthly")
    public void exportReport() throws IOException, JRException {
        List<User> users = userService.findAllUsers();
        List<UserReportDto> userReportDtos = users.stream()
                .map(user -> new UserReportDto(user.getId(), user.getDisplayName(), user.getUsername()))
                .toList();
        JasperPrint jasperPrint = generateReport(userReportDtos);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        cloudinary.uploader().upload(outputStream.toByteArray(), Constants.CLOUDINARY_REPORT_PARAMS);
    }

    private JasperPrint generateReport(List<UserReportDto> users) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile("classpath:templates/users.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Celal Gündoğdu");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }
}
