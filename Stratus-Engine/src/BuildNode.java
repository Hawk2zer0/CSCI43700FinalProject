/*
 * BuildNode Class
 * 
 * Used to Signify a item to build
 */

import java.awt.Graphics2D;

public class BuildNode 
{
	//members
	private String state;
	private long startBuildTime = 0;
	private int buildTime;
	private int buildingNum;
	private int cost = 0;
	private String name = "";
	
	//Sprites
	Sprite active;
	Sprite normal;
	Sprite completed;
	
	public BuildNode(String normalImage, String completedImage, int buildTime, int buildingNum, int cost, String newName)
	{
		state = "Idle";
		try
		{
			normal = SpriteDatabase.instance().getImageSprite(normalImage);
			normal.setHeight(100);
			normal.setWidth(100);
			completed = SpriteDatabase.instance().getImageSprite(completedImage);
			completed.setHeight(100);
			completed.setWidth(100);
		}
		catch (Exception e)
		{
			
		}
		
		active = normal;
		
		name = newName;
	}
	
	
	public void update(Graphics2D graphics, int x, int y)
	{
		active.draw(graphics, x, y);
	}
	
	public int getBuildingID()
	{
		return buildingNum;
	}
	
	public String getState()
	{
		return state;
	}
	
	public void build()
	{
		state = "Building";
		
		System.out.print("Building " + name  + "...");
		System.out.print(active.getWidth());
		System.out.print(active.getHeight());
		
	}
}
