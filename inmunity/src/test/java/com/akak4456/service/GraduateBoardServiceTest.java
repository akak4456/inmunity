package com.akak4456.service;

import java.util.List;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.board.GraduateBoard;
import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.reply.FreeReply;
import com.akak4456.domain.reply.GraduateReply;

public class GraduateBoardServiceTest extends BoardServiceTest<GraduateBoard, GraduateReply> {

	@Override
	protected void makeBoard(MemberEntity member) {
		// TODO Auto-generated method stub
		for(int i=0;i<=205;i++) {
			GraduateBoard board = GraduateBoard.builder()
								.title("title" + i)
								.content("content" + i)
								.member(member)
								.build();
			
			boardRepo.save(board);
		}
	}

	@Override
	protected GraduateBoard makeOneBoard(String title, String content, MemberEntity member,
			List<BoardFileUpload> boardFileUpload) {
		// TODO Auto-generated method stub
		return GraduateBoard.builder()
				.title(title)
				.content(content)
				.member(member)
				.fileUpload(boardFileUpload).build();
	}

	@Override
	protected GraduateReply makeOneReply(String reply, MemberEntity member, GraduateBoard board) {
		// TODO Auto-generated method stub
		return GraduateReply.builder().reply(reply).member(member).board(board).build();
	}

}
