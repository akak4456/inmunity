package com.akak4456.service;

import java.util.List;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.reply.FreeReply;
public class FreeBoardServiceTest extends BoardServiceTest<FreeBoard, FreeReply> {

	@Override
	protected void makeBoard(MemberEntity member) {
		// TODO Auto-generated method stub
		for(int i=0;i<=205;i++) {
			FreeBoard board = FreeBoard.builder()
								.title("title" + i)
								.content("content" + i)
								.member(member)
								.build();
			
			boardRepo.save(board);
		}
	}

	@Override
	protected FreeBoard makeOneBoard(String title, String content, MemberEntity member, List<BoardFileUpload> boardFileUpload) {
		// TODO Auto-generated method stub
		return FreeBoard.builder()
				.title(title)
				.content(content)
				.member(member)
				.fileUpload(boardFileUpload).build();
	}

	@Override
	protected FreeReply makeOneReply(String reply, MemberEntity member, FreeBoard board) {
		// TODO Auto-generated method stub
		return FreeReply.builder().reply(reply).member(member).board(board).build();
	}

}
