import java.awt.Graphics2D;

public class Sidebar 
{
	//Image Sprite for SideBar
	private Sprite imageBase;
	
	//Integers for location
	private int imageX;
	private int imageY;
	
	public Sidebar(int xPos, int yPos, int width, int height)
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
	}
	
	public void update(Graphics2D g)
	{
		imageBase.draw(g, imageX, imageY);
	}
	
	public int getSidebarX()
	{
		return imageX;
	}
	
	public int getSidebarY()
	{
		return imageY;
	}
}
