/*
 * Team Class
 * 
 * Class that handles the team behavior, handles updates and all
 * 
 */

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Team
{
	//members
	private Game_Wrapper origin;
	private int id;
	private int powerGenerated = 0;
	private int powerDrained = 0;
	private int money = 0;
	private boolean hasConYard = false;
	private boolean hasBarracks = false;
	private boolean hasFactory = false;
	private boolean placeBuilding = false;
	
	//arrays containing entities. 
	private ArrayList<Entity> units = new ArrayList<Entity>();
	private ArrayList<Building> buildings = new ArrayList<Building>();
	
	//array for units selected	
	private ArrayList<Entity> active = new ArrayList<Entity>();
	
	//Factory Test ----REMOVE LATER
	private Unit_Factory factory;
	
	public Team(Game_Wrapper source, int teamId, int moneyAmount)
	{
		origin = source;
		factory = new Unit_Factory(source);
		id = teamId;
		money = moneyAmount;
		
		System.out.println("Player " +id+ " Ready!");
		
		addUnits();
	}
	
	public boolean getHasConYard()
	{
		return hasConYard;
	}
	
	public void setHasConYard(boolean rule)
	{
		hasConYard = rule;
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public void buildConYard(int x, int y)
	{
		Building conYard = new Building(origin, "hq.png", 150, 150, 40);
		
		conYard.changePosition(x, y);
		
		conYard.setOwner(id);
		
		conYard.setHealth(500);
		
		buildings.add(conYard);
		
		setHasConYard(true);	
	}
	
	//debug method for now
	public void addUnits()
	{
    	Entity ship = factory.makeUnit("test2.png", 50, 50);
    	
    	Entity ship2 = factory.makeUnit("test.png", 50, 50);
    	
    	ship.changeSpeed(5);
    	ship.setAngles(45);
		
    	ship2.changeSpeed(5);    	
    	ship2.changePosition(300, 200);
    	ship2.setAngles(0);
    	
    	units.add(ship);
    	
    	units.add(ship2);
	}
	
	public void update(Graphics2D graphics)
	{
		for(Entity unit : units)
		{
			unit.update(graphics);
		}
		
		if(units.size() == 2)
		{
			if(units.get(0).checkCollision(units.get(1)))
			{
				System.out.println("Collision detected!");
				units.get(0).die();
				units.get(1).die();
				units.remove(1);
				units.remove(0);
			}
		}
		
		for(Building building : buildings)
		{
			building.update(graphics);
		}		
	}
	
	public void checkMouseHover(int mouseX, int mouseY)
	{
		for(Entity unit : units)
		{
			if(unit.isMouseOver(mouseX, mouseY))
			{
				System.out.println("Health: " + unit.getHealth());
			}
		}
		
		for(Building building :buildings)
		{
			if(building.isMouseOver(mouseX, mouseY))
			{
				System.out.println("Health: " + building.getHealth());
			}
		}
	}
	
	
	
	
	
}
