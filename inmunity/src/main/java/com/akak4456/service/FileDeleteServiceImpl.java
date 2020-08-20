package com.akak4456.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.akak4456.persistent.FileUploadRepository;

import lombok.extern.java.Log;

@Service
@Log
public class FileDeleteServiceImpl implements FileDeleteService {
	@Autowired
	private FileUploadRepository repo;
	@Value("${spring.servlet.multipart.location}")
	private String rootPath;
	@Override
	public void deleteUnnecessaryFilePeriodically() throws IOException {
		// TODO Auto-generated method stub
		LocalDateTime onedayBefore = LocalDateTime.now().minusDays(1L);
		int year = onedayBefore.getYear();
		int month = onedayBefore.getMonthValue();
		int day = onedayBefore.getDayOfMonth();
		String fullDirectory = rootPath + "/"+year+"/"+month+"/"+day;
		File dir = new File(fullDirectory);
		if(!dir.exists())
			throw new IOException();
		Set<String> fileSet = new HashSet<String>(Arrays.asList(dir.list()));
		Set<String> dbSet = repo.findByTime(onedayBefore);
		fileSet.removeAll(dbSet);
		for(String fileName:fileSet) {
			File deletedFile = new File(fullDirectory+"/"+fileName);
			if(!deletedFile.exists()) {
				throw new IOException();
			}
			deletedFile.delete();
		}
	}

}
