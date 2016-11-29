/*
 * Sidebar Class
 * 
 * Extension of Background Class that has additional sprites added to it. 
 * 
 * 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Sidebar extends Background 
{
	//member
	private int midDiv = 1090;
	private int fontY = 237;
	private int upY = 255;
	private int downY = 653;
	
	private Team player;
	private Sprite upArrow;
	private Sprite downArrow;
	ArrayList<BuildNode> nodes = new ArrayList<BuildNode>();
	
	private BuildNode nodeA;
	private BuildNode nodeB;
	private BuildNode nodeC;
	
	private int firstNodeID = 0;
	private int lastNodeID = 2;
	
	public Sidebar(Game_Wrapper source, String imageFile, int width, int height, Team team1)
	{
		super(source, imageFile, width, height);
		changePosition(source.canvas.getWidth() - source.windowXRightOffset, 0);
		
		upArrow = SpriteDatabase.instance().getImageSprite("up.png");
		upArrow.setWidth(50);
		upArrow.setHeight(50);
		
		downArrow = SpriteDatabase.instance().getImageSprite("down.png");
		downArrow.setWidth(50);
		downArrow.setHeight(50);
		
		player = team1;
		
		initNodes();
	}
	
	public void initNodes()
	{
		BuildNode powerplant = new BuildNode("powerplantIcon.png", "powerplantComplete.png", 7, 1, 500, "Power Plant");
		BuildNode barracks = new BuildNode("barracks.png", "barracksComplete.png", 10, 2, 600, "Barracks");
		BuildNode warFactory = new BuildNode("warFactory.png", "warFactoryComplete.png", 20, 3, 2000, "War Factory");
		
		nodes.add(powerplant);
		nodes.add(barracks);
		nodes.add(warFactory);
		
		resetNodes();
	}
	
	public void update(Graphics2D g)
	{
		getSprite().draw(g, getSource().canvas.getWidth() - getSource().windowXRightOffset, 0);
		g.setColor(Color.white);
		Font font = new Font("font", Font.BOLD, 20);
		g.setFont(font);
		g.drawString(player.getMoney() + "", midDiv, fontY);
		
		upArrow.draw(g, midDiv, upY);
		downArrow.draw(g, midDiv, downY);
		
		nodeA.update(g, midDiv-20, 300);
		nodeB.update(g, midDiv-20, 430);
		nodeC.update(g, midDiv-20, 560);
	}
	
	public void checkMouseHover(int mouseX, int mouseY)
	{
		Rectangle upRec = new Rectangle();
		Rectangle downRec = new Rectangle();
		
		Rectangle nodeARec = new Rectangle();
		Rectangle nodeBRec = new Rectangle();
		Rectangle nodeCRec = new Rectangle();
		
		upRec.setBounds(midDiv, upY-20, upArrow.getWidth(), upArrow.getHeight());
		downRec.setBounds(midDiv, downY-20, downArrow.getWidth(), downArrow.getHeight());
		
		nodeARec.setBounds(midDiv-20, 300, nodeA.active.getWidth(), nodeA.active.getHeight());
		nodeBRec.setBounds(midDiv-20, 430, nodeB.active.getWidth(), nodeB.active.getHeight());
		nodeCRec.setBounds(midDiv-20, 560, nodeC.active.getWidth(), nodeC.active.getHeight());
		
		Point poi = new Point (mouseX, mouseY);
		
		if(upRec.contains(poi))
		{
			upNodeList();
		}
		
		if(downRec.contains(poi))
		{
			downNodeList();
		}
		
		if(nodeARec.contains(poi))
		{
			nodeA.build();
		}
		
		if(nodeBRec.contains(poi))
		{
			nodeB.build();
		}
		
		if(nodeCRec.contains(poi))
		{
			nodeC.build();
		}
	}
	
	public void upNodeList()
	{
		if(firstNodeID > 0)
		{
			firstNodeID--;
			lastNodeID--;
			resetNodes();
		}
	}
	
	public void downNodeList()
	{
		if(lastNodeID < nodes.size()-1)
		{
			firstNodeID++;
			lastNodeID++;
			resetNodes();
		}
	}
	
	public void resetNodes()
	{
		nodeA = nodes.get(firstNodeID);
		nodeB = nodes.get(firstNodeID+1);
		nodeC = nodes.get(lastNodeID);
	}
	
	
	
}
