package com.akak4456.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.exception.RecommendOrOppositeAlreadyExist;
import com.akak4456.persistent.BoardRepository;
import com.akak4456.persistent.RecommendOrOppositeRepository;
import com.akak4456.persistent.ReplyRepository;
import com.akak4456.persistent.ScrapRepository;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private ReplyRepository replyRepo;
	@Autowired
	private RecommendOrOppositeRepository recommendOrOppositeRepo;
	@Autowired
	private ScrapRepository scrapRepo;
	
	@Transactional
	@Override
	public void addBoard(Board board) {
		// TODO Auto-generated method stub
		boardRepo.save(board);
	}

	@Transactional
	@Override
	public Page<Board> getListWithPaging(String type, String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		if(type != null&&type.equals("T"))
			return boardRepo.findAllByTitleLike(keyword, pageable);
		else if(type != null&&type.equals("C"))
			return boardRepo.findAllByContentLike(keyword, pageable);
		else if(type != null&&type.equals("W"))
			return boardRepo.findAllByNameLike(keyword, pageable);
		else if(type != null&&type.equals("E"))
			return boardRepo.findAllByEmailLike(keyword, pageable);
		else
			return boardRepo.findAll(pageable);
	}

	@Transactional
	@Override
	public Board getOne(Long bno) {
		// TODO Auto-generated method stub
		Board ret = boardRepo.findById(bno).get();
		ret.setViewcnt(ret.getViewcnt()+1);
		return ret;
	}
	
	@Transactional
	@Override
	public void modify(Long bno, Board board) {
		// TODO Auto-generated method stub
		Board boardToUpdate = boardRepo.findById(bno).get();
		boardToUpdate.setTitle(board.getTitle());
		boardToUpdate.setContent(board.getContent());
		boardToUpdate.getFileUpload().clear();
		if(board.getFileUpload() != null && board.getFileUpload().size() > 0) {
			boardToUpdate.getFileUpload().addAll(board.getFileUpload());
		}
		boardRepo.save(boardToUpdate);

	}
	@Transactional
	@Override
	public void delete(Long bno) {
		// TODO Auto-generated method stub
		replyRepo.deleteByBno(bno);
		recommendOrOppositeRepo.deleteByBno(bno);
		scrapRepo.deleteByBno(bno);
		boardRepo.deleteById(bno);
	}
	
	@Transactional
	@Override
	public void upRecommendcnt(RecommendOrOpposite recommendOrOppositeEntity) throws RecommendOrOppositeAlreadyExist {
		// TODO Auto-generated method stub
		if(recommendOrOppositeRepo.findById(recommendOrOppositeEntity.getId()).isPresent()) {
			throw new RecommendOrOppositeAlreadyExist();
		}
		
		Board boardToUpdate = boardRepo.findById(recommendOrOppositeEntity.getId().getBoard_id()).get();
		boardToUpdate.setRecommendcnt(boardToUpdate.getRecommendcnt()+1);
		boardRepo.save(boardToUpdate);
		//board의 recommend count 올리기
		
		recommendOrOppositeRepo.save(recommendOrOppositeEntity);
		//추천기록 저장
	}
	
	@Transactional
	@Override
	public void upOppositecnt(RecommendOrOpposite recommendOrOppositeEntity) throws RecommendOrOppositeAlreadyExist {
		// TODO Auto-generated method stub
		if(recommendOrOppositeRepo.findById(recommendOrOppositeEntity.getId()).isPresent()) {
			throw new RecommendOrOppositeAlreadyExist();
		}
		
		Board boardToUpdate = boardRepo.findById(recommendOrOppositeEntity.getId().getBoard_id()).get();
		boardToUpdate.setOppositecnt(boardToUpdate.getOppositecnt()+1);
		boardRepo.save(boardToUpdate);
		//board의 opposite count 올리기
		
		recommendOrOppositeRepo.save(recommendOrOppositeEntity);
		//반대기록 저장
	}

	@Override
	public Page<Board> getListWithUseremail(String useremail, Pageable pageable) {
		// TODO Auto-generated method stub
		//getListWithPaging test로 대체
		return boardRepo.findAllByUseremail(useremail, pageable);
		//return boardRepo.findAll(boardRepo.makePredicateByUseremail(useremail),pageable);
	}

}
