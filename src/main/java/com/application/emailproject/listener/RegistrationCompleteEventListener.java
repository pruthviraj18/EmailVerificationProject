package com.application.emailproject.listener;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.application.emailproject.event.RegistrationCompletesEvent;
import com.application.emailproject.user.User;
import com.application.emailproject.user.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor								                     //Event Name here registartion completion event
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompletesEvent> {
	
	@Autowired
	private UserService userService ;
	@Autowired
	private JavaMailSender mailSender;
	
	private User theUser;
	
	private static final Logger log = LoggerFactory.getLogger(RegistrationCompleteEventListener.class);
	//This function is to send the mail for the verification of the registered user once the registration is successful
	@Override
	public void onApplicationEvent(RegistrationCompletesEvent event) {
		
		//1.get the newly registered user
		theUser = event.getUser();
		
		//create a verification token for the user
		String verificationToken = UUID.randomUUID().toString();

		//3.Save the verification token for the user
		userService.saveUserVerificationToken(theUser,verificationToken);

		//4.Build the verification URL to be sent to the user
		//5.Send the email
		String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;

		//5.Send the email
		try {
			sendVerificationEmail(url);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		 log.info("click on the link to verify the registration :"+url);
	}
	
	public void sendVerificationEmail(String url) throws UnsupportedEncodingException, MessagingException
	{
		String subject = " Email verification ";
		String senderName = "User Registration Portal Service";
		
		String mailContent = "<p> Hi, "+ theUser.getFirstName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("GiveSenderMailIDHere", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
	}
}