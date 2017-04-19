package velocityhtml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class app {

	
	
	
	public static void main(String[] args)throws Exception {
			   
			        /*  first, get and initialize an engine  */
			        VelocityEngine ve = new VelocityEngine();
			        ve.init();
			        /*  next, get the Template  */
			        Template t = ve.getTemplate( "./src/main/java/velocityhtml/test.vm" );
			        
			        /*  create a context and add data */   
			        VelocityContext context = new VelocityContext();
			        context.put("id", "B0nd007");
			        context.put("currentTime", new Date());
			        context.put("CompanyName", "B0nd007");
			        context.put("empName", "B0nd007");
			        context.put("img", "C:/Users/SBeedkar/Downloads/HibernateExample/src/main/resources/images/silicus.png");
			       // context.put("logo", "./images/silicus.png");
			      //  context.put("noOfDocs", "B0nd007");
			        context.put("dateOfJoining", "B0nd007");
			        /* now render the template into a StringWriter */
			        StringWriter writer1 = new StringWriter();
			        t.merge( context, writer1 );
			        /* show the World */
			        System.out.println( writer1.toString() );
			        //convert it to html
			        BufferedWriter out = new BufferedWriter(new FileWriter("D:/outfilenameg99.html"));
			        out.write(writer1.toString()); //Here you pass your output
			        out.close();
			

	}

}
