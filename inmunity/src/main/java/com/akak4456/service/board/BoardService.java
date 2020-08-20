package com.akak4456.service.board;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.reply.Reply;
import com.akak4456.exception.RecommendOrOppositeAlreadyExist;
import com.akak4456.persistent.RecommendOrOppositeRepository;
import com.akak4456.persistent.ScrapRepository;
import com.akak4456.persistent.board.BoardRepository;
import com.akak4456.persistent.reply.ReplyRepository;

public abstract class BoardService <T extends Board> {
	@Autowired
	private BoardRepository<T> boardRepo;
	@Autowired
	private ReplyRepository<Reply> replyRepo;
	@Autowired
	private RecommendOrOppositeRepository recommendOrOppositeRepo;
	@Autowired
	private ScrapRepository scrapRepo;
	
	@Transactional
	public void addBoard(T board) {
		// TODO Auto-generated method stub
		boardRepo.save(board);
	}

	@Transactional
	public Page<T> getListWithPaging(String type, String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return boardRepo.findAll(boardRepo.makePredicate(type, keyword), pageable);
	}

	@Transactional
	public T getOne(Long bno) {
		// TODO Auto-generated method stub
		T ret = boardRepo.findById(bno).get();
		ret.setViewcnt(ret.getViewcnt()+1);
		return ret;
	}
	
	@Transactional
	public void modify(Long bno, Board board) {
		// TODO Auto-generated method stub
		T boardToUpdate = boardRepo.findById(bno).get();
		boardToUpdate.setTitle(board.getTitle());
		boardToUpdate.setContent(board.getContent());
		boardToUpdate.getFileUpload().clear();
		if(board.getFileUpload() != null && board.getFileUpload().size() > 0) {
			boardToUpdate.getFileUpload().addAll(board.getFileUpload());
		}
		boardRepo.save(boardToUpdate);

	}
	@Transactional
	public void delete(Long bno) {
		// TODO Auto-generated method stub
		replyRepo.deleteByBno(bno);
		recommendOrOppositeRepo.deleteByBno(bno);
		scrapRepo.deleteByBno(bno);
		boardRepo.deleteById(bno);
	}
	
	@Transactional
	public void upRecommendcnt(RecommendOrOpposite recommendOrOppositeEntity) throws RecommendOrOppositeAlreadyExist {
		// TODO Auto-generated method stub
		if(recommendOrOppositeRepo.findById(recommendOrOppositeEntity.getId()).isPresent()) {
			throw new RecommendOrOppositeAlreadyExist();
		}
		
		T boardToUpdate = boardRepo.findById(recommendOrOppositeEntity.getId().getBoard_id()).get();
		boardToUpdate.setRecommendcnt(boardToUpdate.getRecommendcnt()+1);
		boardRepo.save(boardToUpdate);
		//board의 recommend count 올리기
		
		recommendOrOppositeRepo.save(recommendOrOppositeEntity);
		//추천기록 저장
	}
	
	@Transactional
	public void upOppositecnt(RecommendOrOpposite recommendOrOppositeEntity) throws RecommendOrOppositeAlreadyExist {
		// TODO Auto-generated method stub
		if(recommendOrOppositeRepo.findById(recommendOrOppositeEntity.getId()).isPresent()) {
			throw new RecommendOrOppositeAlreadyExist();
		}
		
		T boardToUpdate = boardRepo.findById(recommendOrOppositeEntity.getId().getBoard_id()).get();
		boardToUpdate.setOppositecnt(boardToUpdate.getOppositecnt()+1);
		boardRepo.save(boardToUpdate);
		//board의 opposite count 올리기
		
		recommendOrOppositeRepo.save(recommendOrOppositeEntity);
		//반대기록 저장
	}
}
