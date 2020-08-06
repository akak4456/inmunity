package com.akak4456.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
	@Autowired
	private  JavaMailSender mailSender;
	public void sendMail(String to, String subject, String msg) {
        try {

            MimeMessage message = mailSender.createMimeMessage();

            message.setSubject(subject);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("akak35793@gmail.com");
            helper.setTo(to);
            helper.setText(msg, true);
            mailSender.send(message);
        } catch (MessagingException ex) {
        }
    }
}
