package client;

import content.Content;
import image.ImageProcessor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import server.Server_Interface;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;


public class Client extends UnicastRemoteObject implements Client_Interface{

    protected Client() throws RemoteException, NotBoundException, MalformedURLException ,IOException
        {
         super();
        }

    /**
     * Gets the server's object and asks the client about which operation he wants
     * to use each time
     * @param user_name
     * @param user_port
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     * @throws IOException 
     */
    protected void run(String user_name , String user_port) throws NotBoundException, MalformedURLException, RemoteException,IOException
    {
        try
        {
         Server_Interface mt = (Server_Interface)Naming.lookup("rmi://localhost:5091/mytube");
         /*
         if(!mt.subscribe(user_name, user_port))
            {
             System.out.println("Could not subscribe");
             System.exit(0);
            }
         */
         String input;
         Scanner scanIn = new Scanner(System.in);  
         while(true)
            {
              System.out.println("What would you like to do?");
              System.out.println("1 - Upload Content");
              System.out.println("2 - Get content by description");
              System.out.println("3 - Get content by id");
              System.out.println("4 - Get Description");
              System.out.println("5 - Get contents with similar description");
              System.out.println("6 - See current servers' contents");
              System.out.println("7 - Change server");
              System.out.println("8 - Exit");

              input = scanIn.nextLine();
              
              if(input.equals("1"))
                {
                 System.out.println("Describe the content");
                 String description = scanIn.nextLine();
                 System.out.println("Content's name");
                 String name = scanIn.nextLine();
                 System.out.println("Content's released year");
                 int year;
                 while(true)
                    {
                     try
                        {
                         year = Integer.parseInt(scanIn.nextLine());
                         break;
                        }
                     catch(Exception Ex)
                        {
                         System.out.println("Year format not correct. Try again please");
                        }
                    }
                 System.out.println("Content's duration");
                 double duration;
                 while(true)
                    {
                     try
                        {
                         duration = Double.parseDouble(scanIn.nextLine());
                         break;
                        }
                     catch(Exception Ex)
                        {
                         System.out.println("Duration format not correct. Try again please");
                        }
                    }
                 
                 System.out.println("Image URL");
                 String imageURL = scanIn.nextLine();
                 try
                 	{
                	 Content c = new Content(name, year, duration);
                	 c.uploadImageInMemory(ImageProcessor.ImageToByte(imageURL));
                     System.out.println(mt.upload(description, c));
                 	}
                 
                 catch(Exception ex)
                 	{
                	 System.out.println("Found some problems, try again");
                 	}
                }
              
              else if(input.equals("2"))
                {
                 System.out.println("Introduce the description of the content");
                 String description = scanIn.nextLine();
                 
                 Content c = mt.getContent(description);
                 
                 if(c != null)
              		{
                	 if(c.getUbication().equals(""))
             	 		{
                		 System.out.println(c.toString());
                		 System.out.println("Introduce a name for the image");
                		 String name = scanIn.nextLine();
                		 ImageProcessor.ByteToImage(c.getImageInByte(), name);
                		 System.out.println("The image has been stored on your filesystem");
             	 		}
             	 
                	 else
             	 		{
                		 System.out.println(c.getUbication());
             	 		}
              		}
                        
                 else
                	 System.out.println("Could not find such description"); 
                }
              
              else if(input.equals("3"))
                {
                 System.out.println("Introduce the id of the content");
                 Long id;
                 while(true)
                    {
                     try
                        {
                    	 id = Long.valueOf(scanIn.nextLine()).longValue();
                         break;
                        }
                     catch(Exception Ex)
                        {
                         System.out.println("Id format not correct. Try again please");
                        }
                    }
                 
                 Content c = mt.getContent(id);
                 
                 if(c != null)
                 	{
                	 if(c.getUbication().equals(""))
                	 	{
                		 System.out.println(c.toString());
                		 System.out.println("Introduce a name for the image");
                         String name = scanIn.nextLine();
                    	 ImageProcessor.ByteToImage(c.getImageInByte(), name);
                    	 System.out.println("The image has been stored on your filesystem");
                	 	}
                	 
                	 else
                	 	{
                		 System.out.println(c.getUbication());
                	 	}
                 	}
                           
                 else
                 System.out.println("Could not find such id");
                }
              
              else if(input.equals("4"))
                {
            	  System.out.println("Introduce the id of the content");
                  Long id;
                  while(true)
                     {
                      try
                         {
                          id = Long.valueOf(scanIn.nextLine()).longValue();
                          break;
                         }
                      catch(Exception Ex)
                         {
                          System.out.println("Id format not correct. Try again please");
                         }
                     }
                  
                  String result = mt.getDescription(id);
                  
                  if(result != null)
                  System.out.println(result);
                  
                  else
                  System.out.println("Could not find such id");
                }
              
              else if(input.equals("5"))
                {
                 System.out.println("Introduce the description of the content");
                 String description = scanIn.nextLine();
                 
                 String result = mt.getSimilar(description);
                 
                 if(result.equals(""))
                    {
                     System.out.println("No content with similar description");
                    }
                 
                 else
                 System.out.println(result);
                }
              
              else if(input.equals("6"))
              	{
            	 Iterator it = mt.showAllContents().listIterator();
            	 
            	 if(it.hasNext())
            		 while(it.hasNext())
            	 		{
            			 Content tmp = (Content)it.next();
            			 System.out.println(tmp.toString());
            	 		}

            	 else
            		System.out.println("No content to show"); 
              	}
              
              else if(input.equals("7"))
              	{
            	  System.out.println("Introduce the URL of the new server");
                  String url = scanIn.nextLine();
                  
                  try
                  	{
                	 mt = (Server_Interface)Naming.lookup(url); 
                	 /*
                     if(!mt.subscribe(user_name, user_port))
                         {
                          System.out.println("Could not subscribe");
                          System.exit(0);
                         } 
                     */
                     System.out.println("You have changed your server");
                  	}
                  
                  catch(Exception ex)
                  	{
                	 System.out.println("Some error with the server changing, try again"); 
                  	}
              	}
              
              else if(input.equals("8")){scanIn.close(); System.exit(0);}
              
              else{
                  System.out.println("\nThe Option is not correct.\n");
              }
              
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Remote method
     * Prints the notification received from the server
     * @param notification
     * @throws RemoteException 
     */
    @Override
    public void getNotified(String notification) throws RemoteException 
    	{
         System.out.println("\nA new content has been uploaded:\t " + notification);
    	}

}