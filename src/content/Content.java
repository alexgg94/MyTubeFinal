package content;

import java.io.Serializable;

public class Content implements Serializable
{ 	
 private String name;
 private Long id;
 private int year;
 private double duration;
 private byte[] imageInBytes;
 private String ubication = "";
    
 public Content(String name, int year, double duration)
    {
     this.name=name;
     this.year=year;
     this.duration=duration;
    }
    
 public Content()
 	{}
 
 /**
  * Returns class' variables merged in a String
  * @return String
  */
 
 @Override
 public String toString()
     {
      return "\n-----------------------------\nName:\t" + this.name + "\nReleased year:\t" + Integer.toString(this.year) + 
    		  "\nDuration:\t" + Double.toString(this.duration) + "\nID:\t" + Long.toString(this.id) + "\n-----------------------------\n";
     }
 
 /**
  * Used to save the content in a file
  * @return String
  */
 public String save()
 	{
	 return this.name + " " + Integer.toString(this.year)+ " " + Double.toString(this.duration);
 	}
 
 /**
  * Uploads a new image in bytes to memory
  * @param imageInByte
  */
 public void uploadImageInMemory(byte[] imageInByte)
 	{
	 this.imageInBytes = imageInByte;
 	}
 
 /**
  *Deletes image from memory 
  */
 public void deleteImageFromMemory()
 	{
	 this.imageInBytes = null;
 	}
 
 public String getName()
 	{
	 return this.name; 
 	}
 
 public int getYear()
 	{
	 return this.year;
 	}
 
 public double getDuration()
 	{
	 return this.duration;
 	}
 
 public byte[] getImageInByte()
 	{
	 return this.imageInBytes;
 	}
 
 public void setUbication(String ubication)
 	{
	 this.ubication = ubication;
 	}
 
 public String getUbication()
 	{
	 return this.ubication;
 	}
 
 public void setId(Long id)
 	{
	 this.id= id;
 	}
}








