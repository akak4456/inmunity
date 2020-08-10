package com.akak4456.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.akak4456.service.file.FileService;
import com.akak4456.vo.UrlVO;

import lombok.extern.java.Log;

@RestController
@Log
public class FileUploadController {
	@Autowired
	private FileService fileService;
	@PostMapping("/fileupload")
	public ResponseEntity<UrlVO> fileUpload(@RequestParam("upload")MultipartFile uploadFile){
		try {
			String createdFilePath = fileService.saveFile(uploadFile);
			UrlVO urlVO = new UrlVO();
			urlVO.setUrl("/fileget/"+createdFilePath);
			return new ResponseEntity<UrlVO>(urlVO,HttpStatus.OK);
		}catch(IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(IllegalStateException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/fileget/{year}/{month}/{date}/{name}")
	public ResponseEntity<byte[]> getOne(
			@PathVariable("year") String year,
			@PathVariable("month") String month,
			@PathVariable("date") String date,
			@PathVariable("name") String name){
		try {
			byte[] bytes = fileService.getFile(year,month,date,name);
			return new ResponseEntity<byte[]>(bytes,HttpStatus.OK);
		}catch(IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/profileupload")
	public ResponseEntity<String> changeProfilePost(@RequestParam("uploadfile") MultipartFile uploadfile){
		log.info(uploadfile.toString());
		//session.invalidate();
		try {
			String createdFilePath = fileService.uploadProfile(uploadfile);
			return new ResponseEntity<>("/fileget/"+createdFilePath,HttpStatus.OK);
		}catch(IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(IllegalStateException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
