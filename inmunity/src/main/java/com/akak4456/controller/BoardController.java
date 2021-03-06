package com.akak4456.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.reply.Reply;
import com.akak4456.exception.RecommendOrOppositeAlreadyExist;
import com.akak4456.service.board.BoardService;
import com.akak4456.vo.PageMaker;
import com.akak4456.vo.PageVO;
import com.akak4456.vo.RecommendOrOppositeVO;

import lombok.extern.java.Log;
@Log
public abstract class BoardController <B extends Board>{
	@Autowired
	protected BoardService<B> boardService;
	
	protected abstract RecommendOrOpposite makeOneRecommendOrOppositeEntity(Long bno,String useremail,boolean isRecommend);
	
	@GetMapping("/boards")
	public String getList(Model model,PageVO pageVO){
		Pageable pageable = pageVO.makePageble(0, "bno");
		PageMaker<B> pageMaker = new PageMaker<B>(boardService.getListWithPaging(pageVO.getType(), pageVO.getKeyword(), pageable));
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("result",pageMaker);
		return "/board/list";
	}
	
	@GetMapping("/boards/{bno}")
	public String getOne(Model model,@PathVariable("bno") Long bno,PageVO pageVO){
		Board getBoard = boardService.getOne(bno);
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("result",getBoard);
		return "/board/one";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{bno}")
	public String modify(Model model,@PathVariable("bno") Long bno,PageVO pageVO) {
		Board getBoard = boardService.getOne(bno);
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("result",getBoard);
		return "/board/modify";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/add")
	public String add(Model model,PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
		return "/board/add";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/boards")
	public ResponseEntity<Void> addOne(@RequestBody B board){
		boardService.addBoard(board);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PreAuthorize("isAuthenticated() and #board.member.useremail == authentication.principal.member.useremail")
	@PutMapping("/boards/{bno}")
	public ResponseEntity<Void> modifyOne(@RequestBody B board,@PathVariable("bno") Long bno){
		boardService.modify(bno,board);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated() and #board.member.useremail == authentication.principal.member.useremail")
	@DeleteMapping("/boards/{bno}")
	public ResponseEntity<Void> deleteOne(@PathVariable("bno") Long bno,@RequestBody B board){
		boardService.delete(bno);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PatchMapping("/uprecommend/{bno}")
	public ResponseEntity<String> upRecommend(@PathVariable("bno")Long bno,@RequestBody RecommendOrOppositeVO recommendOrOppositeVO){
		RecommendOrOpposite entity = makeOneRecommendOrOppositeEntity(recommendOrOppositeVO.getBno(),recommendOrOppositeVO.getUseremail(),true);
		try {
			boardService.upRecommendcnt(entity);
			return new ResponseEntity<>("ok",HttpStatus.OK);
		}catch(RecommendOrOppositeAlreadyExist a) {
			return new ResponseEntity<>("alreadyexist",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>("etc",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("isAuthenticated()")
	@PatchMapping("/upopposite/{bno}")
	public ResponseEntity<String> upOpposite(@PathVariable("bno")Long bno,@RequestBody RecommendOrOppositeVO recommendOrOppositeVO){
		//
		RecommendOrOpposite entity = makeOneRecommendOrOppositeEntity(recommendOrOppositeVO.getBno(),recommendOrOppositeVO.getUseremail(),false);
		try {
			boardService.upOppositecnt(entity);
			return new ResponseEntity<>("ok",HttpStatus.OK);
		}catch(RecommendOrOppositeAlreadyExist a) {
			return new ResponseEntity<>("alreadyexist",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>("etc",HttpStatus.BAD_REQUEST);
		}
	}
}
