import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

public class GameCanvas extends Canvas
{
	//Double Buffer
	private BufferStrategy master;
	
	//World
	private TileWorld world;
	
	private int width;
	private int height;
	
	//Graphics
	private Graphics2D canvasGraphics;
	
	//Set for tracking keysPressed
	private Set<Integer> keysPressed = new HashSet<Integer>();
	private boolean shiftFlag = false;
	
	//World Map Files
	private String world1Map = "resources/mapData/world1Map.txt";
	
	//Frame Rate Variables/Constants
	private long currentTime;
	private long lastTime = 0;
	//frame rate constants
	private double frames30 = 33.3;
	private double frames60 = 16.7;
	//frame rate - 30 frames => 33.3 ms/Frame
	//frame rate - 60 frames => 16.6 ms/Frame
	private double msPerFrame = frames60;
	
	//Game Variables
	private boolean running = true;
	
	public GameCanvas(int gameWidth, int gameHeight)
	{
		setBackground(Color.blue);
		width = gameWidth;
		height = gameHeight;
	}
	
	public void init()
    {
    	
    	world = new TileWorld(this, world1Map);
    	
        this.addMouseListener(new MouseAdapter()
        {
        	public void mousePressed(MouseEvent me)
        	{
        		if(SwingUtilities.isLeftMouseButton(me))
        		{
        			if(shiftFlag)
        			{
        				world.compareClick(me, true);
        			}
        			
        			else
        			{
        				world.compareClick(me,  false);
        			}
        		}
        		
        		else if(SwingUtilities.isRightMouseButton(me))
        		{
        			world.handleRightClick();
        		}
        		
        	}
        	
        });
        
        this.addKeyListener(new KeyListener()
        {
        	public void keyPressed(KeyEvent e)
        	{
        		keysPressed.add(e.getKeyCode());
        		
        		System.out.println("Total Keys: " + keysPressed.size());
        		
        		if (keysPressed.contains(e.VK_ESCAPE))
        		{
        			System.exit(0);
        		}
        		
        		if(keysPressed.contains(e.VK_LEFT))
        		{
        			world.adjustCamera("left", canvasGraphics);
        		}
        		
        		if(keysPressed.contains(e.VK_RIGHT))
        		{
        			world.adjustCamera("right", canvasGraphics);
        		}
        		
        		if(keysPressed.contains(e.VK_DOWN))
        		{
        			world.adjustCamera("down", canvasGraphics);
        		}
        		
        		if(keysPressed.contains(e.VK_UP))
        		{
        			world.adjustCamera("up", canvasGraphics);
        		}
        		
        		if(keysPressed.contains(e.VK_SHIFT))
        		{
        			shiftFlag = true;
        		}
        		
        	}

    		public void keyReleased(KeyEvent e) 
    		{
    			keysPressed.remove(e.getKeyCode());
    			System.out.println("Total Keys: " + keysPressed.size());
        		if(!keysPressed.contains(e.VK_SHIFT))
        		{
        			shiftFlag = false;
        		}
    		}

    		public void keyTyped(KeyEvent e) 
    		{
 			
    		}       	
        });

    }
	
	public void mainloop()
    {        
        createBufferStrategy(2);
    	
    	while(running)
    	{
    		currentTime = System.currentTimeMillis();
    		
    		//prep active double buffering;
    		master = getBufferStrategy();
    		
            canvasGraphics = (Graphics2D) master.getDrawGraphics();
            
            Point tracker = this.getMousePosition();

    		this.paint(canvasGraphics);
    		
    		super.paint(canvasGraphics);
    		//this.paint(canvasGraphics);    		
    		canvasGraphics.setColor(Color.black);
    		canvasGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
    		
    		if(lastTime == 0 || ((currentTime - lastTime) >= msPerFrame))
    		{
    			
				world.update(canvasGraphics, tracker);
    			
				canvasGraphics.dispose();
				master.show();
				
				lastTime = currentTime;   
    		} 
    		

    	}
    	
    }
	
	//To force a specific size
	
	@Override
	public Dimension getPreferredSize() 
	{
	    return new Dimension(width, height);
	}
	
	@Override
	public Dimension getMinimumSize() 
	{
	    return getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize() 
	{
	    return getPreferredSize();
	}
}