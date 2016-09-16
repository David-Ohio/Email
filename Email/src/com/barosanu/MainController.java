package com.barosanu;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

public class MainController implements Initializable{
	
    @FXML
    private TreeView<String> emailFoldersTreeView;
    private TreeItem<String> root  = new TreeItem<String>();
	
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
    
    
    final ObservableList<EmailMessageBean> data = FXCollections.observableArrayList(
    		new EmailMessageBean("Hello from Sefu!!!","aaa@yahoo.com", 5500000),
    		new EmailMessageBean("Hello from Barosanu","bbb@yahoo.com", 200),
    		new EmailMessageBean("Hello from Sefu!!!asdas","ccc@yahoo.com", 10),
    		new EmailMessageBean("Hello from Barosanuasdasas","ddd@yahoo.com", 6300)
    		
    		);

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		messageRenderer.getEngine().loadContent("<html>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsucomes from a line in section 1.10.32.</html>");
		
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("size"));
		
		emailTableView.setItems(data);
		
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
