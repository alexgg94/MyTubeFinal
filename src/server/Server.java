package server;

import client.Client_Interface;
import client_register.Register_Interface;
import content.CloudContent;
import content.Content;
import image.ImageProcessor;
import servlet_access.Access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Server extends UnicastRemoteObject implements Server_Interface
{
 private final Map<Long, String> m1 = new HashMap();
 private final Map<String, Content> m2 = new HashMap();
 private final List<Client_Interface> clients = new ArrayList();
 private Register_Interface register;
 private String name;
 private String url;
    
 public Server() throws RemoteException {super(); getRegister();}

 /**
  * Remote method
  * Uploads to the server a content and it's description
  * @param description
  * @param content
  * @return Long
  * @throws RemoteException
  * @throws IOException 
  */
 @Override
 public String upload(String description, Content content) throws RemoteException ,IOException
	{  
	 String tmp = checkDescription(description);
	 if(tmp.equals(""))
	 	{
		 CloudContent cloud_content = new CloudContent(description, this.url);	 
		 Long key = Access.accessUploader(cloud_content); 		 
		 ImageProcessor.ByteToImage(content.getImageInByte(), content.getName()+content.getYear());
		 content.deleteImageFromMemory();
	     m1.put(key, description);   
	     m2.put(description, content);
    
	     content.setId(key);
	     System.out.println("New content has been uploaded: \n"+content.toString());

	     this.update(key, description, content); 
	     this.notifyClients(content);
	     return "\n";
	 	}
	 
	 else
	 	{
		 System.out.println("A client tried to upload an already existent content");
		 return "Content with the same description already uploaded\nYou can find it -> "+tmp;
	 	}
	}

 /**
  * Remote method
  * Returns the content that has the same description as the parameter
  * if it exists
  * @param description
  * @return Content
  * @throws RemoteException 
  */
 @Override
 public Content getContent(String description) throws RemoteException, UnsupportedEncodingException
	{
	 String result;
     try
        {
    	 Content c = m2.get(description);
    	 c.uploadImageInMemory(ImageProcessor.ImageToByte(c.getName()+c.getYear()));
         return c;
        }
    
     catch(Exception ex) 
        {
    	 result = Access.accessDownloader("getContentByDescription", description);
    	 if(result.equals(""))
   			{
    		 return null;
   			}
   
    	 else
   			{
    		 Content c = new Content();
    		 c.setUbication("You can find that content \t->\t"+result);
    		 return c;
   			}
        }
	}

 /**
  * Remote method
  * Returns the content that has the same id as the parameter
  * if it exists
  * @param id
  * @return Content
  * @throws RemoteException 
  */
 @Override
 public Content getContent(Long id) throws RemoteException, UnsupportedEncodingException
	{  
	 String result;
     try
        {
    	 Content c = m2.get(m1.get(id));
    	 c.uploadImageInMemory(ImageProcessor.ImageToByte(c.getName()+c.getYear()));
         return c;
        }
    
     catch(Exception ex) 
        {
         result = Access.accessDownloader("getContentById", Long.toString(id));    	 
         if(result.equals(""))
      		{
        	 return null;
      		}
      
         else
      		{
        	 Content c = new Content();
    		 c.setUbication("You can find that content \t->\t"+result);
    		 return c;
      		}
        }
	}

 /**
  * Remote method
  * Returns the description that has the same id as the parameter
  * if it exists
  * @param id
  * @return String
  * @throws RemoteException 
  */
 @Override
 public String getDescription(Long id) throws RemoteException, UnsupportedEncodingException
	{
	 String result = m1.get(id);
	 
	 if(result != null)
	 	{
		 return result;
	 	}
	 
	 else
	 	{
		 result = Access.accessDownloader("getDescriptionById", Long.toString(id));;
         if(result.equals(""))
         	{
        	 return null;
         	}
         
         else
         	{
        	 return "You can find that content \t->\t"+result;
         	}
	 	}
	}

 /**
  * Remote method
  * Returns a string of contents that have a similar description of the parameter.
  * @param description
  * @return String
  * @throws RemoteException 
  */
 @Override
 public String getSimilar(String description) throws RemoteException, UnsupportedEncodingException
	{        
     return Access.accessDownloader("getSimilar", description).replaceAll(this.url, "Here");
	}

 private void notifyClients(Content content) throws RemoteException
    {
	 List<String> tmp = new ArrayList();
	 tmp = this.register.getClientsURL();
	 Iterator it = tmp.listIterator();

	 while(it.hasNext())
	 	{
		 String url = it.next().toString();
		 try
		 	{
			 this.clients.add((Client_Interface)Naming.lookup(url));
		 	}
		 
		 catch(Exception ex)
		 	{
			 register.deleteClientURL(url);
		 	}
	 	}
	 
	 it = this.clients.listIterator();
	 while(it.hasNext())
	 	{
		 try
		 	{
			 Client_Interface ci = (Client_Interface) it.next();
			 ci.getNotified(content.toString() + "You can find it here -> " + this.url + "\n");
		 	}
		 
		 catch(Exception ex)
		 	{
			 System.out.println("Could not notify some clients");
		 	}
	 	}
    }
  
 /**
  * Returns all the contents
  */
 public List<Content> showAllContents() throws RemoteException
 	{
	 List<Content> tmp = new ArrayList();
	 tmp.addAll(m2.values());
	 return tmp;
 	}
 
 private void update(Long key, String description, Content content) throws IOException
    {
     BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(this.name, true));
     bufferedwriter.write(Long.toString(key));
     bufferedwriter.newLine();
     bufferedwriter.write(description);
     bufferedwriter.newLine();
     bufferedwriter.write(content.save());
     bufferedwriter.newLine();
     bufferedwriter.close();
    }
    
 private void load() throws FileNotFoundException, IOException
    { 
     String line1;
     String line2;
     String line3;
     String [] line3_splitted;
         
     File bbdd = new File(this.name);

     if (!bbdd.isFile() && !bbdd.createNewFile())
        {
         System.out.println("Could nou load the database");
        }
         
     BufferedReader bufferedReader = new BufferedReader(new FileReader(bbdd));
     while((line1 = bufferedReader.readLine()) != null) 
        {
         String name = "";   
         line2 = bufferedReader.readLine();  
         line3 = bufferedReader.readLine();
         line3_splitted = line3.split(" ");

         for(int i = 0; i < line3_splitted.length -2; i++)
            {
             name += line3_splitted[i]+" ";
            }
 
         Content tmp = new Content(name.substring(0, name.length()-1), Integer.parseInt(line3_splitted[line3_splitted.length -2]), 
                 Double.parseDouble(line3_splitted[line3_splitted.length -1]));
         
         tmp.setId(Long.valueOf(line1).longValue());
         
         m1.put(Long.valueOf(line1).longValue(), line2);
         m2.put(line2, tmp);
        }

    }
    

 public void setName(String name) throws FileNotFoundException, IOException
	{
	 this.name = name;
	 this.load();
	}
 
 public void setUrl(String url)
 	{
	 this.url = url;
 	}
 
  private void getRegister()
  	{
	 try
	 	{
		 this.register = (Register_Interface)Naming.lookup("rmi://localhost:5000/register");
	 	}
	 
	 catch(Exception ex)
	 	{
		 System.out.println("Could not get the register");
		 System.exit(0);
	 	}
  	}
  
  private String checkDescription(String description) throws UnsupportedEncodingException
  	{
	 return Access.accessDownloader("getDescription", description);
  	}
}

