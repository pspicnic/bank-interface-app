/**
 * 
 */
package za.co.pps.bank.mutual.communication;

import javax.mail.MessagingException;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Oct 2017
 * @version 1.0
 */
public interface EmailSender {

	/**
	 * This method sends an email with just text message
	 * @param to
	 * @param subject
	 * @param message
	 */
	public void sendSimpleEmail(String to, String subject, String message);
	
	/**
	 * This method sends an email with an attachment
	 * @param to
	 * @param subject
	 * @param message
	 * @param pathToAttachment
	 */
	public void sendSimpleEmailWithAttachment(String to, String subject, String message, String pathToAttachment)throws MessagingException;
	
}
