package com.project.blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Locale;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BlogAppApplication {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		SpringApplication.run(BlogAppApplication.class, args);
/*
		Cloudinary cloudinary = (Cloudinary) context.getBean("cloudinary");
		File file = new File("wp1.jpg");
		Map params = ObjectUtils.asMap(
				"folder", "socialapp/users"
		);
		try {
			Map uploadResult = cloudinary.uploader().upload(file, params);
			System.out.println(uploadResult.get("url"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
 */
	}

}
