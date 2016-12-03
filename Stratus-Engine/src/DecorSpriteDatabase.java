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
import java.util.HashMap;


public class DecorSpriteDatabase
{
	private static DecorSpriteDatabase instance;
	
	private HashMap<String, Sprite> decorMap = new HashMap<String, Sprite>();
	
	//private constructor because it's not suppose to have more than one instance.
	private DecorSpriteDatabase()
	{
		long startTime = System.currentTimeMillis();		
		loadImageSprite("1","images/decor/dirt_001.png");
		loadImageSprite("2","images/decor/dirt_002.png");
		loadImageSprite("3","images/decor/dirt_grass_hor_01.png");
		loadImageSprite("4","images/decor/dirt_grass_hor_02.png");
		loadImageSprite("5","images/decor/dirt_grass_vert.png");
		loadImageSprite("6","images/decor/grass_01.png");
		loadImageSprite("7","images/decor/grass_02.png");
		loadImageSprite("8","images/decor/road_1.png");
		loadImageSprite("9","images/decor/road_2.png");
		loadImageSprite("10","images/decor/road_3.png");
		loadImageSprite("11","images/decor/road_4.png");
		loadImageSprite("12","images/decor/road_5.png");
		loadImageSprite("13","images/decor/road_asphalt_clean_hor.png");
		loadImageSprite("14","images/decor/road_asphalt_clean_to_damaged_hor.png");
		loadImageSprite("15","images/decor/road_asphalt_clean_to_damaged_vert.png");
		loadImageSprite("16","images/decor/road_asphalt_clean_vert.png");
		loadImageSprite("17","images/decor/road_asphalt_damaged_hor.png");
		loadImageSprite("18","images/decor/road_asphalt_damaged_to_clean_hor.png");
		loadImageSprite("19","images/decor/road_asphalt_damaged_to_clean_vert.png");
		loadImageSprite("20","images/decor/road_asphalt_damaged_vert.png");
		loadImageSprite("21","images/decor/road_corner_1.png");
		loadImageSprite("22","images/decor/road_corner_2.png");
		loadImageSprite("23","images/decor/road_corner_3.png");
		loadImageSprite("24","images/decor/road_corner_4.png");
		loadImageSprite("25", "images/decor/barrels_1.png");
		loadImageSprite("26", "images/decor/barrels_2.png");
		loadImageSprite("27", "images/decor/bush_1.png");
		loadImageSprite("28", "images/decor/bush_big.png");
		loadImageSprite("29", "images/decor/crates_1.png");
		loadImageSprite("30", "images/decor/crates_2.png");
		loadImageSprite("31", "images/decor/crates_3.png");
		loadImageSprite("32", "images/decor/crates_4.png");
		loadImageSprite("33","images/decor/rock_010.png");
		loadImageSprite("34","images/decor/rock_1.png");
		loadImageSprite("35","images/decor/rock_2.png");
		loadImageSprite("36","images/decor/rock_3.png");
		loadImageSprite("37","images/decor/rock_4.png");
		loadImageSprite("38","images/decor/rock_5.png");
		loadImageSprite("39","images/decor/rock_6.png");
		loadImageSprite("40","images/decor/rock_7.png");
		loadImageSprite("41","images/decor/rock_8.png");
		loadImageSprite("42","images/decor/rock_9.png");
		
		System.out.println("Decor Database loaded in " + (long)((System.currentTimeMillis() - startTime)/1000) + " seconds...");
	}
	
	public static DecorSpriteDatabase instance()
	{
		if(instance != null)
		{
			return instance;
		}
		
		else
		{
			instance = new DecorSpriteDatabase();
			return instance;
		}
	}
	
	public  void loadImageSprite(String key, String path)
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
			Sprite newSprite = new Sprite(40, 40, imageOut);
			
			decorMap.put(key, newSprite);
		}
		
		catch (Exception e)
		{
			System.out.print("Something when wrong with DecorSpriteDatabase in loadImageSprite()");
		}
		
	}
	
	public Sprite getImageSprite(String desiredKey) throws Exception
	{
		if(decorMap.get(desiredKey) != null)
		{
			return (Sprite)decorMap.get(desiredKey);
		}
		
		else
		{
			throw new Exception("Bad Key Provided!Key: " + desiredKey);
		}
		
	}
	
	
}
