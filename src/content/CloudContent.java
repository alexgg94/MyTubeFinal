package content;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CloudContent implements Serializable
{ 
 @PrimaryKey
 @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
 private Long id;	
	
 @Persistent String description;
 @Persistent String location;

 public CloudContent(String description, String location)
    {
	 this.description = description;
	 this.location = location;
    }  
 
 /**
  * Returns class' id
  * @return Long
  */
 public Long getId()
 	{
	 return this.id; 
 	}
 
 /**
  * Sets value to id
  * @param id
  */
 public void setId(Long id)
 	{
	 this.id = id;
 	}
 
/**
 * Returns the description of the content
 * @return String
 */
 public String getDescription()
 	{
	 return this.description;
 	}
 
 /**
  * Returns the url of the remote object who has this content 
  * @return String
  */
 public String getLocation()
 	{
	 return this.location;
 	}
}