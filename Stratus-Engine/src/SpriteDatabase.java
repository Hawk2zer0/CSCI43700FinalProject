/*
 *  Sprite Database 
 * 
 *  Singleton design pattern that will act as our way of storing our sprites to speed up spawning them in. Idea based on Starbound's Item, Planet, Enemy Databases
 * 
 */

import java.net.URL;

import javax.imageio.ImageIO;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.util.HashMap;


public class SpriteDatabase 
{
	private static SpriteDatabase instance;
	
	private HashMap<String, Sprite> spriteMap = new HashMap<String, Sprite>();
	
	//private constructor because it's not suppose to have more than one instance.
	protected SpriteDatabase()
	{
		loadImageSprite("test.png", 40,40);
		loadImageSprite("truck3_body.png", 20,40);
		loadImageSprite("hq.png", 80,80);
		loadImageSprite("ruin1.png", 24,40);
		loadImageSprite("barracks.png", 100,100);
		loadImageSprite("barracksComplete.png", 100,100);
		loadImageSprite("powerplant.png", 100, 100);
		loadImageSprite("powerplantIcon.png", 100,100);
		loadImageSprite("powerplantComplete.png", 100,100);
		loadImageSprite("warFactory.png", 100, 100);
		loadImageSprite("warFactoryComplete.png", 100, 100);
	}
	
	public static SpriteDatabase instance()
	{
		if(instance != null)
		{
			return instance;
		}
		
		else
		{
			instance = new SpriteDatabase();
			return instance;
		}
	}
	
	public  void loadImageSprite(String path, int width, int height)
	{
		//"Check-in" all the images
		URL imagePath = this.getClass().getClassLoader().getResource(path);
		
		//load them in
		try
		{
			BufferedImage imageSource = ImageIO.read(imagePath);
			
			//prep for GPU to use. 
			GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			
			//configure Images
			BufferedImage imageOut  = config.createCompatibleImage(imageSource.getWidth(), imageSource.getHeight(), Transparency.BITMASK);
			
			imageOut.getGraphics().drawImage(imageSource, 0, 0, null);
			
			//load the image by creating the sprite
			Sprite newSprite = new Sprite(width, height, imageOut);
			
			spriteMap.put(path, newSprite);
		}
		
		catch (Exception e)
		{
			System.out.println("Something when wrong with SpriteDatabase in loadImageSprite()");
		}
		
	}
	
	public Sprite getImageSprite(String filePath) throws Exception
	{
		if(spriteMap.get(filePath) != null)
		{
			return (Sprite)spriteMap.get(filePath);
		}
		
		else
		{
			throw new Exception("Bad Key Provided!Key: " + filePath);
		}
		
	}
	
	
}
