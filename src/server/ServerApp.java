package server;

//C:\\Users\\gsl\\Desktop\\dibujo

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Creates a registry on a specified port, rebinds the registry and creates 
 * a new Server process
 * @throws RemoteException 
 * @throws FileNotFoundException
 * @throws IOException 
 */
public class ServerApp {
    public static void main(String[] args) throws RemoteException, FileNotFoundException, IOException
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
        } */  
     
     int port = 5091;
     String ip = "localhost";
     
     System.setProperty("java.rmi.server.hostname", ip);    
     Registry registry = LocateRegistry.createRegistry(port); 
     Server s = new Server();
     registry.rebind("mytube", s);
     s.setUrl("rmi://" + ip + ":" + port + "/mytube");
     s.setName(ip+port);
     System.out.println("Server has started successfully on port " +port+ " ip " +ip);
    } 
}

