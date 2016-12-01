import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TileWorld 
{
	private GameCanvas origin;
	
	//Aggregate Classes
	private Camera worldCam;
	private Sidebar sidebar;
	
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
	
	public TileWorld(GameCanvas source, String mapFile)
	{
		tileSet = new ArrayList<ArrayList<Tile>>();
		origin = source;
		mapPath = mapFile;
		cameraHeight = origin.getHeight();
		
		loadMapFromFile();
		determineMapSize();
		setUpSideBarDimensions();
		
		//worldCam = new Camera(width,height);	
		worldCam = new Camera(cameraWidth,600, worldWidth, worldHeight);
		sidebar = new Sidebar(cameraWidth, 0, sidebarWidth, cameraHeight);
		
	}
	
	//Loads map from a txt file and builds as it goes
	public void loadMapFromFile()
	{
		try
		{
			BufferedReader fromFile = new BufferedReader(new FileReader(mapPath));
			
			String curLine;
			
			int activeRow = -1;
			
			int col = 0;
			
			ArrayList<Tile> tempArray = new ArrayList<Tile>();
			
			while((curLine = fromFile.readLine()) != null)
			{				
				String[] parsedLine = curLine.split(",");
				
				//behavior needed when the first value is the CSV-like file is different
				if(activeRow != Integer.parseInt(parsedLine[0]))
				{
					if(activeRow != -1)
					{
						tileSet.add(tempArray);
						
						col = 0;
					}
						
					//overwrites the existing array
					tempArray = new ArrayList<Tile>();
					
					activeRow = Integer.parseInt(parsedLine[0]);
				}
				
				boolean north = false;
				boolean east = false;
				boolean south = false;
				boolean west = false;
				
				if(parsedLine[4] == "1")
				{
					north = true;
				}
				
				if(parsedLine[5] == "1")
				{
					east = true;
				}
				
				if(parsedLine[6] == "1")
				{
					south = true;
				}
				
				if(parsedLine[7] == "1")
				{
					west = true;
				}
						
				Tile temp = new Tile(parsedLine[1],parsedLine[2],parsedLine[3], north, east, south, west, (col * 40), (activeRow * 40));
				
				tempArray.add(temp);
				
				col++;
				
			}
			
			//add last row
			
			tileSet.add(tempArray);
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
					worldCam.leftShift();
					worldCam.upShift();
				}
				
				else if(mouseLoc.getX() < cameraWidth && mouseLoc.getX() > cameraWidth - cam_XOffset)
				{
					worldCam.rightShift();
					worldCam.upShift();
				}
				
				else
				{
					worldCam.upShift();
				}
			}
			
			else if(mouseLoc.getY() < cameraHeight && mouseLoc.getY() > cameraHeight - cam_YOffset)
			{
				if(mouseLoc.getX() >= 0 && mouseLoc.getX() < 0 + cam_XOffset)
				{
					worldCam.leftShift();
					worldCam.downShift();
				}
				
				else if(mouseLoc.getX() < cameraWidth && mouseLoc.getX() > cameraWidth - cam_XOffset)
				{
					worldCam.rightShift();
					worldCam.downShift();
				}
				
				else
				{
					worldCam.downShift();
				}
			}
			
			else if(mouseLoc.getX() >= 0 && mouseLoc.getX() < 0 + cam_XOffset)
			{
				worldCam.leftShift();
			}
			
			else if(mouseLoc.getX() < cameraWidth && mouseLoc.getX() > cameraWidth - cam_XOffset)
			{
				worldCam.rightShift();
			}
			else
			{
				//System.out.println("Mouse not in Hover Zones");
			}
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
	
	public void update(Graphics2D g, Point tracker)
	{
		checkShift(tracker);
		
		checkTilesDraw(g);
		
		sidebar.update(g);
	}
}
