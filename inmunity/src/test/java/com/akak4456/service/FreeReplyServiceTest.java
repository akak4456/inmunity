package com.akak4456.service;

import java.util.List;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.reply.FreeReply;

public class FreeReplyServiceTest extends ReplyServiceTest<FreeBoard, FreeReply> {

	@Override
	protected FreeBoard makeOneBoard(String title, String content, MemberEntity member,
			List<BoardFileUpload> boardFileUpload) {
		// TODO Auto-generated method stub
		return FreeBoard.builder().title(title).content(content).member(member).fileUpload(boardFileUpload).build();
	}

	@Override
	protected FreeReply makeOneReply(String reply, MemberEntity member, FreeBoard board, Long parent_rno) {
		// TODO Auto-generated method stub
		return FreeReply.builder().reply(reply).member(member).board(board).parent_rno(parent_rno).build();
	}

}
