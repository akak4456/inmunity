package com.akak4456.persistent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEnum;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeId;
import com.akak4456.inmunity.InmunityApplication;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class RecommendOrOppositeRepositoryTest {
	@Autowired
	private MemberRepository memberRepo;
	private MemberEntity member;
	@Before
	public void setUp() {
		if(!memberRepo.existsById("akak4456@naver.com")) {
			member = MemberEntity.builder()
					.useremail("akak4456@naver.com")
					.name("akak4456")
					.role(Role.ROLE_MEMBER)
					.emailCheck(EmailCheck.N).build();
			memberRepo.save(member);
		}else {
			member = memberRepo.findById("akak4456@naver.com").get();
		}
	}
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private RecommendOrOppositeRepository recommendOrOppositeRepo;
	@Test
	public void addTest() {
		FreeBoard board = FreeBoard.builder()
				.title("title")
				.content("content")
				.member(member).build();
		
		boardRepo.save(board);
		
		RecommendOrOppositeId id = new RecommendOrOppositeId(board.getBno(),member.getUseremail());
		
		RecommendOrOpposite ro = RecommendOrOpposite.builder()
									.id(id)
									.board(board)
									.member(member)
									.recommendOrOpposite(RecommendOrOppositeEnum.Recommend).build();
		recommendOrOppositeRepo.save(ro);
		
	}
}
