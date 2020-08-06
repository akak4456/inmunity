package com.akak4456.persistent.fileupload;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.akak4456.domain.fileupload.FileUpload;
@NoRepositoryBean
public interface FileUploadRepository<T extends FileUpload> extends CrudRepository<T, Long> {
	List<T> findByUploadPath(String uploadPath);
}
