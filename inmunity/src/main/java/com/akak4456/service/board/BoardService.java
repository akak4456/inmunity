package com.akak4456.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEntity;
import com.akak4456.persistent.board.BoardRepository;
import com.akak4456.persistent.recommendoropposite.RecommendOrOppositeRepository;
import com.akak4456.persistent.scrap.ScrapRepository;

public class BoardService<T extends Board,R extends RecommendOrOppositeEntity> {
	@Autowired
	private BoardRepository<T> boardRepo;
	@Autowired
	private RecommendOrOppositeRepository<R> recommendOrOppositeRepo;
	@Autowired
	private ScrapRepository scrapRepository;

	@Transactional
	public void addBoard(T board) {
		boardRepo.save(board);
	}

	@Transactional(readOnly=true)
	public Page<T> getListWithPaging(String type, String keyword, Pageable pageable) {
		return boardRepo.findAll(boardRepo.makePredicate(type, keyword), pageable);
	}

	@Transactional
	public T getOne(Long bno) {
		T ret = boardRepo.findById(bno).get();
		ret.setViewcnt(ret.getViewcnt()+1);
		boardRepo.save(ret);
		return ret;
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE) 
	public void modify(Long bno,T board) {
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
		recommendOrOppositeRepo.deleteByBno(bno);
		scrapRepository.deleteByBno(bno);
		boardRepo.deleteById(bno);	
	}
	
	@Transactional
	public void upRecommendcnt(R recommendOrOppositeEntity) throws RecommendOrOppositeAlreadyExist {
		if(recommendOrOppositeRepo.findById(recommendOrOppositeEntity.getId()).isPresent()) {
			//추천과 반대 둘중 하나라도 했다면
			throw new RecommendOrOppositeAlreadyExist();
		}
		
		T boardToUpdate = boardRepo.findById(recommendOrOppositeEntity.getId().getBno()).get();
		boardToUpdate.setRecommendcnt(boardToUpdate.getRecommendcnt()+1);
		boardRepo.save(boardToUpdate);
		//board의 recommend count 올리기
		
		recommendOrOppositeRepo.save(recommendOrOppositeEntity);
		//추천기록 저장
	}
	
	@Transactional
	public void upOppositecnt(R recommendOrOppositeEntity) throws RecommendOrOppositeAlreadyExist {
		if(recommendOrOppositeRepo.findById(recommendOrOppositeEntity.getId()).isPresent()) {
			//추천과 반대 둘중 하나라도 했다면
			throw new RecommendOrOppositeAlreadyExist();
		}
		
		T boardToUpdate = boardRepo.findById(recommendOrOppositeEntity.getId().getBno()).get();
		boardToUpdate.setOppositecnt(boardToUpdate.getOppositecnt()+1);
		boardRepo.save(boardToUpdate);
		//board의 opposite count 올리기
		
		recommendOrOppositeRepo.save(recommendOrOppositeEntity);
		//추천기록 저장
	}
}
