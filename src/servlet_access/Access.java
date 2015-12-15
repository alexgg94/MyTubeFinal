package servlet_access;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import content.CloudContent;

public class Access 
{
 /**
  * Access the servlet in charge of uploading contents
  * @param cloud_content
  * @return Long
  * @throws UnsupportedEncodingException
  */
 public static Long accessUploader(CloudContent cloud_content) throws UnsupportedEncodingException
 	{
	 try 
	 	{
		 Long id;
		 URL url = new URL("http://1-dot-southern-surge-114710.appspot.com/uploader");
		 //URL url = new URL("http://localhost:8888/uploader");
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 connection.setDoOutput(true);
		 connection.setRequestMethod("POST");
		 OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		 writer.write("description=" + cloud_content.getDescription() + "&location=" + cloud_content.getLocation());
		 writer.close();
		 
		 if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
		 	{
			 System.out.println("CloudContent uploaded successfully");
			 id = Long.valueOf(connection.getHeaderField("id")).longValue();
			 return id;
		 	}
		 
		 else 
		 	{
			 System.out.println("Could not upload CloudContent successfully");
			 return null;
		 	}
		 
		 } 

	  catch (MalformedURLException ex) 
	 	{
		 System.out.println(ex.getMessage());
		 return null;
		} 
	 
	  catch (IOException ex) 
	 	{
		 System.out.println(ex.getMessage());
		 return null;
		}
	}
 
 /**
  * Access the servlet in charge of downloading contents
  * @param method
  * @param toSearch
  * @return String
  * @throws UnsupportedEncodingException
  */
 public static String accessDownloader(String method, String toSearch) throws UnsupportedEncodingException
 	{
	 String location;
	 try 
	 	{
		 toSearch = toSearch.replaceAll(" ", "%20");
		 //URL url = new URL("http://localhost:8888/downloader?method="+method+"&toSearch="+toSearch);
		 URL url = new URL("http://1-dot-southern-surge-114710.appspot.com/downloader?method="+method+"&toSearch="+toSearch);
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 connection.setDoInput(true);
		 connection.setDoOutput(true);
		 connection.setRequestMethod("GET");
		 
		 if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
		 	{
			 location = connection.getHeaderField("location");
			 System.out.println("CloudContent downloaded successfully");
			 return location.replaceAll("%20", "\n");
		 	}
		 
		 else 
		 	{
			 System.out.println("Could not download CloudContent successfully");
			 return null;
			}
		}
	 
	 catch (MalformedURLException ex) 
		{
	 	 System.out.println(ex.getMessage());
	 	 return null;
		}
	 
	 catch (IOException ex) 
		{
	 	 System.out.println(ex.getMessage());
	 	 return null;
		}
	}
}
