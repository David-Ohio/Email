package com.barosanu.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.barosanu.model.EmailConstants;
import com.barosanu.model.EmailMessageBean;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

public class ComposeEmailController extends AbstractController implements Initializable {

	private int type = EmailConstants.STANDALONE_MESSAGE;
	private EmailMessageBean initialMessage;

	public ComposeEmailController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	/**
	 * Used for replying or forwarding messages
	 * 
	 * @param modelAccess
	 * @param initialMessage
	 * @param type
	 * @throws Exception 
	 */
	public ComposeEmailController(ModelAccess modelAccess, EmailMessageBean initialMessage, int type) throws Exception {
		super(modelAccess);
		if(initialMessage.getContentForForvarding() == null){
			throw new Exception("Content was not found in the forwarded message!!!");
		}
		this.type = type;
		this.initialMessage = initialMessage;

	}

	@FXML
	private TextField RecipientField;

	@FXML
	private TextField SubjectField;

	@FXML
	private HTMLEditor ComposeArea;

	@FXML
	private TextField CcField;

	@FXML
	private ChoiceBox<String> SenderChoice;

	@FXML
	private Label errorLabel;

	@FXML
	private Label attachNamesLabel;

	@FXML
	void AttachBtnAction() {

	}

	@FXML
	void SendBtnAction() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillInitialDetails();
		SenderChoice.setItems(getModelAccess().getEmailAccountsList());
		SenderChoice.setValue(getModelAccess().getEmailAccountsList().get(0));

	}

	private void fillInitialDetails() {
		if (type != EmailConstants.STANDALONE_MESSAGE) {
			switch (type) {
			case EmailConstants.FORWARD_MESSAGE:
				SubjectField.setText("FW: " + initialMessage.getSubject());
				break;
			case EmailConstants.REPLY_MESSAGE:
				SubjectField.setText("RE: " + initialMessage.getSubject());
				RecipientField.setText(initialMessage.getSender());
				break;
			default:
				break;
			}
			ComposeArea.setHtmlText("<br>From " + initialMessage.getSender() 
					+ "<br>Sent: " + initialMessage.getDate()
					+ "<br>To: " + initialMessage.getRecipient() 
					+ "<br>Subject: " + initialMessage.getSubject()
					+ "<br><br>" + initialMessage.getContentForForvarding());
		}

	}

}
