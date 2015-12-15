package servlet;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.*;
import content.CloudContent;

@SuppressWarnings("serial")
public class Uploader extends HttpServlet 
{
 public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException 
 	{
	 String description = req.getParameter("description");
	 String location = req.getParameter("location");
	 PersistenceManager pm = PMF.get().getPersistenceManager();
	 CloudContent c = new CloudContent(description, location);
	 try 
	 	{
		 pm.makePersistent(c);
		 Long id = c.getId();
		 res.setContentType("text/html");
         res.setHeader("id" , Long.toString(id));
         res.getWriter().println(Long.toString(id));
	 	} 
	 
	 finally
	 	{
		 pm.close();
	 	}
 	}
}
