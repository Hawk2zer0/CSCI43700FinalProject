/*
 * World Class 
 * 
 * Basically Controls the map, and knows players and all.
 * 
 */

import java.awt.Graphics2D;
import java.util.ArrayList;

public class World 
{
	//members
	private Background map;
	private Sidebar sidebar;
	private Game_Wrapper origin;
	
	//Teams
	private Team player;
	private ArrayList<Team> teams = new ArrayList<Team>();
	
	public World(Game_Wrapper source, String mapImage, String sideBarImage)
	{
    	origin = source;
    	player = new Team(origin, 1, 10000);    	
    	player.buildConYard(200, 200);
    	
    	map = new Background(source, mapImage, source.canvas.getWidth() - source.windowXRightOffset, source.canvas.getHeight());
    	map.changePosition(0, 0);
		sidebar = new Sidebar(source, sideBarImage, source.windowXRightOffset, source.canvas.getHeight(), player);  	
    	
	}
	
	public void update(Graphics2D graphics)
	{
		map.update(graphics);
		sidebar.update(graphics);
		
		player.update(graphics);
		
		for(Team t : teams)
		{
			t.update(graphics);
		}
	}
	
	public void checkMouseClick(int x, int y)
	{
		sidebar.checkMouseHover(x,y);
		
		player.checkMouseHover(x, y);
		
		for(Team t: teams)
		{
			t.checkMouseHover(x, y);
		}
		
	}
}
