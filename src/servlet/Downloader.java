package servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import content.CloudContent;

public class Downloader extends HttpServlet
{
	 public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException 
	    {
	     PersistenceManager pm = PMF.get().getPersistenceManager();
	     String method = req.getParameter("method");
         String toSearch = req.getParameter("toSearch").replaceAll("%20", " ");
	     Query query = pm.newQuery(CloudContent.class);
         List<CloudContent> cloud_contents = null;
         CloudContent tmp2;
         String tmp;
         String location = "";
         try 
            {
             cloud_contents = (List<CloudContent>) query.execute();
            } 
         finally 
            {
             query.closeAll();
            }
         
	     if(method.equals("getContentByDescription"))
	        {
	    	 Iterator<CloudContent> it = cloud_contents.listIterator();
	    	 while(it.hasNext())
		 		{
	    		 tmp2 = it.next();
	    		 System.out.println("Aqui " + tmp2.getDescription() + "\t" + toSearch);
	    		 if(tmp2.getDescription() != null)
	    		 if(tmp2.getDescription().equalsIgnoreCase(toSearch))
		 			{
	    			 location = tmp2.getLocation();
	    			 break;
			 		}
		 		}
	    	 
			 res.setContentType("text/html");
			 res.setHeader("location", location);
			 res.getWriter().println(location);
	        }
	     
          else if(method.equals("getContentById") || method.equals("getDescriptionById"))
          	{
        	 Iterator<CloudContent> it = cloud_contents.listIterator();
 	    	 while(it.hasNext())
 		 		{
 	    		 tmp2 = it.next();
 	    		 if(tmp2.getId() != null)
 	    		 if(tmp2.getId().equals(Long.valueOf(toSearch).longValue()))
 		 			{
 	    			 location = tmp2.getLocation();
 	    			 break;
 			 		}
 		 		}
 	    	 
 			 res.setContentType("text/html");
 			 res.setHeader("location", location);
 			 res.getWriter().println(location);
          	}
          
          else if(method.equals("getSimilar"))
          	{
        	 StringTokenizer st = new StringTokenizer(toSearch);
        	 while(st.hasMoreTokens())
 		 		{
        		 tmp = st.nextToken();
        		 if(tmp.length() > 3)
 	         		{ 
        			 Iterator<CloudContent> it = cloud_contents.listIterator(); 
        			 while(it.hasNext())
 				  		{
        				 tmp2 = it.next();
        				 if(tmp2.getDescription() != null)
        				 if(tmp2.getDescription().toLowerCase().contains(tmp.toLowerCase()))
 				     		{
        					 location = location + tmp2.getDescription();
        					 location = location + " -> " + tmp2.getLocation() + "%20";
 				     		}
 				  		}
 	         		}
 		 		}
        	 res.setContentType("text/html");
 			 res.setHeader("location", location);
 			 res.getWriter().println(location);
          	}
	     
          else if(method.equals("getDescription"))
          	{
        	 Iterator<CloudContent> it = cloud_contents.listIterator(); 
 			 while(it.hasNext())
			  	{
			 	 tmp2 = it.next();
		    	 if(tmp2.getDescription() != null)
		    		 if(tmp2.getDescription().equalsIgnoreCase(toSearch))
			 			{
		    			 location = tmp2.getLocation();
		    			 break;
				 		}
		 		}
 		     res.setContentType("text/html");
 			 res.setHeader("location", location);
 			 res.getWriter().println(location);
	  		}
	    }
}	
 

