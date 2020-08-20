package com.akak4456.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.board.GraduateBoard;
import com.akak4456.domain.board.JobBoard;
import com.akak4456.domain.board.NewbieBoard;
import com.akak4456.service.board.BoardService;
import com.akak4456.vo.PageMaker;
import com.akak4456.vo.PageVO;

@Controller
public class IndexController {
	@Autowired
	private BoardService<FreeBoard> freeBoardService;
	@Autowired
	private BoardService<GraduateBoard> graduateBoardService;
	@Autowired
	private BoardService<NewbieBoard> newbieBoardService;
	@Autowired
	private BoardService<JobBoard> jobBoardService;
	@GetMapping("/")
	public String index(Model model,PageVO pageVO) {
		//get index page
		Pageable pageable = new PageVO().makePageble(0, "bno");
		PageMaker<FreeBoard> freeboard = new PageMaker<>(freeBoardService.getListWithPaging(null,null, pageable));
		PageMaker<GraduateBoard> graduateboard = new PageMaker<>(graduateBoardService.getListWithPaging(null,null, pageable));
		PageMaker<NewbieBoard> newbieboard = new PageMaker<>(newbieBoardService.getListWithPaging(null,null, pageable));
		PageMaker<JobBoard> jobboard  = new PageMaker<>(jobBoardService.getListWithPaging(null,null, pageable));
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("freeboard",freeboard);
		model.addAttribute("graduateboard",graduateboard);
		model.addAttribute("newbieboard",newbieboard);
		model.addAttribute("jobboard",jobboard);
	    return "/index";
	}
}
