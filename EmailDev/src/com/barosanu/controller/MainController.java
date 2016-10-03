package com.barosanu.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.barosanu.controller.services.CreateAndRegisterEmailAccountService;
import com.barosanu.model.EmailMessageBean;
import com.barosanu.model.folder.EmailFolderBean;
import com.barosanu.model.table.BoldableRowFactory;
import com.barosanu.model.table.FormatableInteger;
import com.barosanu.view.ViewFactory;

import DONOTCOMMIT.DONOTCOMMIT;
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
    private TableColumn<EmailMessageBean, FormatableInteger> sizeCol;
    
    @FXML
    private TableColumn<EmailMessageBean, Date> dateCol;
	
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
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		emailTableView.setRowFactory(e-> new BoldableRowFactory<>());
		ViewFactory viewfactory = ViewFactory.defaultFactory;
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, FormatableInteger>("size"));	
		dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, Date>("date"));
		
		//BUG: sizeCol doesn't get it's default comparator overridden, have to do this manually!!!
		sizeCol.setComparator(new FormatableInteger(0));	
		
		EmailFolderBean<String> root = new EmailFolderBean<String>("");
		emailFoldersTreeView.setRoot(root);		
		emailFoldersTreeView.setShowRoot(false);
		
		CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService1 = 
				new CreateAndRegisterEmailAccountService(DONOTCOMMIT.address1, 
				DONOTCOMMIT.password1,
				root);
		createAndRegisterEmailAccountService1.restart();
		createAndRegisterEmailAccountService1.setOnSucceeded(e->{
			System.out.println(createAndRegisterEmailAccountService1.getValue());
		});
		CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService2 = 
				new CreateAndRegisterEmailAccountService(DONOTCOMMIT.address2, 
				DONOTCOMMIT.password2,
				root);
		createAndRegisterEmailAccountService2.restart();
		
		
		
		
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
