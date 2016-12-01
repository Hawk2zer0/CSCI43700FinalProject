import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Tile 
{
	//Integers to keep track of the tiles position based on the map
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
	
	private Rectangle self;
	
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
				System.out.println("Adding Decor Tile of " + decorId);
				decor = DecorSpriteDatabase.instance().getImageSprite(decorId);
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
	
	public int getHeight()
	{
		return base.getHeight();
	}
	
	public int getWidth()
	{
		return base.getWidth();
	}
	
	public void addDecor(String decorItem)
	{
		//add block here later
	}
	
	public void checkDraw(Graphics2D g, Rectangle camera)
	{
		//check if our tile collides with the camera, if it does, then draw!
		self = new Rectangle(x, y, base.getWidth(), base.getHeight());
		
		if(self.intersects(camera))
		{
			int offsetX = x - camera.x;
			int offsetY = y - camera.y;			
			
			//order matters!
			if(base != null)
			{
				base.draw(g, offsetX, offsetY);
			}
			
			if(terrain != null)
			{
				terrain.draw(g, offsetX, offsetY);
			}
			
			if(decor != null)
			{
				decor.draw(g, offsetX, offsetY);
			}
		}
		
	}
	

}
