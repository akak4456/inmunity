package com.akak4456.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akak4456.domain.member.EmailCheck;
import com.akak4456.service.email.EmailServiceImpl;
import com.akak4456.service.member.MemberService;
import com.akak4456.vo.CheckEmailVO;
import com.akak4456.vo.MemberVO;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;

@Controller
@Log
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Value("${rootaddress}")
	private String rootAddress;
	@GetMapping("/login")
	public void login(Model model,PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	
	@GetMapping("/logout")
	public void logout(Model model,PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	
	@GetMapping("/join")
	public void join(Model model,PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	@GetMapping("/emailcheck/{id}/{code}")
	public String checkemailcode(@PathVariable("id")String id,@PathVariable("code")String code) {
		if(memberService.checkEmailCode(code, id)) {
			memberService.updateEmailCheck(EmailCheck.Y, id);
			return "/checksuccess";
		}else {
			return "/checkfail";
		}
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/my/default")
	public void my(Model model, PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/my/changeinfo")
	public void changeinfo(Model model, PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	
	@PostMapping("/join")
	@ResponseBody
	@Transactional
	public ResponseEntity<String> joinPost(@Valid @RequestBody MemberVO memberVO,BindingResult bindingResult){
		log.info("MEMBERVO:"+memberVO.toString());
		if(bindingResult.hasErrors()) {
			log.info("ERROR");
			for (Object object : bindingResult.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					return new ResponseEntity<>(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
				}
			}
		}
		if(memberService.isSameId(memberVO.getUserid())) {
			return new ResponseEntity<>("idconflict",HttpStatus.BAD_REQUEST);
		}
		if(memberService.isSameEmail(memberVO.getUseremail())) {
			return new ResponseEntity<>("emailconflict",HttpStatus.BAD_REQUEST);
		}
		memberService.joinPost(memberVO);
		generateCodeAndSendEmail(memberVO.getUserid(),memberVO.getUseremail());
		
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	@PostMapping("/my/checkemail")
	@ResponseBody
	@Transactional
	public ResponseEntity<String> sendCode(@RequestBody CheckEmailVO checkEmailVO){
		if(memberService.isEmailAuthenticated(checkEmailVO.getUseremail())) {
			return new ResponseEntity<>("alreadycheck",HttpStatus.BAD_REQUEST);
		}
		generateCodeAndSendEmail(checkEmailVO.getUserid(),checkEmailVO.getUseremail());
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	private void generateCodeAndSendEmail(String userid,String useremail) {
		String generatedCode = RandomStringUtils.randomAlphanumeric(30);
		memberService.updateAuthKey(generatedCode, userid);
		sendEmailCode(userid,useremail,generatedCode);
	}
	private void sendEmailCode(String id,String to,String code) {
		StringBuffer content = new StringBuffer();
		content.append("<a href='");
		content.append("http://");
		content.append(rootAddress);
		content.append("/emailcheck/");
		content.append(id);
		content.append("/");
		content.append(code);
		content.append("'>여기</a>");
		content.append("를 클릭해서 인증을 완료해주세요!");
		emailService.sendMail( to,"이메일 인증을 완료해주세요!" , content.toString());
	}
}
