package com.akak4456.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akak4456.domain.scrap.Scrap;
import com.akak4456.service.scrap.ScrapService;
import com.akak4456.vo.PageMaker;
import com.akak4456.vo.PageVO;
import com.akak4456.vo.ScrapVO;

@RestController
public class ScrapController {
	@Autowired
	private ScrapService scrapService;
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/scrap/add")
	public ResponseEntity<String> add(@RequestBody ScrapVO scrapVO){
		try {
			scrapService.addScrap(scrapVO.getUseremail(), scrapVO.getBno());
		}catch(DataIntegrityViolationException e) {
			return new ResponseEntity<>("already",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/scrap/list")
	public ResponseEntity<PageMaker<Scrap>> list(PageVO pageVO){
		Pageable pageable = pageVO.makePageble(0, "sno");
		PageMaker<Scrap> result = new PageMaker<Scrap>(scrapService.getListWithUseremail(pageVO.getKeyword(), pageable));
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
}
