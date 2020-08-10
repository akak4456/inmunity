package com.akak4456.service.mylog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akak4456.domain.board.Board;
import com.akak4456.persistent.mylog.BoardMyLogRepository;

@Service
public class BoardMyLogService {
	@Autowired
	private BoardMyLogRepository repo;
	
	public List<Board> getListWithUseremail(String useremail){
		return repo.findAllByUseremail(useremail);
	}
}
