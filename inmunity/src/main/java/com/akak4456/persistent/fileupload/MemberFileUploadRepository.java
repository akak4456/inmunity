package com.akak4456.persistent.fileupload;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.fileupload.MemberFileUpload;

@Repository
public interface MemberFileUploadRepository extends FileUploadRepository<MemberFileUpload> {
	@Modifying
	@Query("delete from MemberFileUpload m where m.member.useremail=:useremail")
	public void deleteByUseremail(String useremail);
}
