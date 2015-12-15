
package client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import client_register.Register_Interface;

public class ClientAPP 
{     
 private static String name;
 private static String ip = "localhost";
 private static int port;

/**
 * Creates a registry on a specified port, rebinds the registry and creates
 * a new Client process
 * @param args
 * @throws RemoteException
 * @throws NotBoundException
 * @throws MalformedURLException
 * @throws IOException 
 */
	
 public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, IOException
    {/*
      if(System.getSecurityManager() == null)
        {
         try
            {
             System.setSecurityManager(new RMISecurityManager());
            }
         catch (Exception ex)
            {
             System.out.println(ex);
             System.exit(0);
            }
        }
        */
     System.setProperty("java.rmi.server.hostname", ip); 
   
     Scanner scanIn = new Scanner(System.in);
     System.out.println("Enter your port");
     while(true)
        {
         try
            {
             port = Integer.parseInt(scanIn.nextLine());
             break;
            }
         catch(Exception Ex)
            {
             System.out.println("Port format not correct. Try again please");
            }
        }
     
     Registry registry = LocateRegistry.createRegistry(port);
     System.out.println("Enter the name associate to your object");
     name = scanIn.nextLine();
     Client c = new Client();
     registry.rebind(name, c);
     registerClient();
     c.run(name, Integer.toString(port));
    }
 
 private static void registerClient()
 	{
	 try
	 	{
		 Register_Interface mt = (Register_Interface)Naming.lookup("rmi://localhost:5000/register");
		 mt.uploadClientURL("rmi://" + ip + ":" + port + "/" + name);
	 	}
	 
	 catch(Exception ex)
	 	{
		 System.out.println("Could not register the client in the register");
		 System.exit(0);
	 	}
 	}
}
