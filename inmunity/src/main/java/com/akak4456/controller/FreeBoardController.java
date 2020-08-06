package com.akak4456.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.recommendoropposite.FreeRecommendOrOpposite;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeId;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/freeboard/**")
public class FreeBoardController extends BoardController<FreeBoard, FreeRecommendOrOpposite> {
	@Override
	protected String getRootAddress() {
		// TODO Auto-generated method stub
		return "/freeboard";
	}

	@Override
	protected FreeRecommendOrOpposite makeOneRecommendOrOppositeEntity(Long bno, String email, boolean isRecommend) {
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
