package com.akak4456.service.del;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.fileupload.FileUpload;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.board.BoardRepository;
import com.akak4456.persistent.fileupload.FileUploadRepository;
import com.akak4456.service.file.DeleteService;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public abstract class DeleteServiceTest <B extends Board, F extends FileUpload> {
	/*
	 * 테스트를 하기에 앞서
	 * 윈도우 시간을
	 * 오전 12시 03~04분으로 맞추기
	 */
	@Value("${spring.servlet.multipart.location}")
	protected String rootPath;
	
	@Autowired
	private DeleteService<F> deleteService;
	@Autowired
	private FileUploadRepository<F> fileUploadRepo;
	@Autowired
	private BoardRepository<B> boardRepo;
	@Test
	public void deleteNotMatchFilesTest() throws IOException {
		boardRepo.deleteAll();
		int fakecnt = 5;
		int actualcnt = 3;
		int boardcnt = 3;
		boardRepo.saveAll(prepareData(fakecnt,actualcnt,boardcnt));
		assertEquals(fileUploadRepo.count(),actualcnt*boardcnt);
		assertEquals(getFileCount(),fakecnt+actualcnt*boardcnt);
		deleteService.deleteNotMatchFiles();
		assertEquals(getFileCount(),actualcnt*boardcnt);
	}
	
	public abstract F makeOne(String uploadPath,String uploadFileName);
	public abstract B makeOneBoard(String title,String content, List<F> uploads);
	private List<B> prepareData(int fakecnt,int actualcnt,int boardcnt) throws IllegalStateException, IOException{
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -1);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		
		byte[] content = {1,2};
		Path parentPath = Paths.get(rootPath, String.valueOf(year),String.valueOf(month),String.valueOf(date));
		if(Files.exists(parentPath)) {
			//모든 파일 삭제
			File file = new File(parentPath.toString());
			File[] files = file.listFiles();
			
			if(files.length > 0) {
				for(int i=0;i<files.length;i++) {
					files[i].delete();
				}
			}
		}
		if(Files.notExists(parentPath)) {
			Files.createDirectories(parentPath);
		}
		for(int i=0;i<fakecnt;i++) {
			String fileName = "fake"+i+".png";
			MockMultipartFile multipartFile = new MockMultipartFile(fileName,content);
			Path path = Paths.get(parentPath.toString(),fileName);
			
			multipartFile.transferTo(path);
		}
		int imagecnt = 0;
		List<B> ret = new ArrayList<>();
		for(int boardno = 0;boardno<boardcnt;boardno++) {
			List<F> uploads = new ArrayList<>();
			for(int i=0;i<actualcnt;i++) {
				String uploadPath = String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(date);
				String fileName = "actual"+imagecnt+".png";
				imagecnt++;
				F one = makeOne(uploadPath,fileName);
				uploads.add(one);
				MockMultipartFile multipartFile = new MockMultipartFile(fileName,content);
				Path path = Paths.get(parentPath.toString(),fileName);
				multipartFile.transferTo(path);
			}
			ret.add(makeOneBoard("title","content",uploads));
		}
		
		return ret;
	}
	private int getFileCount() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -1);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		Path parentPath = Paths.get(rootPath, String.valueOf(year),String.valueOf(month),String.valueOf(date));
		int ret = -1;
		if(Files.exists(parentPath)) {
			//모든 파일 삭제
			File file = new File(parentPath.toString());
			File[] files = file.listFiles();
			ret = files.length;
		}
		return ret;
	}
}
