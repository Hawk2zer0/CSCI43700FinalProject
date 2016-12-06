/*
 * Entity Class - Similar to the JavaScript Sprite class 
 * 
 */

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class Entity
{
	//Members that will be set before initialization for all instances
	private Sprite sprite;
	protected TileWorld origin;
	private double xPos = 300;
	private double yPos = 400;
	private double xVelo = 0;
	private double yVelo = 0;
	private double imgAngle = 0;
	private double speed = 0;
	private boolean active = true;
	private int health = 10;
	private double moveAngle = 0;
	private int action = 1;
	
	//Bound behavior action
	public int LOOP = 1;
	public int DENY = 2;
	
	//collision - Bounding Box
	private Rectangle self = new Rectangle();
	private Rectangle other = new Rectangle();
	
	public Entity(TileWorld source, String imageFile, int width, int height)
	{
		origin = source;
		try
		{
			sprite = SpriteDatabase.instance().getImageSprite(imageFile);
			sprite.setHeight(height);
			sprite.setWidth(width);
		}
		
		catch (Exception e)
		{
			
		}
		
	}
	
	public void changeSpeed(double newSpeed)
	{
		speed = speed + newSpeed;
		calculateMovementVector();
	}
	
	//set Speed without causing it to move
	public void setSpeed(double newSpeed)
	{
		speed = newSpeed;
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
	public void adjustSpeed()
	{
		speed = Math.sqrt(Math.pow(xVelo, 2) + Math.pow(yVelo, 2));
	}
	
	public void setBoundsAction(int type)
	{
		action = type;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void setHealth(int newHealth)
	{
		health = newHealth;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public TileWorld getSource()
	{
		return origin;
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
	
	public void setXVelocity(double newVelocity)
	{
		xVelo = newVelocity;
	}
	
	public void setYVelocity(double newVelocity)
	{
		yVelo = newVelocity;
	}
	
	public double getXVelocity()
	{
		return xVelo;
	}
	
	public double getYVelocity()
	{
		return yVelo;
	}
	
	public void setImgAngle(double degrees)
	{
		imgAngle = degrees * (Math.PI/180);
	}
	
	public double getImgAngle()
	{
		return (imgAngle / (Math.PI/180));
	}
	
	public void changeImgAngleBy(double degrees)
	{
		imgAngle = imgAngle + (degrees * (Math.PI/180));
		if(imgAngle > (2 * Math.PI))
		{
			imgAngle = imgAngle - (2 * Math.PI);
		}
	}
	
	public void setMoveAngle(double degrees)
	{
		moveAngle = degrees * (Math.PI/180);
		calculateMovementVector();
	}
	
	public double getMoveAngle()
	{
		return (moveAngle / (Math.PI/180));
	}
	
	public Rectangle getBoundingBox()
	{
		self.setBounds((int)xPos, (int)yPos, sprite.getWidth(), sprite.getHeight());
		return self;
	}
	
	public void changeMoveAngleBy(double degrees)
	{
		moveAngle = moveAngle + (degrees * (Math.PI/180));
		if(moveAngle > (2 * Math.PI))
		{
			moveAngle = moveAngle - (2 * Math.PI);
		}
	}
	
	public void setAngles(double degrees)
	{
		setImgAngle(degrees);
		setMoveAngle(degrees-90);
	}
	
	public void changeAnglesBy(double degrees)
	{
		changeImgAngleBy(degrees);
		changeMoveAngleBy(degrees);
		adjustSpeed();
	}
	
	public void calculateMovementVector()
	{
		xVelo = speed * Math.cos(moveAngle);
		yVelo = speed * Math.sin(moveAngle);
	}
	
	public void addMovementVector(double speed)
	{
		double addDX = speed * Math.cos(moveAngle);
		double addDY = speed * Math.sin(moveAngle);
		
		xVelo = xVelo + addDX;
		yVelo = yVelo + addDY;
		
		adjustSpeed();
	}
	
	//virtual function to override
	public void moveTo(int mouseX, int mouseY) 
	{
		
	}
	
	public void stopMove()
	{
		xVelo = 0;
		yVelo = 0;
	}
	
	public void checkDraw(Graphics2D g, Rectangle camera)
	{
		self.setBounds((int) xPos, (int) yPos, sprite.getWidth(), sprite.getHeight());
		
		//our Sprite is somewhat (if not entirely) on screen, we need to draw them on.
		if(self.intersects(camera))
		{
			//get offsets
			int offsetX = (int)xPos - camera.x;
			int offsetY = (int)yPos - camera.y;	
			
			AffineTransform adjust = new AffineTransform();

			adjust.rotate(imgAngle, offsetX + sprite.getWidth()/2, offsetY + sprite.getHeight()/2);
			g.setTransform(adjust);
			
			sprite.draw(g, offsetX, offsetY);
			adjust.rotate(-imgAngle, offsetX + sprite.getWidth()/2, offsetY + sprite.getHeight()/2);
			g.setTransform(adjust);
			
			
		}
	}
	
	public void die()
	{
		active = false;
	}
	
	public void update(Graphics2D g, Rectangle camera)
	{
		if(active)
		{
			xPos = xPos + xVelo;
			yPos = yPos + yVelo;
			checkBounds();			
			checkDraw(g, camera);
		}
	}
	
	public void checkBounds()
	{
		if(action == LOOP)
		{
			if (xPos > origin.getWorldWidth())
			{
				xPos = 0;
			}
			
			else if(xPos < 0)
			{
				xPos = origin.getWorldWidth();
			}
			
			if(yPos > origin.getWorldHeight())
			{
				yPos = 0;
			}
			
			else if(yPos < 0)
			{
				yPos = origin.getWorldHeight();
			}
		}
		
		if(action == DENY)
		{
			if (xPos > origin.getWorldWidth())
			{
				xPos =  origin.getWorldWidth();
			}
			
			else if(xPos < 0)
			{
				xPos = 0;
			}
			
			if(yPos > origin.getWorldHeight())
			{
				yPos = origin.getWorldHeight();
			}
			
			else if(yPos < 0)
			{
				yPos = 0;
			}
		}
		
	}
	
	public boolean checkCollision(Entity e2)
	{
		self.setBounds((int) xPos, (int) yPos, sprite.getWidth(), sprite.getHeight());
		other.setBounds((int) e2.getXPosition(), (int) e2.yPos, e2.sprite.getWidth(), e2.sprite.getHeight());
		
		return self.intersects(other);
	}
	
	public boolean isMouseOver(int mX, int mY)
	{
		self.setBounds((int) xPos, (int) yPos, sprite.getWidth(), sprite.getHeight());
		Point mouse = new Point(mX, mY);
		
		return self.contains(mouse);
	}
	
	
}
