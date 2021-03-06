package com.akak4456.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.reply.Reply;
import com.akak4456.service.reply.ReplyService;
import com.akak4456.vo.PageMaker;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;
public abstract class ReplyController<B extends Board, R extends Reply> {
	
	@Autowired
	protected ReplyService<R> replyService;
	
	protected abstract B makeOneEmptyBoardByBno(Long bno);
	@GetMapping("/reply/{bno}")
	public ResponseEntity<PageMaker<R>> getList(@PathVariable("bno")Long bno,PageVO pageVO){
		Pageable pageable = null;
		pageable = pageVO.makePageble(1, "path");
		PageMaker<R> pageMaker = new PageMaker<R>(replyService.getListWithPaging(bno, pageable));
		
		return new ResponseEntity<>(pageMaker,HttpStatus.OK);
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/reply/{bno}")
	public ResponseEntity<String> addOne(@PathVariable("bno")Long bno,@RequestBody R reply){
		B board = makeOneEmptyBoardByBno(bno);
		reply.setBoard(board);
		replyService.addReply(reply);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated() and #reply.member.useremail == authentication.principal.member.useremail")
	@PutMapping("/reply/{bno}/{rno}")
	public ResponseEntity<String> modifyOne(@PathVariable("bno")Long bno,@PathVariable("rno")Long rno,@RequestBody R reply){
		B board = makeOneEmptyBoardByBno(bno);
		reply.setBoard(board);
		replyService.updateReply(rno, reply);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated() and #reply.member.useremail == authentication.principal.member.useremail")
	@DeleteMapping("/reply/{bno}/{rno}")
	public ResponseEntity<String> deleteOne(@PathVariable("bno")Long bno,@PathVariable("rno")Long rno,@RequestBody R reply){
		replyService.deleteReply(rno);
		//나중에 확장성을 위해서 bno도 받기로 설정함
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
