package za.co.pps.bank.mutual.communication;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 02 Oct 2017
 * @version 1.0
 */

@Component
public class EmailSenderImpl implements EmailSender {
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Override
	public void sendSimpleEmail(String to, String subject, String textMessage){
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(to);
		message.setSubject(subject);
		message.setText(textMessage);
		emailSender.send(message);

	}
	
	@Override
	public void sendSimpleEmailWithAttachment(String to, String subject, String textMessage, String pathToAttachment) throws MessagingException{
		
		MimeMessage message =  emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(textMessage);
		
		FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
		helper.addAttachment("Exception File",file);
		
		emailSender.send(message);

	}
	
}
