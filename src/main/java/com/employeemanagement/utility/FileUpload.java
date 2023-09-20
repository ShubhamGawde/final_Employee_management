package com.employeemanagement.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUpload {

	

	public boolean fileUpload(MultipartFile file) throws IOException {
		
		String directory = "static/img/";

		String fileName = file.getOriginalFilename();

		String filePath = directory + fileName;

		ClassPathResource resource = new ClassPathResource(filePath);

		Files.copy(file.getInputStream(), Path.of(resource.getURI()), StandardCopyOption.REPLACE_EXISTING);

		return true;
	}

}
