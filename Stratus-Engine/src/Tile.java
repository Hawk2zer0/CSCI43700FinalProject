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
	private boolean wall = false;
	
	private Rectangle self;
	
	public Tile(String baseId, String terrainId, String decorId, int posX, int posY)
	{
		try
		{
			if(Integer.parseInt(baseId) != -1)
			{
				base = TileSpriteDatabase.instance().getImageSprite(baseId);
			}
			
			if(Integer.parseInt(terrainId) != -1)
			{			
				terrain = TileSpriteDatabase.instance().getImageSprite(terrainId);
				wall = true;
			}
			
			if(Integer.parseInt(decorId) != -1)
			{
				decor = DecorSpriteDatabase.instance().getImageSprite(decorId);
				
				if(Integer.parseInt(decorId) >= 25)
				{
					wall = true;
				}
			}			
			
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
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean isBlocked()
	{
		return wall;
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
	
	public Rectangle getArea()
	{
		self = new Rectangle(x, y, base.getWidth(), base.getHeight());
		
		return self;
	}
	

}
