package com.akak4456.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akak4456.domain.board.NotifyBoard;
import com.akak4456.service.board.BoardService;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;
@Controller
@Log
@RequestMapping("/admin/**")
public class AdminController {
	@Autowired
	private BoardService<NotifyBoard> boardService;
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/main")
	public String main(Model model,PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
		return "/admin/main";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/add")
	public String add(Model model,PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
		return "/admin/add";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add")
	public ResponseEntity<Void> addOne(@RequestBody NotifyBoard board){
		boardService.addBoard(board);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
