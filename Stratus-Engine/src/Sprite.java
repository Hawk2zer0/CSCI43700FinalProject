//Sprite Java Class - Main Sprite for engine
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.Image;

public class Sprite
{
	//members
	//Members that must be defined at all times.
	private int height;
	private int width;
	private Image imageFile;	
	
	public Sprite(int newWidth, int newHeight, BufferedImage image)
	{
		width = newWidth;
		height = newHeight;
		imageFile = image;
	}	
	
	public void setWidth(int newWidth)
	{
		width = newWidth;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setHeight(int newHeight)
	{
		height = newHeight;
	}	
	
	public int getHeight()
	{
		return height;
	}
	
	public Image getImage()
	{
		return imageFile;
	}

	public void draw(Graphics2D graphicControl, int x, int y)
	{
		graphicControl.drawImage(imageFile, x, y, width, height, null);
	}
	
	public void draw(Graphics2D graphicControl, AffineTransform original, AffineTransform rotated, int x, int y)
	{
		graphicControl.transform(rotated);
		graphicControl.drawImage(imageFile, x, y, width, height, null);
		graphicControl.setTransform(original);
	}
	
}
