package com.akak4456.service.reply;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.akak4456.domain.reply.Reply;
import com.akak4456.persistent.reply.ReplyRepository;

public abstract class ReplyService<R extends Reply> {
	@Autowired
	private ReplyRepository<R> replyRepo;
	@Transactional
	public void addReply(R reply) {
		// TODO Auto-generated method stub
		replyRepo.save(reply);
		reply.setPath(reply.generatePathFromRnoAndParentRno());
		replyRepo.save(reply);
	}
	@Transactional
	public void updateReply(Long rno, Reply reply) {
		// TODO Auto-generated method stub
		R newReply = replyRepo.findById(rno).get();
		newReply.setReply(reply.getReply());
		replyRepo.save(newReply);
	}
	@Transactional
	public Page<R> getListWithPaging(Long bno, Pageable pageable) {
		// TODO Auto-generated method stub
		return replyRepo.findAll(replyRepo.makePredicate(bno), pageable);
	}
	@Transactional
	public void deleteReply(Long rno) {
		// TODO Auto-generated method stub
		R deleteReply = replyRepo.findById(rno).get();
		
		deleteReply.setMember(null);
		deleteReply.setReply("삭제된 내용");
		deleteReply.setIsdelete('Y');
		replyRepo.save(deleteReply);
	}
}
