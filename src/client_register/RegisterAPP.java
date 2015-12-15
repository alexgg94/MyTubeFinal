package client_register;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.Server;

public class RegisterAPP 
{
 public static void main(String[] args) throws RemoteException
 	{
	 /*
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
	 int port = 5000;
	 String ip = "localhost";
	 System.setProperty("java.rmi.server.hostname", ip);    
     Registry registry = LocateRegistry.createRegistry(port); 
     registry.rebind("register", new Register());
     System.out.println("Register working");
 	} 
}
