import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game_Wrapper extends JFrame
{
	//Panels
	public JPanel canvas;
	
	//Graphics
	private Graphics2D canvasGraphics;
	
	//Double Buffer
	private BufferStrategy master;
	
	private Set<Integer> keysPressed = new HashSet<Integer>();
	
	private TileWorld world;
	
	//World Map Files
	private String world1Map = "resources/world1Map.txt";
	
	//Game window size
	private int gameWidth = 1280;
	private int gameHeight = 720;
	
	//if an offset for the game is needed (an active area that is a partition of the total area)
	public int windowXLeftOffset = 0;
	//int windowXRightOffset = (int)(gameWidth * 0.25);
	public int windowXRightOffset = 0;
	public int windowYTopOffset = 0;
	public int windowYBottomOffset = 0;
	
	
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

    public static void main(String[] args) 
    {
        new Game_Wrapper();
    }

    //set up our game window
    public Game_Wrapper() 
    {
    	
    	
        this.setTitle("Stratus Engine"); 
        this.getContentPane().setLayout(null);
        
        Insets offset = this.getInsets();
        
        System.out.print(offset.left + " AND " + offset.top);
        
        canvas = new JPanel();       
        
        this.getContentPane().add(canvas);
        canvas.setBounds(0 + offset.left, 0 + offset.top, gameWidth, gameHeight);
        System.out.println(canvas.getLocation());
        
        this.setIgnoreRepaint(true);
        
        this.setSize(gameWidth + offset.left + offset.right, gameHeight + offset.top + offset.bottom);
        
        canvas.setBounds(0 + offset.left, 0 + offset.top, gameWidth, gameHeight);
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        //build our buffer
        this.createBufferStrategy(2);
        
        master = this.getBufferStrategy();
        
        init();
        mainloop();
    }
    
    public void init()
    {    	
    	
    	//world = new World(this, "concreteMap.jpg", "sideBar.png");
    	
    	world = new TileWorld(this, world1Map);
    	
        canvas.addMouseListener(new MouseAdapter()
        {
        	public void mousePressed(MouseEvent me)
        	{
        		if(SwingUtilities.isLeftMouseButton(me))
        		{
        			//world.checkMouseClick(me.getX(), me.getY());
        		}
        		
        		else if(SwingUtilities.isRightMouseButton(me))
        		{
        			System.out.println("RIGHT CLICK!");
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
        		
        	}

    		public void keyReleased(KeyEvent e) 
    		{
    			keysPressed.remove(e.getKeyCode());
    			System.out.println("Total Keys: " + keysPressed.size());
    		}

    		public void keyTyped(KeyEvent e) 
    		{
 			
    		}       	
        });
        
        System.out.println("Frame Size: " + this.getWidth() + " x " + this.getHeight());
        System.out.println("Canvas Size: " + canvas.getWidth() + " x " + canvas.getHeight());

    }
    
    public void mainloop()
    {
    	while(running)
    	{
    		currentTime = System.currentTimeMillis();
    		
    		//prep double buffering;
            canvasGraphics = (Graphics2D) master.getDrawGraphics();

    		canvas.paint(canvasGraphics);
    		
    		super.paint(canvasGraphics);
    		//this.paint(canvasGraphics);    		
    		canvasGraphics.setColor(Color.black);
    		canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    		
    		if(lastTime == 0 || ((currentTime - lastTime) >= msPerFrame))
    		{
    			
				world.updateTiles(canvasGraphics);	
    			
				canvasGraphics.dispose();
				master.show();
				
				lastTime = currentTime;   
    		} 
    		
    		try
    		{
    			Thread.sleep(10);
    		}
    		
    		catch(Exception e)
    		{
    			System.out.println("I can't sleep, you're too loud!");
    		}
    		

    	}
    	
    }


}