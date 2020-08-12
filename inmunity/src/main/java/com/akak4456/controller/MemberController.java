package com.akak4456.controller;

import javax.servlet.http.HttpSession;
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
import com.akak4456.service.fileupload.MemberFileUploadService;
import com.akak4456.service.member.MemberService;
import com.akak4456.vo.ChangeInfoVO;
import com.akak4456.vo.ChangeProfileVO;
import com.akak4456.vo.ChangePwVO;
import com.akak4456.vo.CheckEmailVO;
import com.akak4456.vo.ForgotPWVO;
import com.akak4456.vo.MemberVO;
import com.akak4456.vo.PageVO;
import com.akak4456.vo.WithdrawalVO;

import lombok.extern.java.Log;

@Controller
@Log
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private MemberFileUploadService memberFileUploadService;
	
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
	@GetMapping("/forgotpw")
	public void forgotpw(Model model,PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	@PostMapping("/forgotpw")
	public ResponseEntity<String> forgotpwPost(@RequestBody ForgotPWVO forgotPWVO){
		if(!memberService.isSameEmail(forgotPWVO.getUseremail())) {
			return new ResponseEntity<>("notmatch",HttpStatus.BAD_REQUEST);
		}
		String newpassword = RandomStringUtils.randomAlphanumeric(5)+"!"+RandomStringUtils.randomAlphanumeric(5)+"#";
		memberService.changePw(forgotPWVO.getUseremail(), newpassword);
		sendEmailPW(forgotPWVO.getUseremail(),newpassword);
		return new ResponseEntity<>("success",HttpStatus.OK);
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
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/my/changepw")
	public void changepw(Model model, PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/my/changeprofile")
	public void changeprofile(Model model, PageVO pageVO) {
		model.addAttribute("pageVO",pageVO);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/my/withdrawal")
	public void withdrawal(Model model, PageVO pageVO) {
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
	
	@PreAuthorize("isAuthenticated()")
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
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/my/changeinfo")
	public ResponseEntity<String> changeInfoPost(@Valid @RequestBody ChangeInfoVO changeInfoVO,BindingResult bindingResult,HttpSession session){
		if(bindingResult.hasErrors()) {
			log.info("ERROR");
			for (Object object : bindingResult.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					return new ResponseEntity<>(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
				}
			}
		}
		if(!memberService.isMatchUseremailAndPassword(changeInfoVO.getUseremail(), changeInfoVO.getUserpw())) {
			return new ResponseEntity<>("notmatch",HttpStatus.BAD_REQUEST);
		}
		memberService.changeInfo(changeInfoVO.getUseremail(), changeInfoVO.getUsername());
		session.invalidate();
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/my/changeprofile")
	public ResponseEntity<String> changeprofilePost(@RequestBody ChangeProfileVO changeProfileVO,HttpSession session){
		memberFileUploadService.putUserProfileInfoToDB(changeProfileVO.getUploadPath(), changeProfileVO.getUploadFileName(), changeProfileVO.getUseremail());
		memberService.changeProfile(changeProfileVO.getImgSrc(), changeProfileVO.getUseremail());
		session.invalidate();
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/my/changepw")
	public ResponseEntity<String> changePwPost(@Valid @RequestBody ChangePwVO changePwVO,BindingResult bindingResult,HttpSession session){
		if(bindingResult.hasErrors()) {
			log.info("ERROR");
			for (Object object : bindingResult.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					return new ResponseEntity<>(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
				}
			}
		}
		if(!memberService.isMatchUseremailAndPassword(changePwVO.getUseremail(), changePwVO.getPw())) {
			return new ResponseEntity<>("notmatch",HttpStatus.BAD_REQUEST);
		}
		memberService.changePw(changePwVO.getUseremail(), changePwVO.getNewpw());
		session.invalidate();
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/my/withdrawal")
	public ResponseEntity<String> withdrawalPost(@RequestBody WithdrawalVO withdrawalVO,HttpSession session){
		if(!memberService.isMatchUseremailAndPassword(withdrawalVO.getUseremail(), withdrawalVO.getPw())) {
			return new ResponseEntity<>("notmatch",HttpStatus.BAD_REQUEST);
		}
		memberService.withdrawal(withdrawalVO.getUseremail());
		session.invalidate();
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
	private void sendEmailPW(String to,String newpassword) {
		StringBuffer content = new StringBuffer();
		content.append("새로운 비밀번호는 <u>");
		content.append(newpassword);
		content.append("</u>입니다. 이 비밀번호로 로그인해주세요!");
		emailService.sendMail( to,"새로운 비밀번호 입니다!" , content.toString());
	}
}
