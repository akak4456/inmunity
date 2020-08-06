package com.akak4456.service.del;

import java.util.List;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.fileupload.FreeFileUpload;

public class FreeDeleteServiceTest extends DeleteServiceTest <FreeBoard, FreeFileUpload> {

	@Override
	public FreeFileUpload makeOne(String uploadPath, String uploadFileName) {
		// TODO Auto-generated method stub
		FreeFileUpload ret = new FreeFileUpload();
		ret.setUploadPath(uploadPath);
		ret.setUploadFileName(uploadFileName);
		return ret;
	}

	@Override
	public FreeBoard makeOneBoard(String title, String content, List<FreeFileUpload> uploads) {
		// TODO Auto-generated method stub
		FreeBoard board = new FreeBoard();
		board.setTitle(title);
		board.setContent(content);
		board.setFileUpload(uploads);
		return board;
	}

}
