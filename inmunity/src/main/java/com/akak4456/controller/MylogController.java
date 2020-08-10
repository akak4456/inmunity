package com.akak4456.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akak4456.domain.board.Board;
import com.akak4456.service.mylog.BoardMyLogService;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/mylog/**")
public class MylogController {
	@Autowired
	private BoardMyLogService boardMyLogService;
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/showmyboard")
	public void showmyboard(Model model, PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myboards")
	@ResponseBody
	public ResponseEntity<List<Board>> myboards(PageVO pageVO){
		return new ResponseEntity<List<Board>>(boardMyLogService.getListWithUseremail(pageVO.getKeyword()),HttpStatus.OK);
	}
}
