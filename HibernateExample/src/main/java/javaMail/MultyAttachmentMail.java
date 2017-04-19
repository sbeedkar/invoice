package javaMail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

public class MultyAttachmentMail {


	public  void invoicemail(String velocitytemp,String filename) {
	
	
		final Properties prop = System.getProperties();

		try {
		prop.load(new FileInputStream(new File("./src/main/resources/mail-settings.properties")));
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}

		Session session = Session.getDefaultInstance(prop,new Authenticator() {

		protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(
		prop.getProperty("mail.user"), prop.getProperty("mail.passwd"));
		}
		});

		try{
		// Creating default MIME message object
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(prop.getProperty("mail.from")));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(prop.getProperty("mail.to")));
		/*message.addRecipient(Message.RecipientType.CC, new InternetAddress(prop.getProperty("mail.cc")));*/
		message.setSubject("Test mail through simple java API !!");

		BodyPart body = new MimeBodyPart();
		BodyPart body1 = new MimeBodyPart();// Attachment

		body.setContent(velocitytemp,"text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(body);

		
	//	String filename ="D:/htmlToinvoice/finalfile.pdf";
		/*DataSource source = new FileDataSource(filename);
		body1.setDataHandler(new DataHandler(source));
		body1.setFileName("Attachment");*/
		
		Multipart multipartmail = new MimeMultipart();
		//multipartmail.addBodyPart(body1);//attached file 
		multipartmail.addBodyPart(body);
	////////////////////////////////////
		int counter = 0;
		String attachments =prop.getProperty("mail.attachments");
		System.out.println(" list of pdfs "+attachments);
		String [] attachmentList = attachments.split(",");
	
		String [] pdfslist = new String[attachmentList.length];
		
		
		System.out.println("no pdfs "+ attachmentList.length);
		for ( String pdfs : attachmentList) {
			System.out.println("to List "+pdfs);
			pdfslist[counter] = new String(pdfs);		
		    counter++;
		}
		
		
		
	    File dir = new File(attachments);
	  //  String list[] = dir.list();

	    for (int i=0, n=pdfslist.length; i<n; i++) {
	      File f = new File(pdfslist[i]);
	      if (f.isFile()) {
	        System.out.println("Adding: " + pdfslist[i]);
	      
	     //   messageBodyPart = new MimeBodyPart();
	        
	        DataSource source = new FileDataSource(pdfslist[i]);
	        body1.setDataHandler(new DataHandler(source));
	        body1.setFileName(pdfslist[i]);
	        multipartmail.addBodyPart(body1);
	        }
	    }
	
		
		///////////////////////////
		message.setContent(multipartmail);
		Transport.send(message);	// Send mail
		System.out.println("mail send!!");

		}catch(Exception ex){
		ex.printStackTrace();
		}

		}
}
