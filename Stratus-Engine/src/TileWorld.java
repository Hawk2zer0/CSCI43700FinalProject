import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class TileWorld 
{
	private GameCanvas origin;
	
	//Aggregate Classes
	private Camera worldCam;
	private Sidebar sidebar;
	private Team player;
	
	//Map Related variables
	private ArrayList<ArrayList<Tile>> tileSet;
	private String mapPath;
	
	//Integers for WorldSize - Will reflect the size of the canvas
	private int worldWidth;
	private int worldHeight;
	
	//Integers for Mouse "hover zones" - If within these zones, adjust camera!
	private int cam_XOffset = 50;
	private int cam_YOffset = 50;
	
	//Camera width
	private int cameraWidth;
	private int cameraHeight;
	
	//Sidebar width
	private int sidebarWidth = 400;
	
	//boolean to allow mouse movement to take precedence over keys
	boolean mouseOnZone = false;
	
	public TileWorld(GameCanvas source, String mapFile)
	{
		tileSet = new ArrayList<ArrayList<Tile>>();
		origin = source;
		mapPath = mapFile;
		cameraHeight = origin.getHeight();
		
		loadMapFromFile();
		determineMapSize();
		setUpSideBarDimensions();
		
		player = new Team(this,1,1000);
		
		worldCam = new Camera(cameraWidth,cameraHeight, worldWidth, worldHeight);
		sidebar = new Sidebar(cameraWidth, 0, sidebarWidth, cameraHeight, player);
		
		
		
	}
	
	//Loads map from a txt file and builds as it goes only overriding default tiles if that coordinate is mentioned in the file
	public void loadMapFromFile()
	{
		try
		{
			File file = new File(mapPath);
			
			BufferedReader fromFile = new BufferedReader(new FileReader(file));
			
			String curLine;
			
			//bookkeeping Integers
			int maxRows;
			int maxCols;
			int currentRow = 0;
			int currentCol = 0;
			
			//holds are row of tiles
			ArrayList<Tile> tempArray = new ArrayList<Tile>();
			
			curLine = fromFile.readLine();
			
			String[] sizes = curLine.split(",");
			
			maxRows = Integer.parseInt(sizes[1]);
			maxCols = Integer.parseInt(sizes[0]);
			
			while((curLine = fromFile.readLine()) != null)
			{
				String[] parsedLine = curLine.split(",");
				
				//check what row we are on
				if(currentRow != Integer.parseInt(parsedLine[0]))
				{
					//need to catch up with the map
					while(currentRow != Integer.parseInt(parsedLine[0]))
					{
						for(int col = currentCol; col < maxCols; col++)
						{
							Tile temp = new Tile("75","-1","-1", (col * 40), (currentRow * 40));
							
							tempArray.add(temp);
						}
						
						currentRow++;
						currentCol = 0;
						tileSet.add(tempArray);
						tempArray = new ArrayList<Tile>();
					}
				}
				
				if(currentCol != Integer.parseInt(parsedLine[1]))
				{					
					//we need to only catch up on our columns
					for(int col = currentCol; col < Integer.parseInt(parsedLine[1]); col++)
					{
						Tile temp = new Tile("75","-1","-1", (col * 40), (currentRow * 40));
						
						tempArray.add(temp);
						
						currentCol++;
					}
				}
				
				//we are on the same track at this point, so override this tile.
				Tile temp = new Tile(parsedLine[2],parsedLine[3],parsedLine[4], (currentCol * 40), (currentRow * 40));
				
				tempArray.add(temp);
				
				currentCol++;
			}
			
			//make sure the remaining columns are filled!			
			if(currentCol < maxCols)
			{
				for(int col = currentCol; col < maxCols; col++)
				{
					Tile temp = new Tile("75","-1","-1", (col * 40), (currentRow * 40));
					
					tempArray.add(temp);
				}
			}
			
			//add current row			
			tileSet.add(tempArray);
			currentRow++;
			
			//check if all rows have been added
			if(currentRow < maxRows)
			{
				tempArray = new ArrayList<Tile>();
				
				//need to catch up with the map
				while(currentRow < maxRows)
				{					
					for(int col = 0; col < maxCols; col++)
					{
						Tile temp = new Tile("75","-1","-1", (col* 40), (currentRow * 40));
						
						tempArray.add(temp);
					}
					
					currentRow++;
					tileSet.add(tempArray);
				}
			}
			
			fromFile.close();
		}
		
		catch(IOException ioE)
		{
			System.out.println("Error with loading File Resource!: " + ioE);
		}
	}
	
	//checks mouse position and adjusts camera at will
	public void checkShift(Point mouseLoc)
	{
		if(mouseLoc != null)
		{
		
			if(mouseLoc.getY() >= 0 && mouseLoc.getY() < 0 + cam_YOffset)
			{
				if(mouseLoc.getX() >= 0 && mouseLoc.getX() < 0 + cam_XOffset)
				{
					mouseOnZone = true;
					worldCam.leftShift();
					worldCam.upShift();
				}
				
				else if(mouseLoc.getX() < cameraWidth && mouseLoc.getX() > cameraWidth - cam_XOffset)
				{
					mouseOnZone = true;
					worldCam.rightShift();
					worldCam.upShift();
				}
				
				else
				{
					mouseOnZone = true;
					worldCam.upShift();
				}
			}
			
			else if(mouseLoc.getY() < cameraHeight && mouseLoc.getY() > cameraHeight - cam_YOffset)
			{
				if(mouseLoc.getX() >= 0 && mouseLoc.getX() < 0 + cam_XOffset)
				{
					mouseOnZone = true;
					worldCam.leftShift();
					worldCam.downShift();
				}
				
				else if(mouseLoc.getX() < cameraWidth && mouseLoc.getX() > cameraWidth - cam_XOffset)
				{
					mouseOnZone = true;
					worldCam.rightShift();
					worldCam.downShift();
				}
				
				else
				{
					mouseOnZone = true;
					worldCam.downShift();
				}
			}
			
			else if(mouseLoc.getX() >= 0 && mouseLoc.getX() < 0 + cam_XOffset)
			{
				mouseOnZone = true;
				worldCam.leftShift();
			}
			
			else if(mouseLoc.getX() < cameraWidth && mouseLoc.getX() > cameraWidth - cam_XOffset)
			{
				mouseOnZone = true;
				worldCam.rightShift();
			}
			else
			{
				mouseOnZone = false;
			}
			
			//call draw to make sure we are good
		}
	}
	
	//simply looks at the tileSet and determines the size of the world
	public void determineMapSize()
	{
		worldHeight = (tileSet.size() * tileSet.get(0).get(0).getHeight());
		worldWidth = (tileSet.get(0).size() * tileSet.get(0).get(0).getWidth());
		
		System.out.println("World Dimensions: " + worldWidth + " x " + worldHeight);		
	}
	
	public void setUpSideBarDimensions()
	{
		cameraWidth = origin.getWidth() - sidebarWidth;
	}
	
	public int getWorldWidth()
	{
		return worldWidth;
	}
	
	public int getWorldHeight()
	{
		return worldHeight;
	}
	
	public void checkTilesDraw(Graphics2D g)
	{
		for(ArrayList<Tile> row : tileSet)
		{
			for(Tile col : row)
			{
				col.checkDraw(g, worldCam.getCamera());
			}
		}
	}
	
	public void compareClick(MouseEvent mouse, boolean shiftPressed)
	{
		int absX = mouse.getX() + worldCam.getLeft();
		int absY = mouse.getY() + worldCam.getTop();
		
		//Debug Locations Printout
		//System.out.println("Relative Mouse Position: " + mouse.getX() + " " +  mouse.getY());
		//System.out.println("Absolute Mouse Position: " + absX + " " +  absY);	
		
		if(mouse.getX() < sidebar.getSidebarX())
		{
			if(shiftPressed)
			{
				player.checkMouseHover(absX, absY);
			}
			
			else if(player.canPlaceBuilding())
			{
				System.out.println("Test Location");
				
				checkValidSpot(absX, absY);
			}
			
			else
			{
				if(player.hasActive())
				{
					player.moveEntityTo(absX, absY);
				}
				
				else
				{
					player.checkMouseHover(absX, absY);
				}
			}		

		}
		
		else
		{
			sidebar.checkClick(mouse);
		}
	}
	
	public void checkValidSpot(int x, int y)
	{
		boolean validTile = true;
		boolean validBuilding = false;
		
		Rectangle buildingQuestion = new Rectangle(x, y, 80, 80);
		
		for(ArrayList<Tile> tileList : tileSet)
		{
			for(Tile node : tileList)
			{
				if(node.getArea().intersects(buildingQuestion))
				{
					if(node.isBlocked())
					{
						//not valid 
						System.out.println("Terrain not suitable...");
						validTile = false;
					}
				}
			}
		}
		
		//check all buildings
		//testing buildings
		for(Building b : player.getBuildings())
		{
			//within buildRadius?
			if(b.getBuildRadius().intersects(buildingQuestion))
			{
				validBuilding = true;
				
				if(b.getBoundingBox().intersects(buildingQuestion))
				{
					//not valid
					System.out.println("Too Close to another building");
					
					validBuilding = false;
				}
			}
		}
		
		if(!validBuilding)
		{
			System.out.println("Buildings issue");
		}
		
		if(validTile && validBuilding)
		{
			player.placeStructure(x,y);
			sidebar.resetNode();
		}
	}
	
	public void handleRightClick()
	{
		player.removeSelected();
	}
	
	public void adjustCamera(String direction, Graphics2D g)
	{
		if(!mouseOnZone)
		{		
			if(direction == "up")
			{
				worldCam.upShift();
			}
			
			else if(direction == "down")
			{
				worldCam.downShift();
			}
			
			else if(direction == "left")
			{
				worldCam.leftShift();
			}
			
			else if(direction == "right")
			{
				worldCam.rightShift();
			}
			
			else
			{
				System.out.println("Unexpected Output, camera idle.");
			}
			
			keyboardUpdate(g);
		}
	}
	
	//update that is called regularly for the standard game loop
	public void update(Graphics2D g, Point tracker)
	{
		checkShift(tracker);
		
		checkTilesDraw(g);
		
		player.update(g, worldCam.getCamera());
		
		sidebar.update(g);
	}
	
	//update called when keyboard input is detected...this is to prevent image tearing
	public void keyboardUpdate(Graphics2D g)
	{
		checkTilesDraw(g);
		
		player.update(g, worldCam.getCamera());
		
		sidebar.update(g);
	}
	
	public ArrayList<ArrayList<Tile>> getWorld()
	{
		return tileSet;
	}
}
