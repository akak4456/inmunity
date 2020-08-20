package com.akak4456.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEnum;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeId;
import com.akak4456.domain.reply.FreeReply;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/freeboard/**")
public class FreeBoardController extends BoardController<FreeBoard> {

	@Override
	protected RecommendOrOpposite makeOneRecommendOrOppositeEntity(Long bno, String useremail, boolean isRecommend) {
		// TODO Auto-generated method stub
		RecommendOrOppositeId id = new RecommendOrOppositeId(bno, useremail);
		RecommendOrOpposite ret = null;
		FreeBoard board = FreeBoard.builder().bno(bno).title("empty").content("empty").build();
		MemberEntity member = MemberEntity.builder().useremail(useremail).role(Role.ROLE_MEMBER).emailCheck(EmailCheck.N).build();
		if (isRecommend) {
			ret = RecommendOrOpposite.builder().id(id).board(board).member(member).recommendOrOpposite(RecommendOrOppositeEnum.Recommend).build();
		} else {
			ret = RecommendOrOpposite.builder().id(id).board(board).member(member).recommendOrOpposite(RecommendOrOppositeEnum.Opposite).build();
		}
		return ret;

	}
}
