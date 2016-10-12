package com.barosanu;

import com.barosanu.model.EmailAccountBean;
import com.barosanu.model.EmailMessageBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test {
	
	public static void main(String[] args) {
		final EmailAccountBean emailAccountBean = new EmailAccountBean("barosanuemailtest@gmail.com", "asfasdasdaAA123a");

		ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
	}

}
