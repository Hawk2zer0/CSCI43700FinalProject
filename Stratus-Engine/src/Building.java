/*
 * Building Class Extension of the Entity class that has custom collisions
 * 
 */

import java.awt.Rectangle;

public class Building extends Entity 
{
	//members
	private int buildRangeOffset;
	
	private Rectangle buildRange = new Rectangle();
	
	public Building(TileWorld source, String imageFile, int width, int height,int offset)
	{
		super(source, imageFile, width, height);
		buildRangeOffset = offset;
		
		buildRange.setBounds((int) getXPosition(), (int) getYPosition(), getSprite().getWidth(), getSprite().getHeight());
	}
	
	
}
