package com.barosanu.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.barosanu.model.EmailMessageBean;
import com.barosanu.model.SampleData;
import com.barosanu.model.folder.EmailFolderBean;
import com.barosanu.model.table.BoldableRowFactory;
import com.barosanu.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainController extends AbstractController implements Initializable{
	
    public MainController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	@FXML
    private TreeView<String> emailFoldersTreeView;
    private MenuItem showDetails = new MenuItem("show details");

	
    @FXML
    private TableView<EmailMessageBean> emailTableView;
	
    @FXML
    private TableColumn<EmailMessageBean, String> subjectCol;

    @FXML
    private TableColumn<EmailMessageBean, String> senderCol;

    @FXML
    private TableColumn<EmailMessageBean, String> sizeCol;
	
    @FXML
    private WebView messageRenderer;
	
    @FXML
    private Button Button1;

    @FXML
    void Button1Action(ActionEvent event) {
    	System.out.println("button1 clicked!!");
    }
    
    @FXML
    void changeReadAction() {
    	EmailMessageBean message = getModelAccess().getSelectedMessage();
    	if(message != null){
    		boolean value = message.isRead();
    		message.setRead(!value);
    		EmailFolderBean<String>  selectedFolder = getModelAccess().getSelectedFolder();
    		if(selectedFolder != null){
    			if(value){
    				selectedFolder.incrementUnreadMessageCount(1);
    			}else{
    				selectedFolder.decrementUreadMessagesCount();
    			}
    		}
    	}
    }
    

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		emailTableView.setRowFactory(e-> new BoldableRowFactory<>());
		ViewFactory viewfactory = ViewFactory.defaultFactory;
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("size"));		
		sizeCol.setComparator(new Comparator<String>() {			
			Integer int1, int2;			
			@Override
			public int compare(String o1, String o2) {
					int1 = EmailMessageBean.formattedValues.get(o1);
					int2 = EmailMessageBean.formattedValues.get(o2);
					return int1.compareTo(int2);
			}
		});
		
		
		EmailFolderBean<String> root = new EmailFolderBean<String>("");
		emailFoldersTreeView.setRoot(root);		
		emailFoldersTreeView.setShowRoot(false);
//		root.setGraphic(viewfactory.resolveIcon(root.getValue()));		
//		TreeItem<String> Inbox = new TreeItem<String>("Inbox", viewfactory.resolveIcon("Inbox"));
//		TreeItem<String> Sent = new TreeItem<String>("Sent", viewfactory.resolveIcon("Sent"));
//			TreeItem<String> Subitem1 = new TreeItem<String>("Subitem1", viewfactory.resolveIcon("Subitem1"));
//			TreeItem<String> Subitem2 = new TreeItem<String>("Subitem2",viewfactory.resolveIcon("Subitem2"));
//			Sent.getChildren().addAll(Subitem1, Subitem2);
//		TreeItem<String> Spam = new TreeItem<String>("Spam", viewfactory.resolveIcon("Spam"));
//		TreeItem<String> Trash = new TreeItem<String>("Trash", viewfactory.resolveIcon("Trash"));		
//		root.getChildren().addAll(Inbox, Sent, Spam, Trash);
		EmailFolderBean<String> barosanu = new EmailFolderBean<String>("example@yahoo.com");
		root.getChildren().add(barosanu);
		EmailFolderBean<String> Inbox = new EmailFolderBean<String>("Inbox", "CompleteInbox");
		EmailFolderBean<String> Sent = new EmailFolderBean<String>("Sent", "CompleteSent");
			Sent.getChildren().add(new EmailFolderBean<String>("Subfoler1", "Subfoler1"));
			Sent.getChildren().add(new EmailFolderBean<String>("Subfoler2", "Subfoler2"));
		EmailFolderBean<String> Spam = new EmailFolderBean<String>("Spam", "CompleteSpam");
		barosanu.getChildren().addAll(Inbox, Sent, Spam);
		
		Inbox.getData().addAll(SampleData.Inbox);
		Sent.getData().addAll(SampleData.Sent);
		Spam.getData().addAll(SampleData.Spam);

		
		emailTableView.setContextMenu(new ContextMenu(showDetails));
		
		emailFoldersTreeView.setOnMouseClicked(e ->{
			EmailFolderBean<String> item = (EmailFolderBean<String>)emailFoldersTreeView.getSelectionModel().getSelectedItem();
			if(item != null && !item.isTopElement()){
				emailTableView.setItems(item.getData());
				getModelAccess().setSelectedFolder(item);
				//clear selected message:
				getModelAccess().setSelectedMessage(null);
			}
		});
		emailTableView.setOnMouseClicked(e->{
			EmailMessageBean message = emailTableView.getSelectionModel().getSelectedItem();
			if(message != null){
				getModelAccess().setSelectedMessage(message);
				messageRenderer.getEngine().loadContent(message.getContent());
			}
		});
		showDetails.setOnAction(e->{
			
			Scene scene = viewfactory.getEmailDetailsScene();
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});		
		
		
	}
	

}