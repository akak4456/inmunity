package com.akak4456.service.board;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.recommendoropposite.FreeRecommendOrOpposite;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeId;

public class FreeBoardServiceTest extends BoardServiceTest<FreeBoard,FreeRecommendOrOpposite> {

	@Override
	public void makeData(int count) {
		// TODO Auto-generated method stub
		boardRepo.deleteAll();
		for(int i=1;i<=count;i++) {
			boardRepo.save(makeOne(i+"번째 제목",i+"번째 내용"));
		}
	}

	@Override
	public FreeBoard makeOne(String title,String content) {
		// TODO Auto-generated method stub
		FreeBoard board = new FreeBoard();
		board.setTitle(title);
		board.setContent(content);
		board.setUseremail("akak4456@naver.com");
		board.setUsername("akak4456");
		return board;
	}

	@Override
	public FreeRecommendOrOpposite makeOneRecommendOrOppositeEntity(Long bno, String email, boolean isRecommend) {
		// TODO Auto-generated method stub
		FreeRecommendOrOpposite ret = new FreeRecommendOrOpposite();
		RecommendOrOppositeId id = new RecommendOrOppositeId(bno,email);
		ret.setId(id);
		if(isRecommend)
			ret.setRecommendOrOpposite(RecommendOrOpposite.Recommend);
		else
			ret.setRecommendOrOpposite(RecommendOrOpposite.Opposite);
		return ret;
	}
}
