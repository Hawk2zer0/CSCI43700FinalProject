/*
 * Unit_Factory Class
 * 
 * Follows the Abstract Factory Pattern slightly (no common inheritance)
 * 
 */
public class Unit_Factory 
{
	//members
	private Game_Wrapper origin;
	
	public Unit_Factory(Game_Wrapper source)
	{
		origin = source;
	}
	
	public Entity makeUnit(String imgFile, int width, int height)
	{
		Entity temp = new Entity(origin, imgFile, width, height);
		
		return temp;
	}
}
