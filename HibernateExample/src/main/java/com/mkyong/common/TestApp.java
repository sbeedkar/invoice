package com.mkyong.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.hibernate.Session;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.mkyong.persistence.HibernateUtil;


import javaMail.MailWithoutAttachments;

public class TestApp {
	
	public static final String HTML = "D:/htmlToinvoice/testimageo1.html";

	/* main */
	public static void main(String[] args) throws IOException, DocumentException {
	/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		/* next, get the Template */
		Template t = ve.getTemplate("./src/main/java/velocityhtml/imagetest.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		// context.put("img", "olympics_logo.jpg");
		/* now render the template into a StringWriter */
		 
		StringWriter writer1 = new StringWriter();
		t.merge(context, writer1);//
		
		BufferedWriter out = new BufferedWriter(new FileWriter(HTML)); // convert  it to html
		out.write(writer1.toString()); // Here you pass your output
		out.close();
			/******** send mail *********/
		MailWithoutAttachments m = new MailWithoutAttachments();
		m.invoiceimagemail(writer1.toString());
		
	}
}
