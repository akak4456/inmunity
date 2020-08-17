package com.akak4456.persistent;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.fileupload.FileUpload;
@Repository
public interface FileUploadRepository extends CrudRepository<FileUpload, Long> {
	@Modifying
	@Transactional
	@Query("delete from MemberFileUpload m where m.member.useremail=:useremail")
	public void deleteByUseremail(String useremail);
	List<FileUpload> findByUploadPath(String uploadPath);
}
