package com.akak4456.service.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import com.akak4456.domain.fileupload.FileUpload;
import com.akak4456.persistent.fileupload.FileUploadRepository;

import lombok.extern.java.Log;
@Log
public abstract class DeleteService<T extends FileUpload> {
	/*
	 서버가 돌아가는 중에 운영체제에서 시간을 바꾸면 cron이 제대로 작동안함
	 테스트 하기 위해서는 서버를 돌리기 전에 시간을 수정해야 함
	 */
	@Value("${spring.servlet.multipart.location}")
	private String rootPath;
	
	@Autowired
	private FileUploadRepository<T> repo;
	
	@Scheduled(cron = "0 5 0 * * *")
	public void deleteNotMatchFiles() throws IOException {
		// TODO Auto-generated method stub
		//System.out.println("time to delete " + new Date());
		log.info(new Date()+"");
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -1);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		Path folderPath = Paths.get(rootPath, String.valueOf(year),String.valueOf(month),String.valueOf(date));
		if(!Files.exists(folderPath)) {
			//folder가 없으면 전날 생성된 파일이 없음을 의미
			//반환한다
			return;
		}
		File file = new File(folderPath.toString());
		File[] files = file.listFiles();
		Set<String> fileNameSet = new HashSet<>();
		for(int i=0;i<files.length;i++) {
			fileNameSet.add(files[i].getName());
		}
		/*
		 * Iterator<String> it = fileNameSet.iterator(); while(it.hasNext()) {
		 * log.info(it.next()); }
		 */
		
		//해당 디렉토리의 file들을 모두 조사한다
		String uploadPath = String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(date);
		Set<String> dbSet = new HashSet<>();
		for(T dbrecord: repo.findByUploadPath(uploadPath)) {
			dbSet.add(dbrecord.getUploadFileName());
		}
		//dbset에서 어제 파일 리스트들을 모두 꺼내온다.
		fileNameSet.removeAll(dbSet);
		Iterator<String> it = fileNameSet.iterator();
		while(it.hasNext()) {
			Path filePath = Paths.get(folderPath.toString(),it.next());
			Files.delete(filePath);
		}
	}
}
