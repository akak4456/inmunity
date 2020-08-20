package com.akak4456.service;

import java.util.List;

import com.akak4456.domain.board.GraduateBoard;
import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.reply.GraduateReply;

public class GraduateReplyServiceTest extends ReplyServiceTest<GraduateBoard, GraduateReply> {
	
	@Override
	protected GraduateBoard makeOneBoard(String title, String content, MemberEntity member,
			List<BoardFileUpload> boardFileUpload) {
		// TODO Auto-generated method stub
		return GraduateBoard.builder().title(title).content(content).member(member).fileUpload(boardFileUpload).build();
	}

	@Override
	protected GraduateReply makeOneReply(String reply, MemberEntity member, GraduateBoard board, Long parent_rno) {
		// TODO Auto-generated method stub
		return GraduateReply.builder().reply(reply).member(member).board(board).parent_rno(parent_rno).build();
	}

}
