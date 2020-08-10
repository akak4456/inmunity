package com.akak4456.service.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@Service
@Log
public class FileServiceImpl implements FileService {
	//computer 파일 직접 조작
	@Value("${spring.servlet.multipart.location}")
	private String rootPath;
	
	@Value("${thumbnailWidth}")
	private int thumbnailWidth;
	
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

	@Override
	public String uploadProfile(MultipartFile uploadFile) throws IllegalStateException, IOException {
		// TODO Auto-generated method stub
		Path path = getPath(Paths.get(uploadFile.getOriginalFilename()).getFileName().toString());
		if(Files.notExists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}
		FileOutputStream fos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = createThumbnail(uploadFile, thumbnailWidth);
			fos = new FileOutputStream(path.toFile());
			baos.writeTo(fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path.subpath(path.getNameCount()-4, path.getNameCount()).toString().replace("\\", "/");
	}
	
	private ByteArrayOutputStream createThumbnail(MultipartFile orginalFile, Integer width) throws IOException {
		ByteArrayOutputStream thumbOutput = null;
		BufferedImage thumbImg = null;
		BufferedImage img = null;
		try {
			thumbOutput = new ByteArrayOutputStream();
			img = ImageIO.read(orginalFile.getInputStream());
			thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, Scalr.OP_ANTIALIAS);
			ImageIO.write(thumbImg, orginalFile.getContentType().split("/")[1], thumbOutput);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
		return thumbOutput;
	}
}
