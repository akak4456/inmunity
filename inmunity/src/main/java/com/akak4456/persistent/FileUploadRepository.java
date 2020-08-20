package com.akak4456.persistent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.fileupload.FileUpload;
@Repository
public interface FileUploadRepository extends CrudRepository<FileUpload, Long> {
	@Modifying
	@Transactional
	@Query("delete from MemberFileUpload m where m.member.useremail=:useremail")
	public void deleteByUseremail(String useremail);
	
	@Transactional
	@Query("select f.uploadFileName from FileUpload f where f.regdate < :onedaybefore")
	public Set<String> findByTime(@Param("onedaybefore")LocalDateTime onedaybefore);

	List<FileUpload> findByUploadPath(String uploadPath);
}
