import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Sidebar 
{
	//Image Sprite for SideBar
	private Sprite imageBase;
	
	//player instance
	private Team player;
	
	//Collision Rectangles for GUI
	private Rectangle buildingUp = new Rectangle(950, 96, 145, 64);
	private Rectangle buildingDown = new Rectangle(950, 607, 145, 64);
	private Rectangle unitUp = new Rectangle(1107, 96, 145, 64);
	private Rectangle unitDown = new Rectangle(1107, 607, 145, 64);
	
	//building collision Rectangles
	private Rectangle buildingA = new Rectangle(960, 180, 110, 110);
	private Rectangle buildingB = new Rectangle(960, 326, 110, 110);
	private Rectangle buildingC = new Rectangle(960, 461, 110, 110);
	
	//unit collision Rectangles
	private Rectangle unitA = new Rectangle(1120, 180, 110, 110);
	private Rectangle unitB = new Rectangle(1120, 326, 110, 110);
	private Rectangle unitC = new Rectangle(1120, 461, 110, 110);
	
	//ArrayLists for building/training
	ArrayList<BuildNode> buildings = new ArrayList<BuildNode>();
	ArrayList<BuildNode> units = new ArrayList<BuildNode>();
	
	//enumerators
	private int lowerBuilding = 0;
	private int upperBuilding;
	
	//PlaceHolder Classes
	BuildNode bnA;
	BuildNode bnB;
	BuildNode bnC;
	
	//Integers for location
	private int imageX;
	private int imageY;
	
	//flag for building
	private boolean constructing = false;
	
	public Sidebar(int xPos, int yPos, int width, int height, Team newPlayer)
	{
		SpriteDatabase.instance().loadImageSprite("sideBar.png", width, height);
		try
		{
			imageBase = SpriteDatabase.instance().getImageSprite("sideBar.png");
		}
		
		catch (Exception e)
		{
			
		}
		imageX = xPos;
		imageY = yPos;		
		player = newPlayer;
		
		loadBuildings();
		
		upperBuilding = buildings.size()-1;
		
		resetNodes();
	}
	
	public void loadBuildings()
	{
		BuildNode powerplant = new BuildNode(player,"powerplantIcon.png", "powerplantComplete.png", 5, 1, 500, "Power Plant");
		BuildNode barracks = new BuildNode(player, "barracks.png", "barracksComplete.png", 6, 2, 600, "Barracks");
		BuildNode warFactory = new BuildNode(player, "warFactory.png", "warFactoryComplete.png", 10, 3, 1000, "War Factory");
		
		buildings.add(powerplant);
		buildings.add(barracks);
		buildings.add(warFactory);
	}
	
	public void resetNodes()
	{
		bnA = buildings.get(lowerBuilding);
		bnB = buildings.get(lowerBuilding+1);
		bnC = buildings.get(upperBuilding);
		
		System.out.println(bnA.getBuildingID());
	}
	
	public void update(Graphics2D g)
	{
		imageBase.draw(g, imageX, imageY);
		g.drawString("" + player.getMoney(),1080, 40);
		
		//update nodes
		bnA.update(g, 960, 180);
		bnB.update(g, 960, 330);
		bnC.update(g, 960, 470);
		
		for(BuildNode node : buildings)
		{
			node.checkBuild();
		}
	}
	
	public int getSidebarX()
	{
		return imageX;
	}
	
	public int getSidebarY()
	{
		return imageY;
	}
	
	public void incrementBuildings()
	{
		if(upperBuilding + 1 < buildings.size())
		{
			upperBuilding++;
			lowerBuilding++;
			resetNodes();
		}
	}
	
	public void decrementBuildings()
	{
		if(lowerBuilding - 1 >= 0)
		{
			upperBuilding--;
			lowerBuilding--;
			resetNodes();
		}
	}
	
	public void resetNode()
	{
		for(BuildNode node : buildings)
		{
			if(node.getState() == "Ready")
			{
				node.reset();
				constructing = false;
			}
		}
	}
	
	public void checkClick(MouseEvent mouse)
	{
		//check collisions
		Point mouseLocation = new Point(mouse.getX(), mouse.getY());
		
		if(buildingUp.contains(mouseLocation))
		{
			incrementBuildings();
		}
		
		else if(buildingDown.contains(mouseLocation))
		{
			decrementBuildings();
		}
		
		else if(unitUp.contains(mouseLocation))
		{
			System.out.println("Increment Unit Tab");
		}
		
		else if(unitDown.contains(mouseLocation))
		{
			System.out.println("Decrement Unit Tab");
		}
		
		else if(buildingA.contains(mouseLocation))
		{
			
			
			if(bnA.getState() == "Ready")
			{
				System.out.println("We're building now!");
				player.buildBuilding(bnA.getBuildingID());
			}
			
			else if(!constructing)
			{
				bnA.build();
				constructing = true;
			}	

			
		}
		
		else if(buildingB.contains(mouseLocation))
		{
			if(bnB.getState() == "Ready")
			{
				player.buildBuilding(bnB.getBuildingID());
			}
			
			else if(!constructing)
			{
				bnB.build();
				constructing = true;
			}	
			
		}
		
		else if(buildingC.contains(mouseLocation))
		{
			if(bnC.getState() == "Ready")
			{
				player.buildBuilding(bnC.getBuildingID());
			}
			
			else if(!constructing)
			{
				bnC.build();
				constructing = true;
			}	
		}
		
		else if(unitA.contains(mouseLocation))
		{
			
		}
		
		else if(unitB.contains(mouseLocation))
		{
			
		}
		
		else if(unitC.contains(mouseLocation))
		{
		
		}
	}
}
