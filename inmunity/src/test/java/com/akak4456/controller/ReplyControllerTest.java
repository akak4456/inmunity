package com.akak4456.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEntity;
import com.akak4456.domain.reply.Reply;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.reply.ReplyRepository;
import com.akak4456.service.board.BoardService;
import com.akak4456.service.reply.ReplyService;
import com.akak4456.vo.PageVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public abstract class ReplyControllerTest<B extends Board,R extends Reply,O extends RecommendOrOppositeEntity> {
	private MockMvc mockMvc;
	protected ObjectMapper objectMapper;
	@Autowired
	private ReplyController<B,R> replyController;
	@Autowired
	private BoardService<B,O> boardService;
	@Autowired
	private ReplyService<B,R> replyService;
	@Autowired
	private ReplyRepository<R> replyRepo;
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(replyController).build();
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}
	public abstract String getRootAddress();
	
	public abstract B makeOneBoard(String title,String content);
	
	public abstract R makeOneReply(B board,Long parent_rno,String replyer,String reply);
	@Test
	public void getListTest() throws Exception{
		B board = makeOneBoard("board","board");
		boardService.addBoard(board);
		R reply = makeOneReply(board,-1L,"reply","reply");
		replyService.addReply(reply);
		PageVO pageVO = new PageVO(1);
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(getRootAddress()+"/reply/"+board.getBno(),pageVO)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void addOneTest() throws Exception {
		B board = makeOneBoard("new title","new content");
		boardService.addBoard(board);
		R reply = makeOneReply(board,-1L,"new reply","new reply");
		String content =objectMapper.writeValueAsString(reply);
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(getRootAddress()+"/reply/"+board.getBno())
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void modifyOneTest() throws Exception{
		B board = makeOneBoard("to update title","to update content");
		boardService.addBoard(board);
		R reply = makeOneReply(board,-1L,"reply","reply");
		replyService.addReply(reply);
		R newReply = replyRepo.findById(reply.getRno()).get();
		newReply.setReply("new Reply");
		String content = objectMapper.writeValueAsString(newReply);
		//log.info("CONTENT:"+content);
		mockMvc.perform(
				MockMvcRequestBuilders
				.put(getRootAddress()+"/reply/"+board.getBno()+"/"+newReply.getRno())
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void deleteOneTest() throws Exception{
		B board = makeOneBoard("delete title","delete content");
		boardService.addBoard(board);
		R reply = makeOneReply(board,-1L,"reply","reply");
		replyService.addReply(reply);
		mockMvc.perform(
				MockMvcRequestBuilders
				.delete(getRootAddress()+"/reply/"+board.getBno()+"/"+reply.getRno())
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
