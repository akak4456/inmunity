package com.akak4456.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileSystemManipulateService {
	public String saveFile(MultipartFile uploadFile) throws IllegalStateException, IOException;
	//return created file path
	public byte[] getFile(String year,String month, String date, String name) throws IOException;
	
	public String uploadProfile(MultipartFile uploadFile) throws IllegalStateException,IOException;
}
