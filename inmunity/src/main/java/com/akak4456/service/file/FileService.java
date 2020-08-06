package com.akak4456.service.file;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String saveFile(MultipartFile uploadFile) throws IllegalStateException, IOException;
	//return created file path
	public byte[] getFile(String year,String month, String date, String name) throws IOException;
}
