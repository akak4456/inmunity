package com.akak4456.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEntity;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.board.BoardRepository;
import com.akak4456.vo.PageVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public abstract class BoardControllerTest <T extends Board, R extends RecommendOrOppositeEntity> {
	private MockMvc mockMvc;
	protected ObjectMapper objectMapper;
	@Autowired
	private BoardController<T,R> boardController;
	@Autowired
	private BoardRepository<T> boardRepo;
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}
	public abstract String getRootAddress();
	
	public abstract T makeOne(String title,String content);
	
	public abstract T mappingObject(MvcResult result);
	@Test
	public void getListTest() throws Exception{
		PageVO pageVO = new PageVO(1);
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(getRootAddress()+"/boards",pageVO)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void getOneTest() throws Exception {
		T board = makeOne("title1","content1");
		boardRepo.save(board);
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
				.get(getRootAddress()+"/boards/"+board.getBno())
				.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
		
		T board2 = mappingObject(result);
		assertEquals(board2.getTitle(),"title1");
		assertEquals(board2.getContent(),"content1");
	}
	@Test
	public void addOneTest() throws Exception {
		T board = makeOne("new title","new content");
		String content =objectMapper.writeValueAsString(board);
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(getRootAddress()+"/boards")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void modifyOneTest() throws Exception{
		T board = makeOne("to update title","to update content");
		boardRepo.save(board);
		T newBoard = boardRepo.findById(board.getBno()).get();
		newBoard.setTitle("update title");
		newBoard.setContent("update content");
		String content = objectMapper.writeValueAsString(newBoard);
		//log.info("CONTENT:"+content);
		mockMvc.perform(
				MockMvcRequestBuilders
				.put(getRootAddress()+"/boards/"+board.getBno())
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void deleteOneTest() throws Exception{
		T board = makeOne("delete title","delete content");
		boardRepo.save(board);
		mockMvc.perform(
				MockMvcRequestBuilders
				.delete(getRootAddress()+"/boards/"+board.getBno())
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
