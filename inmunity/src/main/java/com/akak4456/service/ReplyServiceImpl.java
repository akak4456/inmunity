package com.akak4456.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akak4456.domain.reply.Reply;
import com.akak4456.persistent.ReplyRepository;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private ReplyRepository replyRepo;
	@Transactional
	@Override
	public void addReply(Reply reply) {
		// TODO Auto-generated method stub
		replyRepo.save(reply);
		reply.setPath(reply.generatePathFromRnoAndParentRno());
		replyRepo.save(reply);
	}
	@Transactional
	@Override
	public void updateReply(Long rno, Reply reply) {
		// TODO Auto-generated method stub
		Reply newReply = replyRepo.findById(rno).get();
		newReply.setReply(reply.getReply());
		replyRepo.save(newReply);
	}
	@Transactional
	@Override
	public Page<Reply> getListWithPaging(Long bno, Pageable pageable) {
		// TODO Auto-generated method stub
		return replyRepo.findAllByBno(bno, pageable);
	}
	@Transactional
	@Override
	public void deleteReply(Long rno) {
		// TODO Auto-generated method stub
		Reply deleteReply = replyRepo.findById(rno).get();
		
		deleteReply.setMember(null);
		deleteReply.setReply("삭제된 내용");
		deleteReply.setIsdelete('Y');
		replyRepo.save(deleteReply);
	}
	@Override
	public Page<Reply> getListWithUseremail(String useremail, Pageable pageable) {
		// TODO Auto-generated method stub
		//getListWithPaging test로 테스트 대체
		return replyRepo.findAllByUseremail(useremail, pageable);
	}

}
