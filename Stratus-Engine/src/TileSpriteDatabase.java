/*
 *  Tile Sprite Database 
 * 
 *  Singleton design pattern that will act as our way of storing our Tile sprites to speed up spawning them in. Idea based on Starbound's Item, Planet, Enemy Databases
 * 
 */

import java.net.URL;

import javax.imageio.ImageIO;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class TileSpriteDatabase
{
	private static TileSpriteDatabase instance;
	
	private HashMap<String, Sprite> tileMap = new HashMap<String, Sprite>();
	
	//private constructor because it's not suppose to have more than one instance.
	private TileSpriteDatabase()
	{
		long startTime = System.currentTimeMillis();		
		loadImageSprite("1", "images/beach_bl_grass.png");
		loadImageSprite("2", "images/beach_bl.png");
		loadImageSprite("3", "images/beach_bm_01_grass.png");
		loadImageSprite("4", "images/beach_bm_01.png");
		loadImageSprite("5", "images/beach_bm_02_grass.png");
		loadImageSprite("6", "images/beach_bm_02.png");
		loadImageSprite("7", "images/beach_bm_03_grass.png");
		loadImageSprite("8", "images/beach_bm_03.png");
		loadImageSprite("9","images/beach_bm_04_grass.png");
		loadImageSprite("10","images/beach_bm_04.png");
		loadImageSprite("11","images/beach_bm_05_grass.png");
		loadImageSprite("12","images/beach_bm_05.png");
		loadImageSprite("13","images/beach_br_grass.png");
		loadImageSprite("14","images/beach_br.png");
		loadImageSprite("15","images/beach_l_up_diagonal_grass.png");
		loadImageSprite("16","images/beach_l_up_diagonal_neighbour_grass.png");
		loadImageSprite("17","images/beach_l_up_diagonal_neighbour.png");
		loadImageSprite("18","images/beach_l_up_diagonal.png");
		loadImageSprite("19","images/beach_lm_01_grass.png");
		loadImageSprite("20","images/beach_lm_01.png");
		loadImageSprite("21","images/beach_lm_02_grass.png");
		loadImageSprite("22","images/beach_lm_02.png");
		loadImageSprite("23","images/beach_lm_03_grass.png");
		loadImageSprite("24","images/beach_lm_03.png");
		loadImageSprite("25","images/beach_lm_04_grass.png");
		loadImageSprite("26","images/beach_lm_04.png");
		loadImageSprite("27","images/beach_r_diagonal_neighbour_grass.png");
		loadImageSprite("28","images/beach_r_diagonal_neighbour.png");
		loadImageSprite("29","images/beach_r_down_diagonal_grass.png");
		loadImageSprite("30","images/beach_r_down_diagonal_neighbour_grass.png");
		loadImageSprite("31","images/beach_r_down_diagonal_neighbour.png");
		loadImageSprite("32","images/beach_r_down_diagonal.png");
		loadImageSprite("33","images/beach_r_up_diagonal_grass.png");
		loadImageSprite("34","images/beach_r_up_diagonal.png");
		loadImageSprite("35","images/beach_rm_01_grass-22.png");
		loadImageSprite("36","images/beach_rm_01_grass.png");
		loadImageSprite("37","images/beach_rm_01-22.png");
		loadImageSprite("38","images/beach_rm_01.png");
		loadImageSprite("39","images/beach_rm_02_grass.png");
		loadImageSprite("40","images/beach_rm_02.png");
		loadImageSprite("41","images/beach_rm_03_grass.png");
		loadImageSprite("42","images/beach_rm_03.png");
		loadImageSprite("43","images/beach_rm_04_grass.png");
		loadImageSprite("44","images/beach_rm_04.png");
		loadImageSprite("45","images/beach_rm_05_grass.png");
		loadImageSprite("46","images/beach_rm_05.png");
		loadImageSprite("47","images/beach_tl_grass.png");
		loadImageSprite("48","images/beach_tl.png");
		loadImageSprite("49","images/beach_tm_01_grass.png");
		loadImageSprite("50","images/beach_tm_01.png");
		loadImageSprite("51","images/beach_tm_02_grass.png");
		loadImageSprite("52","images/beach_tm_02.png");
		loadImageSprite("53","images/beach_tm_03_grass.png");
		loadImageSprite("54","images/beach_tm_03.png");
		loadImageSprite("55","images/beach_tm_04_grass.png");
		loadImageSprite("56","images/beach_tm_04.png");
		loadImageSprite("57","images/beach_tr_grass.png");
		loadImageSprite("58","images/beach_tr.png");
		loadImageSprite("59","images/beach2_bl.png");
		loadImageSprite("60","images/beach2_bm_01.png");
		loadImageSprite("61","images/beach2_bm_02.png");
		loadImageSprite("62","images/beach2_bm_03.png");
		loadImageSprite("63","images/beach2_br.png");
		loadImageSprite("64","images/beach2_lm_01.png");
		loadImageSprite("65","images/beach2_lm_02.png");
		loadImageSprite("66","images/beach2_lm_03.png");
		loadImageSprite("67","images/beach2_rm_01.png");
		loadImageSprite("68","images/beach2_rm_02.png");
		loadImageSprite("69","images/beach2_rm_03.png");
		loadImageSprite("70","images/beach2_tl.png");
		loadImageSprite("71","images/beach2_tm_01.png");
		loadImageSprite("72","images/beach2_tm_02.png");
		loadImageSprite("73","images/beach2_tm_03.png");
		loadImageSprite("74","images/beach2_tr.png");
		loadImageSprite("75","images/grass.png");
		loadImageSprite("76","images/sand.png");
		loadImageSprite("77","images/beach_l_down_diagonal_neighbour_grass.png");
		loadImageSprite("78","images/beach_l_down_diagonal_neighbour.png");
		
		System.out.println("Tile Database loaded in " + (long)((System.currentTimeMillis() - startTime)/1000) + " seconds...");
	}
	
	public static TileSpriteDatabase instance()
	{
		if(instance != null)
		{
			return instance;
		}
		
		else
		{
			instance = new TileSpriteDatabase();
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
			
			tileMap.put(key, newSprite);
		}
		
		catch (Exception e)
		{
			System.out.print("Something when wrong with TileSpriteDatabase in loadImageSprite()");
		}
		
	}
	
	public Sprite getImageSprite(String desiredKey) throws Exception
	{
		if(tileMap.get(desiredKey) != null)
		{
			return (Sprite)tileMap.get(desiredKey);
		}
		
		else
		{
			throw new Exception("Bad Key Provided!Key: " + desiredKey);
		}
		
	}
	
	
}
