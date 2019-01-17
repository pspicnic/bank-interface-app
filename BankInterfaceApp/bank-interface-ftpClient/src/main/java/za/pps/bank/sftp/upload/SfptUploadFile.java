/**
 * 
 */
package za.pps.bank.sftp.upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.text.ChangedCharSetException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 23 Mar 2018
 * @version 1.0
 */
@Component
@PropertySource("classpath:/configs/application.properties")
public class SfptUploadFile {
	
	@Value("${sftp.privateKeyPassphrase}")
	private String passphrase;
	
	@Value("${sftp.privateKey}")
	private String identityFilePath;
	
	@Value("${sftp.user}")
	private String username;
	
	@Value("${sftp.host.address}")
	private String host;
	
	@Value("${sftp.host.port}")
	private String portNumber;
	
	private ChannelSftp channel;
	private Session session; 
	

	/**
	 * This method establishes a sftp connection and return the connection object, close the connection once done.
	 * 
	 * @return {@link ChannelSftp}
	 */
	private Session establishConnection() throws JSchException{

		System.out.println("Properties ==============");
		System.out.println("Private Key :"+identityFilePath);
		System.out.println("Passphrase :" + passphrase);
		System.out.println("Username :"+username);
		System.out.println("Host : " + host);
		System.out.println("Port :" + portNumber);
		
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(identityFilePath, passphrase);//SSH Private key path and the passphrase 
			jsch.setConfig("PasswordAuthentication", "no");
			session = jsch.getSession(username, host, Integer.parseInt(portNumber));
			Properties config = new Properties();
			config.setProperty("StrictHostKeyChecking", "no");
			config.setProperty("PasswordAuthentication", "no");
			session.setConfig(config);
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		
		if(session == null){
			throw new JSchException("Can't establish connection.....");
		}
		
		return session;
	}
	
	private ChannelSftp getConnection() throws JSchException{
		try {
			channel = (ChannelSftp)establishConnection().openChannel("sftp");
			channel.connect();
		} catch (JSchException e ) {
			e.printStackTrace();
		}
		
		if(channel == null){
			throw new JSchException("Channel cannot be established.....");
		}
		
		return channel;
	}
	
	private void closeConnection(){
		if(session != null){
			session.disconnect();
		}
		if(channel != null){
			channel.disconnect();
		}
	}
	
	public boolean uploadFileToServer(String encrytptedFilePath, String remoteFolder){
		try {

			if(new File(encrytptedFilePath).isFile()){
				System.out.println("========= Files to Upload =============");
				System.out.println("File : " + encrytptedFilePath);
				System.out.println("Folder : " + remoteFolder );
				getConnection().put(encrytptedFilePath, remoteFolder);
			}
		 } catch (SftpException e) {
			e.printStackTrace();
			return false;
		 } catch (JSchException e) {
			e.printStackTrace();
			return false;
		}
		 closeConnection();
		 return true;
	}
	
	

}
