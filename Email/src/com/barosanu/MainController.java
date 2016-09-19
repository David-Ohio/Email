package com.barosanu;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainController implements Initializable{
	
    @FXML
    private TreeView<String> emailFoldersTreeView;
    private TreeItem<String> root  = new TreeItem<String>();
    private SampleData sampleData = new SampleData();
    private MenuItem showDetails = new MenuItem("show details");
    private Singleton singleton;
	
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

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = Singleton.getIntance();
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
		root.setGraphic(resolveIcon(root.getValue()));
		
		TreeItem<String> Inbox = new TreeItem<String>("Inbox", resolveIcon("Inbox"));
		TreeItem<String> Sent = new TreeItem<String>("Sent", resolveIcon("Sent"));
			TreeItem<String> Subitem1 = new TreeItem<String>("Subitem1", resolveIcon("Subitem1"));
			TreeItem<String> Subitem2 = new TreeItem<String>("Subitem2",resolveIcon("Subitem2"));
			Sent.getChildren().addAll(Subitem1, Subitem2);
		TreeItem<String> Spam = new TreeItem<String>("Spam", resolveIcon("Spam"));
		TreeItem<String> Trash = new TreeItem<String>("Trash", resolveIcon("Trash"));
		
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
				messageRenderer.getEngine().loadContent(message.getContent());
				singleton.setMessage(message);
			}
		});
		showDetails.setOnAction(e->{
			
			Pane pane = new Pane();
			try {
				pane = FXMLLoader.load(getClass().getResource("EmailDetailsLayout.fxml"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
			
			Scene scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});
		
		
		
		
		
	}
	
	private Node resolveIcon(String treeItemValue){
		String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
		ImageView returnIcon;
			try {
				if(lowerCaseTreeItemValue.contains("inbox")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/inbox.png")));
				} else if(lowerCaseTreeItemValue.contains("sent")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/sent2.png")));
				} else if(lowerCaseTreeItemValue.contains("spam")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/spam.png")));
				} else if(lowerCaseTreeItemValue.contains("@")){
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/email.png")));
				} else{
					returnIcon= new ImageView(new Image(getClass().getResourceAsStream("images/folder.png")));
				}
			} catch (NullPointerException e) {
				System.out.println("Invalid image location!!!");
				e.printStackTrace();
				returnIcon = new ImageView();
			}
			
			returnIcon.setFitHeight(16);
			returnIcon.setFitWidth(16);

		return returnIcon;
	}

}
