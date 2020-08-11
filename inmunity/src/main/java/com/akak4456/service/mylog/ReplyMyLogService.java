package com.akak4456.service.mylog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akak4456.domain.reply.Reply;
import com.akak4456.persistent.boardandreplygeneral.ReplyGeneralRepository;

@Service
public class ReplyMyLogService {
	@Autowired
	private ReplyGeneralRepository repo;
	
	public Page<Reply> getListWithUseremail(String useremail,Pageable pageable){
		return repo.findAll(repo.makePredicate(useremail), pageable);
	}

}
