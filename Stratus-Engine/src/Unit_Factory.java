/*
 * Unit_Factory Class
 * 
 * Follows the Abstract Factory Pattern slightly (no common inheritance)
 * Currently has yet to be implemented (NOT IN USE!)
 * 
 */
public class Unit_Factory 
{
	//members
	private TileWorld origin;
	
	public Unit_Factory(TileWorld source)
	{
		origin = source;
	}
	
	public Entity makeUnit(String imgFile, int width, int height)
	{
		Entity temp = new Entity(origin, imgFile, width, height);
		
		return temp;
	}
}
