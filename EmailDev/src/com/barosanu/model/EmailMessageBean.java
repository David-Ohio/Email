package com.barosanu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;

import com.barosanu.model.table.AbstractTableItem;
import com.barosanu.model.table.FormatableInteger;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessageBean extends AbstractTableItem{
	
	private SimpleStringProperty sender;
	private SimpleStringProperty subject;
	private SimpleStringProperty recipient;
	private SimpleObjectProperty<FormatableInteger> size;
	private SimpleObjectProperty<Date> date;
	private Message messageRefference;
	private List<MimeBodyPart> listOfAttachments = new ArrayList<MimeBodyPart>();
	private StringBuffer attachmentsNames = new StringBuffer("");
	
	private boolean hasAttachments = false;
	public static SimpleStringProperty attachementsLabelValue = new SimpleStringProperty("");
	public static SimpleBooleanProperty attachmentsBtnVisible = new SimpleBooleanProperty(false);
	
	public EmailMessageBean(String Subject, String Sender, String Recipient, int size, boolean isRead, Date date, Message MessageRefference){
		super(isRead);
		this.subject = new SimpleStringProperty(Subject);
		this.sender = new SimpleStringProperty(Sender);
		this.recipient = new SimpleStringProperty(Recipient);
		this.size = new SimpleObjectProperty<FormatableInteger>(new FormatableInteger(size));
		this.date = new SimpleObjectProperty<Date>(date);
		this.messageRefference = MessageRefference;
	}
	
	public String getSender(){
		return sender.get();
	}
	public String getSubject(){
		return subject.get();
	}
	public String getRecipient(){
		return recipient.get();
	}
	public FormatableInteger getSize(){
		return size.get();
	}
	public Date getDate(){
		return date.get();
	}
	public Message getMessageRefference() {
		return messageRefference;
	}
	public List<MimeBodyPart> getListOfAttachments() {
		return listOfAttachments;
	}
	public StringBuffer getAttachmentsNames() {
		return attachmentsNames;
	}

	public boolean isHasAttachments() {
		return hasAttachments;
	}

	public void setHasAttachments(boolean hasAttachments) {
		this.hasAttachments = hasAttachments;
	}
	
	public static EmailMessageBean copy(EmailMessageBean emailMessageBean){
		return new EmailMessageBean(emailMessageBean.subject.get(), 
				emailMessageBean.sender.get(), 
				emailMessageBean.recipient.get(), 
				emailMessageBean.size.get().getSize(),
				emailMessageBean.isRead(), 
				emailMessageBean.date.get(), 
				emailMessageBean.getMessageRefference());
	}
	
}
