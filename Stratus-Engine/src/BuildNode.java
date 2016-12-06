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
	private int buildingCost = 0;
	private String name = "";
	
	//timer variable
	long timerStart;
	long lastInterval;
	
	//Sprites
	Sprite active;
	Sprite normal;
	Sprite completed;
	
	Team player;
	
	public BuildNode(Team curPlayer, String normalImage, String completedImage, int time, int id, int cost, String newName)
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
		buildingCost = cost;
		buildTime = time;
		buildingNum = id;
			
		
		name = newName;
		
		player = curPlayer;
	}
	
	public void checkBuild()
	{
		if(state == "Building")
		{
			
			if((System.currentTimeMillis() - timerStart) >= (buildTime * 1000))
			{
				active = completed;
				state = "Ready";
				System.out.println("Ready to go!");
			}
			
			else if(System.currentTimeMillis() - lastInterval >= 1000)
			{
				lastInterval = System.currentTimeMillis();
				player.setMoney(player.getMoney()-100);
			}
				
		}		

	}
	
	public void reset()
	{
		state = "Idle";
		active = normal;
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
		
		System.out.println("Building " + name  + "...");
		
		timerStart = System.currentTimeMillis();
		
	}
}
