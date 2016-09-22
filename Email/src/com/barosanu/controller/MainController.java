package com.barosanu.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.barosanu.model.EmailMessageBean;
import com.barosanu.model.SampleData;
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
import javafx.scene.control.TreeItem;
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
    private TreeItem<String> root  = new TreeItem<String>();
    private SampleData sampleData = new SampleData();
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
		
		
		
		emailFoldersTreeView.setRoot(root);
		
		root.setValue("example@yahoo.com");
		root.setGraphic(viewfactory.resolveIcon(root.getValue()));
		
		TreeItem<String> Inbox = new TreeItem<String>("Inbox", viewfactory.resolveIcon("Inbox"));
		TreeItem<String> Sent = new TreeItem<String>("Sent", viewfactory.resolveIcon("Sent"));
			TreeItem<String> Subitem1 = new TreeItem<String>("Subitem1", viewfactory.resolveIcon("Subitem1"));
			TreeItem<String> Subitem2 = new TreeItem<String>("Subitem2",viewfactory.resolveIcon("Subitem2"));
			Sent.getChildren().addAll(Subitem1, Subitem2);
		TreeItem<String> Spam = new TreeItem<String>("Spam", viewfactory.resolveIcon("Spam"));
		TreeItem<String> Trash = new TreeItem<String>("Trash", viewfactory.resolveIcon("Trash"));
		
		root.getChildren().addAll(Inbox, Sent, Spam, Trash);
		root.setExpanded(true);
		
		emailTableView.setContextMenu(new ContextMenu(showDetails));
		
		emailFoldersTreeView.setOnMouseClicked(e ->{
			TreeItem<String> item = emailFoldersTreeView.getSelectionModel().getSelectedItem();
			if(item != null){
				emailTableView.setItems(sampleData.emailFolders.get(item.getValue()));
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
