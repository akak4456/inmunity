package com.akak4456.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akak4456.domain.fileupload.MemberFileUpload;
import com.akak4456.persistent.FileUploadRepository;
import com.akak4456.persistent.MemberRepository;

@Service
public class FileDBServiceImpl implements FileDBService {

	@Autowired
	private FileUploadRepository repo;
	@Autowired
	private MemberRepository memberRepo;

	@Transactional
	@Override
	public void putUserProfileInfoToDB(String uploadPath, String uploadFileName, String useremail) {
		// TODO Auto-generated method stub
		repo.deleteByUseremail(useremail);
		// 이메일과 관련된 모든 정보를 지운 뒤에

		MemberFileUpload upload = MemberFileUpload.builder().uploadFileName(uploadFileName).uploadPath(uploadPath).member(memberRepo.findById(useremail).get()).build();

		repo.save(upload);
		// 새로운 프로필 정보 저장
	}

}
