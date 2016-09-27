package com.barosanu.model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class EmailAccountBean {
	
	private String emailAdress;
	private String password;
	private Properties properties;
	private Store store;
	private Session session;
	
	
	public String getEmailAdress() {
		return emailAdress;
	}
	public String getPassword() {
		return password;
	}
	public Properties getProperties(){
		return properties;
	}
	public Store getStore() {
		return store;
	}
	public Session getSession() {
		return session;
	}


	public EmailAccountBean(String emailAdress, String password) {
		this.emailAdress = emailAdress;
		this.password = password;
		properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.transport.protocol", "smtps");
		properties.put("mail.smtps.host", "smtp.gmail.com");
		properties.put("mail.smtps.auth", "true");
		properties.put("incomingHost", "imap.gmail.com");
		properties.put("outgoingHost", "smtp.gmail.com");

		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailAdress, password);
			}
		};

		// Connecting:
		session = Session.getInstance(properties, auth);
		
		try{
			this.store = session.getStore();
			store.connect(properties.getProperty("incomingHost"), emailAdress, password);
			System.out.println("EmailAccount constructed successfully: " + this);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	


}
