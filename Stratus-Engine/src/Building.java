/*
 * Building Class Extension of the Entity class that has custom collisions
 * 
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Building extends Entity 
{
	//members
	private int buildRangeOffset;
	private int owner;	
	private Rectangle buildRange = new Rectangle();
	
	public Building(TileWorld source, String imageFile, int width, int height,int offset)
	{
		super(source, imageFile, width, height);
		buildRangeOffset = offset;
		
		buildRange.setBounds((int) getXPosition(), (int) getYPosition(), getSprite().getWidth() + (2*offset), getSprite().getHeight() + (2*offset));
	}
	
	public void setOwner(int newOwner)
	{
		owner = newOwner;
	}
	
	public int getOwner()
	{
		return owner;
	}
	
	public Rectangle getBuildRadius()
	{
		return buildRange;
	}
	
	public void updateTiles()
	{
		
		for(ArrayList<Tile> tileList : getSource().getWorld())
		{
			for(Tile piece : tileList)
			{
				if(piece.getArea().intersects(getBoundingBox()))
				{
					piece.block();
					System.out.println("Tile at (" + piece.getX() + "," + piece.getY() +  ") blocked due to building");
				}
			}
		}
	}
	
	

}
