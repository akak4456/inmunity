package com.akak4456.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.akak4456.domain.scrap.Scrap;
import com.akak4456.exception.ScrapAlreadyExist;

public interface ScrapService {
	public void addScrap(String useremail,Long bno) throws ScrapAlreadyExist;
	
	public Page<Scrap> getListWithUseremail(String useremail,Pageable pageable);	
}
