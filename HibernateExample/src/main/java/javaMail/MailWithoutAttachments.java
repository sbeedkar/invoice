package javaMail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;

public class MailWithoutAttachments {
	final Properties prop = System.getProperties();

	public void invoiceimagemail(String velocitytemp) {
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/mail-settings.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Session session = Session.getDefaultInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(prop.getProperty("mail.user"), prop.getProperty("mail.passwd"));
			}
		});

		try {
			// Creating default MIME message object
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(prop.getProperty("mail.from")));
			// Recipient : To
			int counter = 0;
			String recipient = prop.getProperty("mail.to");
			String[] recipientList = recipient.split(",");
			InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
			System.out.println("no multiple user " + recipientList.length);
			for (String to : recipientList) {
				System.out.println("to List " + to);
				recipientAddress[counter] = new InternetAddress(to);
				counter++;
			}
			message.addRecipients(Message.RecipientType.TO, recipientAddress);
			/*
			 * message.addRecipient(Message.RecipientType.CC, new
			 * InternetAddress(prop.getProperty("mail.cc")));
			 */
			message.setSubject("Test mail through simple java API !!");

			MimeMultipart multipartmail = new MimeMultipart();
			BodyPart msgbody = new MimeBodyPart();
			msgbody.setContent(velocitytemp, "text/html");// add template
			multipartmail.addBodyPart(msgbody);
		

			BodyPart imagebody = new MimeBodyPart();
		/*	StringBuffer imgPath = new StringBuffer()
					.append("C:/Users/SBeedkar/Downloads/HibernateExample/src/main/resources/images/silicus.png");*/
		//	DataSource fds = new FileDataSource(VelocityEngine.);
		//	imagebody.setDataHandler(new DataHandler(fds));
			imagebody.setHeader("Content-ID", "<123>");
			multipartmail.addBodyPart(imagebody);

			message.setContent(multipartmail);
		
			// Send mail
			Transport.send(message);

			System.out.println("mail send!!");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
