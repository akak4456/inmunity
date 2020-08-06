package com.akak4456.service.reply;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.recommendoropposite.FreeRecommendOrOpposite;
import com.akak4456.domain.reply.FreeReply;

public class FreeReplyServiceImplTest extends ReplyServiceTest<FreeBoard, FreeReply,FreeRecommendOrOpposite> {

	@Override
	public FreeBoard makeOneBoard(String title, String content) {
		// TODO Auto-generated method stub
		FreeBoard board = new FreeBoard();
		board.setTitle(title);
		board.setContent(content);
		return board;
	}

	@Override
	public FreeReply makeOneReply(FreeBoard board, Long parent_rno, String replyer, String reply) {
		// TODO Auto-generated method stub
		FreeReply replyret = new FreeReply();
		replyret.setBoard(board);
		replyret.setParent_rno(parent_rno);
		replyret.setReplyer(replyer);
		replyret.setReply(reply);
		return replyret;
	}

}
