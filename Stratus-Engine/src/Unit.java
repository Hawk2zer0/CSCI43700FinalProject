/*
 * Unit Class 
 * 
 * Commandable Entity Class with the ability to attack
 * 
 * 
 */

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Unit extends Entity
{
	//members
	private int owner;
	private int state = 0;
	
	//state "macros"
	private int IDLE = 0;
	private int MOVING = 1;
	private int ATTACK = 2;
	private int DEAD = 3;
	
	//pathfinder variables
	//start and end tiles - so we can lock them 
	Tile startTile;
	Tile endTile;
	//ArrayLists to bookKeep the A*
	ArrayList<Tile> nodes = new ArrayList<Tile>();
	ArrayList<Integer> distanceTo = new ArrayList<Integer>();
	ArrayList<Integer> rowNum = new ArrayList<Integer>();
	ArrayList<Integer> colNum = new ArrayList<Integer>();
	ArrayList<Tile> sourceNode = new ArrayList<Tile>();
	//priority boolean
	boolean leftFirst = false;
	boolean upFirst = false;
	Queue<Tile> discoveredNodes = new LinkedList<Tile>();
	ArrayList<Tile> visited = new ArrayList<Tile>();
	
	//movement variables
	Stack<Point> path = new Stack<Point>();
	Point currentPoint;
	Point nextPoint; // provides a buffer for if directions are changed
	
	
	//methods
	public Unit(TileWorld source, String imageFile, int width, int height, int newOwner)
	{
		super(source,imageFile, width, height);
		state = IDLE;
	}	
	
	public void setOwner(int newOwner)
	{
		owner = newOwner;
	}
	
	@Override
	public void moveTo(int mouseX, int mouseY)
	{
		//clear all arrays
		nodes.clear();
		distanceTo.clear();
		rowNum.clear();
		colNum.clear();
		sourceNode.clear();
		visited.clear();
		discoveredNodes.clear();

		
		//variables to pass into A*
		int startRow = 0;
		int startCol = 0;
		
		//find our start and end node
		for(int row = 0; row < origin.getWorld().size(); ++row)
		{
			for(int col = 0; col < origin.getWorld().get(row).size(); ++col)
			{
				Tile temp = origin.getWorld().get(row).get(col);
				
				//start node found!
				if(state != MOVING)
				{
					if(temp.getArea().contains(new Point((int)this.getXPosition(), (int) this.getYPosition())))
					{
						visited.add(temp);
						nodes.add(temp);
						distanceTo.add(0);
						sourceNode.add(null);
						rowNum.add(row);
						colNum.add(col);
						startTile = temp;
						startRow = row;
						startCol = col;
					}
				}
				
				else
				{
					if(temp.getArea().contains(nextPoint))
					{
						System.out.println("Next Point used to re-navigate");
						visited.add(temp);
						nodes.add(temp);
						distanceTo.add(0);
						sourceNode.add(null);
						rowNum.add(row);
						colNum.add(col);
						startTile = temp;
						startRow = row;
						startCol = col;
						path.clear();
					}
				}

				
				//end node found!
				if(temp.getArea().contains(new Point(mouseX, mouseY)))
				{
					visited.add(temp);
					nodes.add(temp);
					distanceTo.add(9999);
					sourceNode.add(null);
					rowNum.add(row);
					colNum.add(col);
					endTile = temp;
				}
			}
		}
		
		if(startTile != endTile)
		{
			if(!endTile.isBlocked())
			{
				
				//check their locations and determine priority
				if(startTile.getX() - endTile.getX() >= 0)
				{
					leftFirst = true;
				}
				
				if(startTile.getY() - endTile.getY() >= 0)
				{
					upFirst = true;
				}
				
				A_Star(startTile, startRow, startCol);
				
				if(distanceTo.get(nodes.indexOf(endTile)) == 9999)
				{
					System.out.println("There's no way for me to reach there");
				}
				
				else
				{
					//build path
					buildPath(endTile);
					
					if(state != MOVING)
					{
						nextPoint = path.pop();
						moveOut();
					}				

				}
				
			}
			
			else
			{
				System.out.println("I can't go there...");
			}
		}		
	}
	
	public void A_Star(Tile currentNode, int row, int column)
	{
		ArrayList<ArrayList<Tile>> map = origin.getWorld();
		
		if(leftFirst)
		{
			//left node
			//check if in bounds and not already visited
			if(row-1 >= 0)
			{
				if(!nodes.contains(map.get(row-1).get(column)))
				{
					//new entry!
					Tile temp = map.get(row-1).get(column);
					//make sure the block is accessible
					if(!temp.isBlocked())
					{
						nodes.add(temp);
						distanceTo.add(distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.add(currentNode);
						rowNum.add(row-1);
						colNum.add(column);
						discoveredNodes.add(temp);
					}
				}
				
				//it already exists
				else if(nodes.contains(map.get(row-1).get(column)))
				{
					Tile temp = map.get(row-1).get(column);
					int oldDistance = distanceTo.get(nodes.indexOf(temp));
					if((distanceTo.get(nodes.indexOf(currentNode))+1) < oldDistance)
					{
						distanceTo.set(nodes.indexOf(temp),distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.set(nodes.indexOf(temp), currentNode);
					}				
					
				}
				
			}
			

			
			//right node
			if(row+1 < map.size())
			{
				if(!nodes.contains(map.get(row+1).get(column)))
				{
					//new entry!
					Tile temp = map.get(row+1).get(column);
					//make sure the block is accessible
					if(!temp.isBlocked())
					{
						nodes.add(temp);
						distanceTo.add(distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.add(currentNode);
						rowNum.add(row+1);
						colNum.add(column);
						discoveredNodes.add(temp);
					}
				}
				
				//it already exists
				else if(nodes.contains(map.get(row+1).get(column)))
				{
					Tile temp = map.get(row+1).get(column);
					int oldDistance = distanceTo.get(nodes.indexOf(temp));
					if((distanceTo.get(nodes.indexOf(currentNode))+1) < oldDistance)
					{
						distanceTo.set(nodes.indexOf(temp),distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.set(nodes.indexOf(temp), currentNode);
					}				
					
				}				
				
			}
			
			
		}
		
		else
		{
			//right node
			if(row+1 < map.size())
			{
				if(!nodes.contains(map.get(row+1).get(column)))
				{
					//new entry!
					Tile temp = map.get(row+1).get(column);
					//make sure the block is accessible
					if(!temp.isBlocked())
					{
						nodes.add(temp);
						distanceTo.add(distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.add(currentNode);
						rowNum.add(row+1);
						colNum.add(column);
						discoveredNodes.add(temp);
					}
				}
				
				//it already exists
				else if(nodes.contains(map.get(row+1).get(column)))
				{
					Tile temp = map.get(row+1).get(column);
					int oldDistance = distanceTo.get(nodes.indexOf(temp));
					if((distanceTo.get(nodes.indexOf(currentNode))+1) < oldDistance)
					{
						distanceTo.set(nodes.indexOf(temp),distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.set(nodes.indexOf(temp), currentNode);
					}				
					
				}				
				
			}
			
			//left node
			//check if in bounds and not already visited
			if(row-1 >= 0)
			{
				if(!nodes.contains(map.get(row-1).get(column)))
				{
					//new entry!
					Tile temp = map.get(row-1).get(column);
					//make sure the block is accessible
					if(!temp.isBlocked())
					{
						nodes.add(temp);
						distanceTo.add(distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.add(currentNode);
						rowNum.add(row-1);
						colNum.add(column);
						discoveredNodes.add(temp);
					}
				}
				
				//it already exists
				else if(nodes.contains(map.get(row-1).get(column)))
				{
					Tile temp = map.get(row-1).get(column);
					int oldDistance = distanceTo.get(nodes.indexOf(temp));
					if((distanceTo.get(nodes.indexOf(currentNode))+1) < oldDistance)
					{
						distanceTo.set(nodes.indexOf(temp),distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.set(nodes.indexOf(temp), currentNode);
					}				
					
				}
				
			}
		}
		
		if(upFirst)
		{
			//North Node
			//check if in bounds and not already visited
			if(column-1 >= 0)
			{
				if(!nodes.contains(map.get(row).get(column-1)))
				{
					//new entry!
					Tile temp = map.get(row).get(column-1);
					//make sure the block is accessible
					if(!temp.isBlocked())
					{
						nodes.add(temp);
						distanceTo.add(distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.add(currentNode);
						rowNum.add(row);
						colNum.add(column-1);
						discoveredNodes.add(temp);
					}
				}
				
				//it already exists
				else if(nodes.contains(map.get(row).get(column-1)))
				{
					Tile temp = map.get(row).get(column-1);
					int oldDistance = distanceTo.get(nodes.indexOf(temp));
					if((distanceTo.get(nodes.indexOf(currentNode))+1) < oldDistance)
					{
						distanceTo.set(nodes.indexOf(temp),distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.set(nodes.indexOf(temp), currentNode);
					}				
					
				}			
			}			
			
			//South Node
			if(column+1 < map.get(0).size())
			{
				if(!nodes.contains(map.get(row).get(column+1)))
				{
					//new entry!
					Tile temp = map.get(row).get(column+1);
					//make sure the block is accessible
					if(!temp.isBlocked())
					{
						nodes.add(temp);
						distanceTo.add(distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.add(currentNode);
						rowNum.add(row);
						colNum.add(column+1);
						discoveredNodes.add(temp);
					}
				}
				
				//it already exists
				else if(nodes.contains(map.get(row).get(column+1)))
				{
					Tile temp = map.get(row).get(column+1);
					int oldDistance = distanceTo.get(nodes.indexOf(temp));
					if((distanceTo.get(nodes.indexOf(currentNode))+1) < oldDistance)
					{
						distanceTo.set(nodes.indexOf(temp),distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.set(nodes.indexOf(temp), currentNode);
					}				
					
				}
			}
		}
		
		else
		{
			//South Node
			if(column+1 < map.get(0).size())
			{
				if(!nodes.contains(map.get(row).get(column+1)))
				{
					//new entry!
					Tile temp = map.get(row).get(column+1);
					//make sure the block is accessible
					if(!temp.isBlocked())
					{
						nodes.add(temp);
						distanceTo.add(distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.add(currentNode);
						rowNum.add(row);
						colNum.add(column+1);
						discoveredNodes.add(temp);
					}
				}
				
				//it already exists
				else if(nodes.contains(map.get(row).get(column+1)))
				{
					Tile temp = map.get(row).get(column+1);
					int oldDistance = distanceTo.get(nodes.indexOf(temp));
					if((distanceTo.get(nodes.indexOf(currentNode))+1) < oldDistance)
					{
						distanceTo.set(nodes.indexOf(temp),distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.set(nodes.indexOf(temp), currentNode);
					}				
					
				}
			}	
			
			//North Node
			//check if in bounds and not already visited
			if(column-1 >= 0)
			{
				if(!nodes.contains(map.get(row).get(column-1)))
				{
					//new entry!
					Tile temp = map.get(row).get(column-1);
					//make sure the block is accessible
					if(!temp.isBlocked())
					{
						nodes.add(temp);
						distanceTo.add(distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.add(currentNode);
						rowNum.add(row);
						colNum.add(column-1);
						discoveredNodes.add(temp);
					}
				}
				
				//it already exists
				else if(nodes.contains(map.get(row).get(column-1)))
				{
					Tile temp = map.get(row).get(column-1);
					int oldDistance = distanceTo.get(nodes.indexOf(temp));
					if((distanceTo.get(nodes.indexOf(currentNode))+1) < oldDistance)
					{
						distanceTo.set(nodes.indexOf(temp),distanceTo.get(nodes.indexOf(currentNode))+1);
						sourceNode.set(nodes.indexOf(temp), currentNode);
					}				
					
				}			
			}			
					
		} 
		
		visited.add(currentNode);
		if(discoveredNodes.size() > 0)
		{
			Tile nextNode = discoveredNodes.remove();
			A_Star(nextNode, rowNum.get(nodes.indexOf(nextNode)), colNum.get(nodes.indexOf(nextNode)));
		}
		
	}

	public void buildPath(Tile currentNode)
	{
		//build point for unit to move to.
		int x = currentNode.getX() + (currentNode.getWidth()/2);
		int y = currentNode.getY() + (currentNode.getHeight()/2);
		
		Point pathPoint = new Point(x, y);
		
		path.push(pathPoint);
		
		//find the next one
		Tile nextNode = sourceNode.get(nodes.indexOf(currentNode));
		
		//the starting node is where we already are, no need to add
		if(nextNode != startTile && nextNode != null)
		{
			buildPath(nextNode);
		}
		
		
	}
	
	public void moveOut()
	{
		if(nextPoint != null)
		{
			state = MOVING;
			
			currentPoint = nextPoint;
			
			if(!path.isEmpty())
			{
				nextPoint = path.pop();
			}
			
			else
			{
				nextPoint = null;
			}
			
			System.out.println("nextPoint changed");
			
			System.out.println("Current Point to Go: " + currentPoint.x + "," + currentPoint.y);
			System.out.println("Sprite Location: " + ((int)getXPosition() + (getSprite().getWidth()/2)) + "," + ((int)getYPosition() + (getSprite().getHeight()/2)));
			
			//calculate angle
			int diffX = currentPoint.x - ((int)getXPosition() + (getSprite().getWidth()/2));
			int diffY = currentPoint.y - ((int)getYPosition() + (getSprite().getHeight()/2));
			double angle = 0;
			
			System.out.println("ArcTan of : " + diffY + "/" + diffX );
			
			//special case handling
			if(diffX == 0)
			{
				if(diffY > 0)
				{
					angle = 180;
				}
				
				else if(diffY < 0)
				{
					angle = 0;
				}
			}
			
			else if(diffY == 0)
			{
				if(diffX > 0)
				{
					angle = 90;
				}
				
				else if(diffX < 0)
				{
					angle = 270;
				}
			}
			
			//no special case occurred
			else
			{
				angle = (Math.atan2((double)diffY , (double) diffX) * (180/Math.PI));
			}
			
			System.out.println("Angle After Adding: " + angle);
			
			this.setAngles(angle);		
			
		}
		
		else
		{
			//we have arrived!
			stopMove();
			state = IDLE;
		}
	}
	
	public boolean checkLocation()
	{		
		if(currentPoint != null)
		{
			Point location = new Point((int)(getXPosition() + getSprite().getWidth()/2), (int)(getYPosition() + getSprite().getHeight()/2));
			
			return (location.equals(currentPoint));
		}
		
		else
		{
			return false;
		}		
	}
	
	@Override
	public void update(Graphics2D g, Rectangle camera)
	{
		if(state != DEAD)
		{
			setXPosition(getXPosition() + getXVelocity());
			setYPosition(getYPosition() + getYVelocity());
			
			//check if we are at our next position
			if(checkLocation())
			{
				moveOut();
			}
			checkBounds();			
			checkDraw(g, camera);
		}
	}
	
}
