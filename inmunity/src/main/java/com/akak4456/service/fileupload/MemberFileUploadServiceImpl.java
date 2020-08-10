package com.akak4456.service.fileupload;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akak4456.domain.fileupload.MemberFileUpload;
import com.akak4456.persistent.fileupload.MemberFileUploadRepository;
import com.akak4456.persistent.member.MemberRepository;

@Service
public class MemberFileUploadServiceImpl implements MemberFileUploadService {
	@Autowired
	private MemberFileUploadRepository repo;
	@Autowired
	private MemberRepository memberRepo;
	
	@Transactional
	@Override
	public void putUserProfileInfoToDB(String uploadPath, String uploadFileName, String useremail) {
		// TODO Auto-generated method stub
		repo.deleteByUseremail(useremail);
		//이메일과 관련된 모든 정보를 지운 뒤에
		
		MemberFileUpload upload = new MemberFileUpload();
		upload.setUploadPath(uploadPath);
		upload.setUploadFileName(uploadFileName);
		upload.setMember(memberRepo.findById(useremail).get());
		
		repo.save(upload);
		//새로운 프로필 정보 저장
	}
}
