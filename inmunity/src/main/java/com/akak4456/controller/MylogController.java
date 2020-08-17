package com.akak4456.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.reply.Reply;
import com.akak4456.service.BoardService;
import com.akak4456.service.ReplyService;
import com.akak4456.vo.PageMaker;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/mylog/**")
public class MylogController {
	@Autowired
	private BoardService boardMyLogService;
	@Autowired
	private ReplyService replyMyLogService;
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/showmyboard")
	public void showmyboard(Model model, PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myboards")
	@ResponseBody
	public ResponseEntity<PageMaker<Board>> myboards(PageVO pageVO){
		Pageable pageable = pageVO.makePageble(0, "bno");
		PageMaker<Board> result = new PageMaker<Board>(boardMyLogService.getListWithUseremail(pageVO.getKeyword(), pageable));
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/showmyreply")
	public void showmyreply(Model model, PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myreply")
	@ResponseBody
	public ResponseEntity<PageMaker<Reply>> myreply(PageVO pageVO){
		Pageable pageable = pageVO.makePageble(0, "rno");
		PageMaker<Reply> result = new PageMaker<Reply>(replyMyLogService.getListWithUseremail(pageVO.getKeyword(), pageable));
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/showmyscrap")
	public void showmyscrap(Model model, PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
}
