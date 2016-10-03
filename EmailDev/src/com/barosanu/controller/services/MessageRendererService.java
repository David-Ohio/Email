package com.barosanu.controller.services;

import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import com.barosanu.model.EmailMessageBean;

import javafx.scene.web.WebEngine;

public class MessageRendererService implements Runnable{
	
	private EmailMessageBean messageToRender;
	private WebEngine messageRendererEngine;
	private List<MimeBodyPart> listOfAttachments = new ArrayList<MimeBodyPart>();
	public StringBuffer attachmentsNames  =new StringBuffer();

	public MessageRendererService(WebEngine messageRendererEngine) {
		this.messageRendererEngine = messageRendererEngine;
	}
	
	public void setMessageToRender(EmailMessageBean messageToRender){
		this.messageToRender = messageToRender;
	}

	@Override
	public void run() {
		listOfAttachments.clear();
		attachmentsNames.setLength(0);//this clears the String buffer
		Message message = messageToRender.getMessageRefference();
		try {
			String messageType = message.getContentType();
			if(messageType.contains("TEXT/HTML") || messageType.contains("TEXT/PLAIN") || messageType.contains("text")){
				messageRendererEngine.loadContent(message.getContent().toString());

			} else if(messageType.contains("multipart")){
				Multipart mp = (Multipart)message.getContent();
				StringBuffer sb = new StringBuffer();
				for (int i = mp.getCount()-1; i >= 0; i--) {
					BodyPart bp = mp.getBodyPart(i);
					String contentType = bp.getContentType();
					if(contentType.contains("TEXT/HTML") || contentType.contains("TEXT/PLAIN") || contentType.contains("mixed")|| contentType.contains("text")){
						//Here the risk of incomplete messages are shown, for messages that contain both 
						//html and text content, but these messages are very rare;
						if (sb.length()==0) {
							sb.append(bp.getContent().toString());
						}
						
					//here the attachments are handled
					}else if(contentType.contains("APPLICATION") || contentType.contains("application")){
							MimeBodyPart mbp = (MimeBodyPart)bp;
							listOfAttachments.add(mbp);
							attachmentsNames.append(mbp.getFileName() + " ");
							
					//Sometimes the text content of the message is encapsulated in another multipart,
					//so we have to iterate again through it.		
					}else if(bp.getContentType().contains("multipart")){
						Multipart mp2 = (Multipart)bp.getContent();
						for (int j = mp2.getCount()-1; j >= 0; j--) {
							BodyPart bp2 = mp2.getBodyPart(i);
							if((bp2.getContentType().contains("TEXT/HTML") || bp2.getContentType().contains("TEXT/PLAIN") ) ){
								sb.append(bp2.getContent().toString());
						    }
					    }
				   }  
			}
				messageRendererEngine.loadContent(sb.toString());
			}
		} catch (Exception e) {
			System.out.println("Exception while vizualizing message: ");
			e.printStackTrace();		
		}	
		
	}

}
