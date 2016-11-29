/*
 * Background Class 
 * 
 * Used only for Static Images 
 */

import java.awt.Graphics2D;

public class Background 
{
	//members
	private Sprite sprite;
	private Game_Wrapper origin;
	private double xPos = 0;
	private double yPos = 0;
	
	public Background(Game_Wrapper source, String imageFile, int width, int height)
	{
		sprite = SpriteDatabase.instance().getImageSprite(imageFile);
		origin = source;
		sprite.setWidth(width);
		sprite.setHeight(height);
	}
	
	public double getXPosition()
	{
		return xPos;
	}
	
	public double getYPosition()
	{
		return yPos;
	}
	
	public void setXPosition(double newX)
	{
		xPos = newX;
	}
	
	public void setYPosition(double newY)
	{
		yPos = newY;
	}
	
	public void changePosition(double x, double y)
	{
		setXPosition(x);
		setYPosition(y);
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public Game_Wrapper getSource()
	{
		return origin;
	}
	
	public void draw(Graphics2D g)
	{
		if(g != null)
		{
			sprite.draw(g, (int)xPos, (int)yPos);
			//System.out.println(sprite.getWidth() + "|" + sprite.getHeight());
		}
		
		else
		{
			System.out.println("Missing Graphics");
		}
	}
	
	public void update(Graphics2D g)
	{
		draw(g);
	}
	
}
