package com.barosanu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Pane pane = new FlowPane();		
		Label label = new Label("Label");
		Button button = new Button("just a button");		
		pane.getChildren().addAll(label, button);		
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();		
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println("pressing the button!!");
				
			}
		});
		
	}

}
