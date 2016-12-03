import java.awt.Rectangle;

/*
 * Camera Class 
 * 
 * Purpose to do reflect a camera and draw what it contains.  
 * 
 */


public class Camera
{
	//Integer Bounds to reflect location in regards to the map 
	private int mapLeft = 0;
	private int mapTop = 0;
	private int mapBottom = 0;
	private int mapRight = 0;
	
	//Integer Bounds for map Max Size
	private int maxX = 0;
	private int maxY = 0;	
	
	//Integer Bounds for Camera Size
	private int cWidth = 0;
	private int cHeight = 0;
	
	private int scrollSpeed = 10;
	
	public Camera(int width, int height, int mapWidth, int mapHeight)
	{
		cWidth = width;
		cHeight = height;
		
		mapRight = width;
		mapBottom = height;
		
		maxX = mapWidth;
		maxY = mapHeight;
	}
	
	public void setPosition(int xOffset, int yOffset)
	{
		mapLeft = mapLeft + xOffset;
		mapRight = mapRight + xOffset;
		mapTop = mapTop + yOffset;
		mapBottom = mapBottom + yOffset;
	}
	
	//Getters (No Setters since this is change occurs on its own and maxSize can't be changed)
	
	public int getLeft()
	{
		return mapLeft;
	}
	
	public int getRight()
	{
		return mapRight;
	}
	
	public int getTop()
	{
		return mapTop;
	}
	
	public int getBottom()
	{
		return mapBottom;
	}
	
	public int getScrollSpeed()
	{
		return scrollSpeed;
	}
	
	public void setScrollSpeed(int speed)
	{
		scrollSpeed = speed;
	}
	
	public Rectangle getCamera()
	{
		Rectangle cameraRect = new Rectangle(mapLeft, mapTop, cWidth, cHeight);
		
		return cameraRect;
	}
	
	// Camera Shift Methods - Logic for camera movement
	
	public void leftShift()
	{		
		if(mapLeft > 0)
		{
			mapLeft = mapLeft - scrollSpeed;
			mapRight = mapRight - scrollSpeed;
		}		
	}
	
	public void rightShift()
	{		
		if(mapRight < maxX-1)
		{
			mapLeft = mapLeft + scrollSpeed;
			mapRight = mapRight + scrollSpeed;
		}
	}
	
	public void upShift()
	{		
		if(mapTop > 0)
		{
			mapTop = mapTop - scrollSpeed;
			mapBottom = mapBottom - scrollSpeed;
		}
	}
	
	public void downShift()
	{		
		if(mapBottom < maxY-1)
		{
			mapTop = mapTop + scrollSpeed;
			mapBottom = mapBottom + scrollSpeed;
		}
	}
	
	
}
