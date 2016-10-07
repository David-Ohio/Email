package com.barosanu.controller.services;

import javax.mail.internet.MimeBodyPart;

import com.barosanu.model.EmailMessageBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class SaveAttachmentsService extends Service<Void>{
	
	private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";
	private EmailMessageBean message;
	
	public SaveAttachmentsService(EmailMessageBean message) {
		super();
		this.message = message;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>(){
			@Override
			protected Void call() throws Exception {
				for(MimeBodyPart mbp:message.getListOfAttachments()){
					try {
						mbp.saveFile(LOCATION_OF_DOWNLOADS + mbp.getFileName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			}			
		};
	}
}
