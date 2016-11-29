import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TileWorld 
{
	private Game_Wrapper origin;
	private ArrayList<ArrayList<Tile>> tileSet;
	private String mapPath;
	
	
	public TileWorld(Game_Wrapper source, String mapFile)
	{
		tileSet = new ArrayList<ArrayList<Tile>>();
		origin = source;
		mapPath = mapFile;
		loadMapFromFile();
	}
	
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
						
				Tile temp = new Tile(parsedLine[1],parsedLine[2],parsedLine[3], north, east, south, west, (col * 64), (activeRow * 64));
				
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
	
	public void updateTiles(Graphics2D g)
	{
		for(ArrayList<Tile> row : tileSet)
		{
			for(Tile col : row)
			{
				col.update(g);
			}
		}
	}
}
