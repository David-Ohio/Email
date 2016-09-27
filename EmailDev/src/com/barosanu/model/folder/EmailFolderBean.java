package com.barosanu.model.folder;

import com.barosanu.model.EmailMessageBean;
import com.barosanu.view.ViewFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailFolderBean<T> extends TreeItem<String>{
	
	private boolean topElement = false;
	private int unreadMessagesCount;
	private String name;
	@SuppressWarnings("unused")
	private String completeName;
	private ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
	
	/**
	 * Constructor for the top element
	 * @param value
	 */
	public EmailFolderBean(String value){
		super(value, ViewFactory.defaultFactory.resolveIcon(value));
		this.name = value;
		this.completeName = value;
		data = null;
		topElement = true;
		this.setExpanded(true);
	}
	
	/**
	 * Constructor for email folders
	 * @param value
	 * @param completeName
	 */
	public EmailFolderBean(String value, String completeName){
		super(value, ViewFactory.defaultFactory.resolveIcon(value));
		this.name = value;
		this.completeName = completeName;
	}
	
	
	public void addEmail(EmailMessageBean message){
		data.add(message);
		if(!message.isRead()){
			incrementUnreadMessageCount(1);
		}		
	}

	public void incrementUnreadMessageCount(int newMessages){
		unreadMessagesCount = unreadMessagesCount + newMessages;
		updateValue();
	}
	public void decrementUreadMessagesCount(){
		unreadMessagesCount--;
		updateValue();
	}
	
	public ObservableList<EmailMessageBean> getData(){
		return data;
	}
	
	private void updateValue(){
		if(unreadMessagesCount>0){
			this.setValue((String) (name + "(" + unreadMessagesCount + ")"));
		}else{
			this.setValue(name);
		}		
	}
	public boolean isTopElement(){
		return topElement;
	}
}
