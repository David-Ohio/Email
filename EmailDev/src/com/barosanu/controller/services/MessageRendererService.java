package com.barosanu.controller.services;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import com.barosanu.model.EmailMessageBean;

import javafx.scene.web.WebEngine;

public class MessageRendererService implements Runnable{
	
	private EmailMessageBean messageToRender;
	private WebEngine messageRendererEngine;

	public MessageRendererService(WebEngine messageRendererEngine) {
		this.messageRendererEngine = messageRendererEngine;
	}
	
	public void setMessageToRender(EmailMessageBean messageToRender){
		this.messageToRender = messageToRender;
	}

	@Override
	public void run() {
		
		EmailMessageBean.attachementsLabelValue.set("");
		EmailMessageBean.attachmentsBtnVisible.set(false);
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
					}else if(contentType.toLowerCase().contains("application")){
							MimeBodyPart mbp = (MimeBodyPart)bp;
							//TODO: find a way to inform UI about this:
							messageToRender.getListOfAttachments().add(mbp);
							messageToRender.getAttachmentsNames().append(mbp.getFileName() + " ");
							messageToRender.setHasAttachments(true);
							
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
				EmailMessageBean.attachementsLabelValue.set(messageToRender.getAttachmentsNames().toString());
				if(messageToRender.isHasAttachments()){
					EmailMessageBean.attachmentsBtnVisible.set(true);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception while vizualizing message: ");
			e.printStackTrace();		
		}	
		
	}

}
