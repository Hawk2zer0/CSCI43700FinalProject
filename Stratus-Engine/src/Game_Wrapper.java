import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game_Wrapper extends JFrame
{
	//Panels
	public GameCanvas canvas;
	
	//Game window size
	public int gameWidth = 1280;
	public int gameHeight = 720;
	
	//if an offset for the game is needed (an active area that is a partition of the total area)
	public int windowXLeftOffset = 0;
	//int windowXRightOffset = (int)(gameWidth * 0.25);
	public int windowXRightOffset = 0;
	public int windowYTopOffset = 0;
	public int windowYBottomOffset = 0;

    public static void main(String[] args) 
    {
    	new Game_Wrapper();
    }

    //set up our game window
    public Game_Wrapper() 
    {    	    	
        this.setTitle("Stratus Engine"); 
        this.getContentPane().setLayout(new GridBagLayout());
        
        GridBagConstraints config = new GridBagConstraints();
        
        canvas = new GameCanvas(gameWidth, gameHeight);
        
        config.fill = GridBagConstraints.BOTH;
        config.gridx = 0;
        config.gridy = 0;
        this.add(canvas, config);
        
        Insets offset = this.getInsets();
        this.setBounds(offset.left, offset.top, gameWidth, gameHeight);
        
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        canvas.init();
        canvas.mainloop();
    }

}