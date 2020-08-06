package com.akak4456.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.test.web.servlet.MvcResult;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.recommendoropposite.FreeRecommendOrOpposite;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class FreeBoardControllerTest extends BoardControllerTest<FreeBoard,FreeRecommendOrOpposite> {

	@Override
	public String getRootAddress() {
		// TODO Auto-generated method stub
		return "/freeboard";
	}

	@Override
	public FreeBoard makeOne(String title, String content) {
		// TODO Auto-generated method stub
		FreeBoard board = new FreeBoard();
		board.setTitle(title);
		board.setContent(content);
		return board;
	}

	@Override
	public FreeBoard mappingObject(MvcResult result) {
		// TODO Auto-generated method stub
		
		try {
			String content = result.getResponse().getContentAsString();
			FreeBoard ret = objectMapper.readValue(content,FreeBoard.class);
			return ret;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
