/*
 * Team Class
 * 
 * Class that handles the team behavior, handles updates and all
 * 
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Team
{
	//members
	private TileWorld origin;
	private int id;
	private int powerGenerated = 0;
	private int powerDrained = 0;
	private int money = 0;
	private boolean hasConYard = false;
	private boolean hasBarracks = false;
	private boolean hasFactory = false;
	private boolean placeBuilding = false;
	
	//arrays containing entities. 
	private ArrayList<Unit> units = new ArrayList<Unit>();
	private ArrayList<Building> buildings = new ArrayList<Building>();
	
	//array for units selected	
	private ArrayList<Unit> activeUnits = new ArrayList<Unit>();
	private Building activeBuilding;
	
	public Team(TileWorld source, int teamId, int moneyAmount)
	{
		origin = source;
		id = teamId;
		money = moneyAmount;
		
		System.out.println("Player " +id+ " Ready!");
		
		addUnits();
		buildConYard(40, 40);
		buildRuin();
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
	
	public void setMoney(int newAmount)
	{
		money = newAmount;
	}
	
	public boolean canPlaceBuilding()
	{
		return placeBuilding;
	}
	
	public Building getActiveBuilding()
	{
		return activeBuilding;
	}
	
	public ArrayList<Building> getBuildings()
	{
		return buildings;
	}
	
	public void buildConYard(int x, int y)
	{
		Building conYard = new Building(origin, "hq.png", 80, 80, 40);
		
		conYard.changePosition(x, y);
		
		conYard.setOwner(id);
		
		conYard.setHealth(500);
		
		conYard.updateTiles();
		
		buildings.add(conYard);
		
		setHasConYard(true);	
	}
	
	public void buildRuin()
	{
		Building ruin = new Building(origin, "ruin1.png", 24,40,0);
		
		ruin.changePosition(640, 520);
		
		ruin.setHealth(20);
		
		ruin.updateTiles();
		
		buildings.add(ruin);
	}
	
	public void buildBuilding(int id)
	{
		if(id == 1)
		{
			activeBuilding  = new Building(origin, "powerplant.png", 80, 80,20);
		}
		
		else if(id == 2)
		{
			activeBuilding  = new Building(origin, "barracks.png", 80, 80,30);
		}
		
		else if(id == 3)
		{
			activeBuilding  = new Building(origin, "warFactory.png", 80, 80, 40);
		}
		
		if(activeBuilding != null)
		{
			System.out.println("Something is wrong");
		}
		
		else
		{
			System.out.println("No Assignement");
		}
		
		placeBuilding = true;
	}
	
	public void placeStructure(int x, int y)
	{
		activeBuilding.changePosition(x, y);
		activeBuilding.setOwner(1);
		activeBuilding.setHealth(200);
		activeBuilding.updateTiles();
		buildings.add(activeBuilding);
		activeBuilding = null;
		placeBuilding = false;
	}
	
	//debug method for now
	public void addUnits()
	{		
		Unit test = new Unit(origin,"truck3_body.png", 40, 40, 1);
		
		test.setSpeed(1);
		
		test.changePosition(40, 880);
		
		units.add(test);
		
		Unit test2 = new Unit(origin,"truck3_body.png", 40, 40, 1);
		
		test2.setSpeed(1);
		
		test2.changePosition(80, 880);
		
		units.add(test2);
	}
	
	public void update(Graphics2D graphics, Rectangle camera)
	{
		for(Entity unit : units)
		{
			unit.update(graphics, camera);
		}
		
		for(Entity building : buildings)
		{
			building.update(graphics, camera);
		}
	
	}
	
	public void checkMouseHover(int mouseX, int mouseY)
	{
		
		for(Unit unit : units)
		{
			if(unit.isMouseOver(mouseX, mouseY))
			{
				System.out.println("Health: " + unit.getHealth());
				if(activeUnits.contains(unit))
				{
					activeUnits.remove(unit);
				}
				
				else
				{
					addToSelected(unit);
				}
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
	
	public void moveEntityTo(int mouseX, int mouseY)
	{
		for(Unit unit : activeUnits)
		{
			int offsetX = mouseX;
			int offsetY = mouseY;
			
			unit.moveTo(offsetX, offsetY);
		}
	}
	
	//does the team have active units?
	public boolean hasActive()
	{
		return (activeUnits.size() > 0);
	}
	
	//adds to selected group
	public void addToSelected(Unit selectedUnit)
	{
		activeUnits.add(selectedUnit);
		System.out.println("Unit added to Selection Array");
	}
	
	//removes all entities in selection
	public void removeSelected()
	{
		if(hasActive())
		{
			System.out.println(activeUnits.size() + " Items removed from Active Array");
			activeUnits.clear();
		}
	}
	
}
