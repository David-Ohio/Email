package com.barosanu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;

import com.barosanu.model.EmailMessageBean;
import com.barosanu.model.folder.EmailFolderBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelAccess {
	
	
	private EmailMessageBean selectedMessage;
	private EmailFolderBean<String> selectedFolder;
	
	//needed for updater service
	private List<Folder> folderList = new ArrayList<Folder>();
	private ObservableList<String> emailAccountsList = FXCollections.observableArrayList();
	

	public EmailMessageBean getSelectedMessage() {
		return selectedMessage;
	}

	public void setSelectedMessage(EmailMessageBean selectedMessage) {
		this.selectedMessage = selectedMessage;
	}
	
	public EmailFolderBean<String> getSelectedFolder() {
		return selectedFolder;
	}

	public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
		this.selectedFolder = selectedFolder;
	}

	public List<Folder> getFolderList() {
		return folderList;
	}

	
	public void addFolder(Folder folder){
		folderList.add(folder);
	}
	
	public void addEmailAccount(String emailAccount){
		emailAccountsList.add(emailAccount);
	}

	public ObservableList<String> getEmailAccountsList() {
		return emailAccountsList;
	}


}
