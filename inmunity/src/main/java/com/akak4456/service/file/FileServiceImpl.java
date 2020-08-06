package com.akak4456.service.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@Service
@Log
public class FileServiceImpl implements FileService {
	//computer 파일 직접 조작
	@Value("${spring.servlet.multipart.location}")
	private String rootPath;
	
	//나중에 fileupload db를 조작하는 서비스를 autowired 할것
	@Override
	public String saveFile(MultipartFile uploadFile) throws IllegalStateException, IOException {
		Path path = getPath(uploadFile.getOriginalFilename());
		if(Files.notExists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}
		uploadFile.transferTo(path);
		return path.subpath(path.getNameCount()-4, path.getNameCount()).toString().replace("\\", "/");
	}
	
	private Path getPath(String fileName) {
		UUID uuid = UUID.randomUUID();
		fileName = uuid.toString() + "_"+fileName;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int date = now.get(Calendar.DATE);
		return Paths.get(rootPath, String.valueOf(year),String.valueOf(month),String.valueOf(date),fileName);
	}

	@Override
	public byte[] getFile(String year,String month, String date, String name) throws IOException {
		// TODO Auto-generated method stub
		Path path = Paths.get(rootPath,year,month,date,name);
		return Files.readAllBytes(path);
	}
}
