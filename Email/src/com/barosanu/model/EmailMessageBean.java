package com.barosanu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import com.barosanu.model.table.AbstractTableItem;

import javafx.beans.property.SimpleStringProperty;

public class EmailMessageBean extends AbstractTableItem{
	
	public static Map<String, Integer> formattedValues = new HashMap<String, Integer>();
	
	private SimpleStringProperty sender;
	private SimpleStringProperty subject;
	private SimpleStringProperty size;
	private Message messageReference;
	
	//Attachments hanling:
	private List<MimeBodyPart> attachmentsList = new ArrayList<MimeBodyPart>();
	private StringBuffer attachmentsNames = new StringBuffer();
	
	
	
	
	
	
	
	public EmailMessageBean(String Subject, String Sender, int size,  boolean isRead, Message messageReference){
		super(isRead);
		this.subject = new SimpleStringProperty(Subject);
		this.sender = new SimpleStringProperty(Sender);
		this.size = new SimpleStringProperty(formatSize(size));
		this.messageReference = messageReference;

	}
	
	@Override
	public String toString() {
		return "EmailMessageBean "
				+ "sender=" + sender.get() + 
				", subject=" + subject.get() +
				", size=" + size.get();
	}

	public String getSender(){
		return sender.get();
	}
	public String getSubject(){
		return subject.get();
	}
	public String getSize(){
		return size.get();
	}

	
	private String formatSize(int size){
		String returnValue;
		if(size<= 0){
			returnValue =  "0";}
		
		else if(size<1024){
			returnValue = size + " B";
		}
		else if(size < 1048576){
			returnValue = size/1024 + " kB";
		}else{
			returnValue = size/1048576 + " MB";
		}
		formattedValues.put(returnValue, size);
		return returnValue;
		
	}

	public Message getMessageReference() {
		return messageReference;
	}

	public List<MimeBodyPart> getAttachmentsList() {
		return attachmentsList;
	}

	public String getAttachmentsNames() {
		return attachmentsNames.toString();
	}
	
	public void addAttachment(MimeBodyPart mbp){
		attachmentsList.add(mbp);
		try {
			attachmentsNames.append(mbp.getFileName() + "; ");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public boolean hasAttachments(){
		return attachmentsList.size() > 0;
	}
	//clear methods:
	public void clearAttachments(){
		attachmentsList.clear();
		attachmentsNames.setLength(0);
	}


}
