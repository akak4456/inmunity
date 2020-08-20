package com.akak4456.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.board.NotifyBoard;
import com.akak4456.service.board.BoardService;
import com.akak4456.vo.PageMaker;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/notifyboard/**")
public class NotifyBoardController {
	@Autowired
	private BoardService<NotifyBoard> boardService;
	@GetMapping("/boards")
	public String getList(Model model,PageVO pageVO){
		Pageable pageable = pageVO.makePageble(0, "bno");
		PageMaker<NotifyBoard> pageMaker = new PageMaker<NotifyBoard>(boardService.getListWithPaging(pageVO.getType(), pageVO.getKeyword(), pageable));
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("result",pageMaker);
		return "/notify/list";
	}
	@GetMapping("/boards/{bno}")
	public String getOne(Model model,@PathVariable("bno") Long bno,PageVO pageVO){
		Board getBoard = boardService.getOne(bno);
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("result",getBoard);
		return "/notify/one";
	}
}
