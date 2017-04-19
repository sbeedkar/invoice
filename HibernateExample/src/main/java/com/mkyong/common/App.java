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
import javaMail.MailApp;
import javaMail.MultyAttachmentMail;

public class App {
	
	public static final String DEST = "D:/htmlToinvoice/silicus70.pdf";
	public static final String HTML = "D:/htmlToinvoice/dynamichtmls1ilicus70.html";
	
	//////////////////// create PDF

	public void createPdf(String file) throws IOException, DocumentException {
		// step 1
		Document document = new Document();
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		// step 3
		document.open();
		// step 4
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(HTML));
		// step 5
		document.close();
	}

	/* main */
	public static void main(String[] args) throws IOException, DocumentException {
		System.out.println("Maven + Hibernate + MySQL");
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Info info = new Info();
		String hql = "from Info s where s.empName =:empName";

		info = (Info) session.createQuery(hql).setParameter("empName", "p904").uniqueResult();
		System.out.println("jjjjj: " + info.getCompanyName());

		//////////////////////////////////////////////////// create dynamic
		//////////////////////////////////////////////////// html/////////
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		/* next, get the Template */
		Template t = ve.getTemplate("./src/main/java/velocityhtml/test.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		context.put("id", info.getId());
		context.put("currentTime", new Date());
		context.put("CompanyName", info.getCompanyName());
		context.put("empName", info.getEmpName());
		//context.put("image", "C:/Users/SBeedkar/Downloads/HibernateExample/src/main/resources/images/silicus.png");
	    context.put("dateOfJoining", info.getJoinDate());
		/* now render the template into a StringWriter */
		StringWriter writer1 = new StringWriter();
    	t.merge(context, writer1);
	
		
		// System.out.println( writer1.toString() ); show the World

		BufferedWriter out = new BufferedWriter(new FileWriter(HTML)); // convert  it to html
		out.write(writer1.toString()); // Here you pass your output
		out.close();
		// call pdf convertor
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		new App().createPdf(DEST);

		/******** send mail *********/

		MailApp m = new MailApp();
		m.invoicemail(writer1.toString(), DEST);
		//MultyAttachmentMail m2= new MultyAttachmentMail();
	//	m2.invoicemail(writer1.toString(), DEST);
	}

}
