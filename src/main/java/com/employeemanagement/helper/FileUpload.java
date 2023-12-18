package com.employeemanagement.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class FileUpload {

	@Value("${project.path}")
	private String path;

	public String uploadImage(MultipartFile file) throws IOException {
		// file name
		String name = file.getOriginalFilename();

		// random name generate file
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

		// full path
		String filePath = path + File.separator + fileName;

		// create new folder if does not exists
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		 Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		String uriString = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/image")
				.queryParam("img", fileName).build().toUriString();
		return uriString;
	}


	public boolean deleteImage(String img) throws IOException {
		String imgPath = path + File.separator + img;
		Path path = Paths.get(imgPath);
		return Files.deleteIfExists(path);
	}

}
