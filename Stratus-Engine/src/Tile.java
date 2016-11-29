import java.awt.Graphics2D;


public class Tile 
{
	private int x;
	private int y;
	private Sprite base;
	private Sprite terrain;
	private Sprite decor;
	
	//pathfinding variables
	private boolean wallNorth;
	private boolean wallEast;
	private boolean wallSouth;
	private boolean wallWest;
	
	public Tile(String baseId, String terrainId, String decorId, boolean north, boolean east, boolean south, boolean west, int posX, int posY)
	{
		try
		{
			if(Integer.parseInt(baseId) != -1)
			{
				System.out.println("Adding Base Tile of " + baseId);
				base = TileSpriteDatabase.instance().getImageSprite(baseId);
			}
			
			if(Integer.parseInt(terrainId) != -1)
			{			
				System.out.println("Adding Terrain Tile of " + terrainId);
				terrain = TileSpriteDatabase.instance().getImageSprite(terrainId);
			}
			
			if(Integer.parseInt(decorId) != -1)
			{
				//need decor database
			}
			
			wallNorth = north;
			wallEast = east;
			wallSouth = south;
			wallWest = west;
			
			x = posX;
			y = posY;
		}
		
		catch (Exception e)
		{
			System.out.println(e);
		}
		

	}
	
	public void addDecor(String decorItem)
	{
		//add block here later
	}
	
	public void update(Graphics2D g)
	{
		//order matters!
		if(base != null)
		{
			base.draw(g, x, y);
		}
		
		if(terrain != null)
		{
			terrain.draw(g,x,y);
		}
		
		if(decor != null)
		{
			decor.draw(g, x, y);
		}
	}
	

}
