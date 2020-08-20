package com.akak4456.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.fileupload.MemberFileUpload;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.FileUploadRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class FileDeleteServiceTest {
	@Autowired
	private FileUploadRepository repo;
	@Autowired
	private FileDeleteService service;
	@Value("${spring.servlet.multipart.location}")
	private String rootPath;
	
	@Test
	public void deleteTest() {
		/*
		 ../upload/year/month/{currentday-1}폴더에 들어가서
		 거기에 있는 파일 모두 삭제한 뒤에
		 1.png, 2.png .... 8.png를 넣을 것
		 
		 초록 막대가 뜬 뒤에는
		 1.png
		 3.png
		 7.png
		 가 남아있는지 확인
		 */
		repo.deleteAll();
		LocalDateTime onedayBefore = LocalDateTime.now().minusDays(1L);
		int year = onedayBefore.getYear();
		int month = onedayBefore.getMonthValue();
		int day = onedayBefore.getDayOfMonth();
		log.info(year +" " + month +" " +day);
		log.info(rootPath);
		String fullPath = rootPath + "/"+year+"/"+month+"/"+day;
		log.info(fullPath);
		assertEquals(new File(fullPath).list().length,8);
		//8개의 파일을 넣었다고 가정함
		BoardFileUpload upload1 = BoardFileUpload.builder()
									.uploadPath("path")
									.uploadFileName("1.png")
									.build();
		repo.save(upload1);
		upload1.setRegdate(LocalDateTime.now().minusDays(1L).minusMinutes(10));
		repo.save(upload1);
		BoardFileUpload upload2 = BoardFileUpload.builder()
									.uploadPath("path")
									.uploadFileName("3.png")
									.build();
		repo.save(upload2);
		upload2.setRegdate(LocalDateTime.now().minusDays(1L).minusMinutes(10));
		repo.save(upload2);
		MemberFileUpload upload3 = MemberFileUpload.builder()
									.uploadPath("path")
									.uploadFileName("7.png")
									.build();
		repo.save(upload3);
		upload3.setRegdate(LocalDateTime.now().minusDays(1L).minusMinutes(10));
		repo.save(upload3);
		try {
		service.deleteUnnecessaryFilePeriodically();
		}catch(IOException e) {
		
		}
		//3개의 파일만 남기고 모두 삭제한다
		assertEquals(new File(fullPath).list().length,3);
	}
}
