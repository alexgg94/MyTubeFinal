package image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class ImageProcessor 
{
 /**
  * Returns Image in bytes
  * @param imageURL
  * @param imageType
  * @return byte[]
  * @throws IOException
  */
 public static byte[] ImageToByte(String imageURL) throws IOException
 	{
	 byte[] imageInByte;
	 imageURL = imageURL + ".png";
	 BufferedImage originalImage = ImageIO.read(new File(imageURL));
	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 ImageIO.write(originalImage, "png", baos);
	 baos.flush();
	 imageInByte = baos.toByteArray();
	 baos.close(); 
	 return imageInByte;
 	}
 
 public static void ByteToImage(byte[] imageInByte, String imageName) throws IOException
 	{
	 InputStream in = new ByteArrayInputStream(imageInByte);
	 BufferedImage bImageFromConvert = ImageIO.read(in);
	 ImageIO.write(bImageFromConvert, "png", new File(imageName+".png"));
 	}
}













