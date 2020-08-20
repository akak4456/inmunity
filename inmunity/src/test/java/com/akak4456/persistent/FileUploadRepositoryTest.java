package com.akak4456.persistent;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.fileupload.FileUpload;
import com.akak4456.domain.fileupload.MemberFileUpload;
import com.akak4456.inmunity.InmunityApplication;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class FileUploadRepositoryTest {
	@Autowired
	private FileUploadRepository repo;
	
	@Test
	public void onedaybeforeDeleteTest() {
		repo.deleteAll();
		BoardFileUpload upload1 = BoardFileUpload.builder()
									.uploadPath("path")
									.uploadFileName("1")
									.build();
		repo.save(upload1);
		upload1.setRegdate(LocalDateTime.now().minusDays(1L).minusMinutes(15));
		repo.save(upload1);
		BoardFileUpload upload2 = BoardFileUpload.builder()
									.uploadPath("path")
									.uploadFileName("2")
									.build();
		repo.save(upload2);
		upload2.setRegdate(LocalDateTime.now().minusDays(1L).minusMinutes(20));
		repo.save(upload2);
		BoardFileUpload upload3 = BoardFileUpload.builder()
									.uploadPath("path")
									.uploadFileName("3")
									.build();
		repo.save(upload3);
		upload3.setRegdate(LocalDateTime.now().minusDays(1L).minusMinutes(30));
		repo.save(upload3);
		BoardFileUpload upload4 = BoardFileUpload.builder()
									.uploadPath("path")
									.uploadFileName("4")
									.build();
		repo.save(upload4);
		upload4.setRegdate(LocalDateTime.now().minusDays(1L).plusMinutes(20));
		repo.save(upload4);
		MemberFileUpload upload5 = MemberFileUpload.builder()
									.uploadPath("path")
									.uploadFileName("5")
									.build();
		repo.save(upload5);
		upload5.setRegdate(LocalDateTime.now().minusDays(1L).plusMinutes(20));
		repo.save(upload5);
		MemberFileUpload upload6 = MemberFileUpload.builder()
									.uploadPath("path")
									.uploadFileName("6")
									.build();
		repo.save(upload6);
		upload6.setRegdate(LocalDateTime.now().minusDays(1L).minusMinutes(20));
		repo.save(upload6);
		log.info(LocalDateTime.now().minusDays(1L).minusMinutes(20)+"");
		Set<String> fileUpload = repo.findByTime(LocalDateTime.now().minusDays(1L));
		assertEquals(fileUpload.size(),4);
	}
}
