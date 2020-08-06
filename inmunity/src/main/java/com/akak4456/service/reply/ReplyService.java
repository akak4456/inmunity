package com.akak4456.service.reply;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.reply.Reply;
import com.akak4456.persistent.reply.ReplyRepository;

public class ReplyService <B extends Board, R extends Reply> {
	@Autowired
	private ReplyRepository<R> replyRepo;
	
	@Transactional
	public void addReply(R reply) {
		replyRepo.save(reply);
		reply.setPath(reply.generatePathFromRnoAndParentRno());
		replyRepo.save(reply);
	}
	
	@Transactional
	public void updateReply(Long rno,R reply) {
		// TODO Auto-generated method stub
		R newReply = replyRepo.findById(rno).get();
		newReply.setReply(reply.getReply());
		replyRepo.save(newReply);
	}
	@Transactional(readOnly=true)
	public Page<R> getListWithPaging(Long bno,Pageable pageable) {
		// TODO Auto-generated method stub
		return replyRepo.findAll(replyRepo.makePredicate(bno), pageable);
		//return replyRepo.findAll(replyRepo.makePredicate(),pageable);
	}
	@Transactional
	public void deleteReply(Long rno) {
		R deleteReply = replyRepo.findById(rno).get();
		
		deleteReply.setReplyer("삭제된 작성자");
		deleteReply.setReply("삭제된 내용");
		deleteReply.setIsdelete('Y');
		replyRepo.save(deleteReply);
	}
}
