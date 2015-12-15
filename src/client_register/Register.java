package client_register;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class Register extends UnicastRemoteObject implements Register_Interface
{
 private List<String> clientsURL = new ArrayList();	
 public Register() throws RemoteException {super();}
 
 @Override
 public List<String> getClientsURL() throws RemoteException 
 	{
 	 return this.clientsURL;
	}

 @Override
 public void uploadClientURL(String url) throws RemoteException 
 	{
	 this.clientsURL.add(url);
	 System.out.println("New client has been registered: " +url);
	}

 @Override
 public void deleteClientURL(String url) throws RemoteException 
 	{
	 this.clientsURL.remove(url);
	 System.out.println("A client has been deleted: " +url);
	}
}
