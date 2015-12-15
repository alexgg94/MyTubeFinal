package client_register;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Register_Interface extends Remote
{
 public List<String> getClientsURL() throws RemoteException;
 public void uploadClientURL(String url) throws RemoteException;
 public void deleteClientURL(String url) throws RemoteException;
}
