package com.barosanu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;

import com.barosanu.model.EmailMessageBean;
import com.barosanu.model.folder.EmailFolderBean;

public class ModelAccess {
	
	
	private EmailMessageBean selectedMessage;

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

	private EmailFolderBean<String> selectedFolder;
	
	private List<Folder> foldersList = new ArrayList<Folder>();
	
	public List<Folder> getFoldersList(){
		return  foldersList;
	}
	
	public void addFolder(Folder folder){
		foldersList.add(folder);
	}

}
