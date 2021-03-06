package com.barosanu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Folder;

import com.barosanu.controller.persistence.ValidAccount;
import com.barosanu.model.EmailAccountBean;
import com.barosanu.model.EmailMessageBean;
import com.barosanu.model.folder.EmailFolderBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

public class ModelAccess {

	private Map<String, EmailAccountBean> emailAccounts = new HashMap<String, EmailAccountBean>();
	private ObservableList<String> emailAccountsNames = FXCollections.observableArrayList();
	private List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
	private EmailFolderBean<String> root = new EmailFolderBean<String>("", new ImageView());

	public ObservableList<String> getEmailAccountNames() {
		return emailAccountsNames;
	}

	public EmailAccountBean getEmailAccountByName(String name) {
		return emailAccounts.get(name);
	}

	public void addAccount(EmailAccountBean account) {
		emailAccounts.put(account.getEmailAdress(), account);
		emailAccountsNames.add(account.getEmailAdress());
		validAccountList.add(new ValidAccount(account.getEmailAdress(), account.getPassword()));
	}

	private EmailMessageBean selectedMessage;
	private EmailFolderBean<String> selectedFolder;

	// needed for updater service
	private List<Folder> folderList = new ArrayList<Folder>();

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

	public void addFolder(Folder folder) {
		folderList.add(folder);
	}

	public EmailFolderBean<String> getRoot() {
		return root;
	}
	
	public List<ValidAccount> getValidAccountList(){
		return validAccountList;
	}


}
