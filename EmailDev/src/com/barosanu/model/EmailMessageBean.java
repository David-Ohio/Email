package com.barosanu.model;

import java.util.Date;

import com.barosanu.model.table.AbstractTableItem;
import com.barosanu.model.table.FormatableInteger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessageBean extends AbstractTableItem{
	
	private SimpleStringProperty sender;
	private SimpleStringProperty subject;
	private SimpleObjectProperty<FormatableInteger> size;
	private SimpleObjectProperty<Date> date;
	private String content;
	
	public EmailMessageBean(String Subject, String Sender, int size, String Content, boolean isRead, Date date){
		super(isRead);
		this.subject = new SimpleStringProperty(Subject);
		this.sender = new SimpleStringProperty(Sender);
		this.size = new SimpleObjectProperty<FormatableInteger>(new FormatableInteger(size));
		this.date = new SimpleObjectProperty<Date>(date);
		this.content = Content;
	}
	
	public String getSender(){
		return sender.get();
	}
	public String getSubject(){
		return subject.get();
	}
	public FormatableInteger getSize(){
		return size.get();
	}
	public String getContent(){
		return content;
	}
	public Date getDate(){
		return date.get();
	}

	
}
