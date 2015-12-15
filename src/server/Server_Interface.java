package server;

import content.Content;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server_Interface extends Remote
{
 public List<Content> showAllContents()throws RemoteException;
 public String upload(String description, Content content) throws RemoteException, IOException;
 public Content getContent(String description) throws RemoteException,  UnsupportedEncodingException;
 public Content getContent(Long id) throws RemoteException, UnsupportedEncodingException;
 public String getDescription(Long id) throws RemoteException, UnsupportedEncodingException;
 public String getSimilar(String description) throws RemoteException, UnsupportedEncodingException;
}
