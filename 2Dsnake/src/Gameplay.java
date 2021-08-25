import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.Random;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener, MouseListener {
	
	private int [] snakeXlength= new int[750];
	private int [] snakeYlength= new int[750];
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean gameover= false;
	
	private ImageIcon rightmouth;
	private ImageIcon leftmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	private ImageIcon snakeimage;
	private ImageIcon appleimage;
	private ImageIcon titleImage;
	private ImageIcon background;
	
	private int length = 3;
	private int score;
	
	private Timer timer;
	private int delay= 100;
	
	
	private int [] appleXpos = new int[34];
	private int [] appleYpos = new int[23];
	
	private int moves = 0;
		
	private Random random = new Random();
	
	//coordinates of the apple
	private int xpos =  random.nextInt(34);
	private int ypos =  random.nextInt(23);
	

    public enum STATE{
        MENU,
        GAME
    }

    public static STATE State = STATE.MENU;

	
	public Gameplay()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
		appleXpos[0]=25;
		for(int i=1; i<34; i++) {
			appleXpos[i]= appleXpos[i-1]+25;
		}
		appleYpos[0]=75;
		for(int i=1; i<23; i++) {
			appleYpos[i]= appleYpos[i-1]+25;
		}
		
	}
	
	public void paint (Graphics g)
	{
		if(moves == 0) {

			snakeXlength[2]= 50;
			snakeXlength[1]= 75;
			snakeXlength[0]= 100;
			
			snakeYlength[2]= 100;
			snakeYlength[1]= 100;
			snakeYlength[0]= 100;
			
		}
		//draw title image border
		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);
		
		//draw the title image
		titleImage = new  ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		//draw border for gameplay
		g.setColor(Color.white);
		g.drawRect(24, 74, 851, 577);
		
		if (State== STATE.GAME) {
			
			//draw the background in the game
			background = new ImageIcon("background.png");
			background.paintIcon(this, g, 25, 75);
			
			//draw the score
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.PLAIN, 14));
			g.drawString("Scores: "+score, 780, 30);
			
			rightmouth = new ImageIcon("rightmouth.png");
			rightmouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
			
			for(int a=0; a < length; a++)
			{
				if(a==0 && right) {
					rightmouth = new ImageIcon("rightmouth.png");
					rightmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
				}
				if(a==0 && left) {
					leftmouth = new ImageIcon("leftmouth.png");
					leftmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
				}
				if(a==0 && down) {
					downmouth = new ImageIcon("downmouth.png");
					downmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
				}
				if(a==0 && up) {
					upmouth = new ImageIcon("upmouth.png");
					upmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
				}
				
				if(a!=0) {
					snakeimage = new ImageIcon("snakeimage.png");
					snakeimage.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
				}
				
			}
			
			appleimage = new ImageIcon("apple.png");
			if((appleXpos[xpos] == snakeXlength[0]) && appleYpos[ypos] == snakeYlength[0]) 
			{
				score++;
				length++;
				xpos =  random.nextInt(34);
				ypos =  random.nextInt(23);
			}
			appleimage.paintIcon(this, g, appleXpos[xpos], appleYpos[ypos]);
			
			if(gameover== true) {
				
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("GAME OVER!!!", 290, 300);
				g.setFont(new Font("arial", Font.BOLD, 25));
				g.drawString("Press SPACE to try again", 300, 400);
				
				restart();

			}
			
			for(int i=1; i<= length-1; i++) {
				if(snakeXlength[i] == snakeXlength[0] && snakeYlength[i] == snakeYlength[0]) {
					
					gameover = true;
					
				}
			}
			
		}
		 else if(State == STATE.MENU)
		 {
			 	//draw the background in the menu
				background = new ImageIcon("Menu.png");
				background.paintIcon(this, g, 25, 75);
				
			 	g.setColor(Color.black);
		        g.drawRect(325, 200, 250, 50);
		        Color button= new Color(255,204,0);
		        g.setColor(button);
		        g.fillRect(326, 201, 249, 49);
		        g.setFont(new Font("arial", Font.BOLD, 25));
		        g.setColor(Color.black);
		        g.drawString("Play", 420, 230);
		        g.drawRect(325, 300, 250, 50);
		        g.setColor(button);
		        g.fillRect(326, 301, 249, 49);
		        g.setColor(Color.black);
		        g.drawString("Quit", 420, 330);
		        
	            repaint();
	        }

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		if(right)
		{
			for(int r = length-1; r>=0; r--) 
			{
				snakeYlength[r+1]= snakeYlength[r];
			}
			for(int r = length-1; r>=0; r--) 
			{
				if(r==0) 
				{
					snakeXlength[r]= snakeXlength[r]+25;
				}
				else 
				{
					snakeXlength[r]=snakeXlength[r-1];
				}
				if(snakeXlength[r] > 850)
				{	
					restart();
					gameover = true;
					
				}
			}
			repaint(); 
		}
		if(left)
		{
				for(int r = length-1; r>=0; r--)
				{
					snakeYlength[r+1]= snakeYlength[r];
				}
				for(int r = length-1; r>=0; r--) 
				{
					if(r==0)
					{
						snakeXlength[r]= snakeXlength[r]-25;
					}
					else 
					{
						snakeXlength[r]=snakeXlength[r-1];
					}
					if(snakeXlength[r] < 25)
					{
						restart();
						gameover = true;
					}
				}
				repaint(); 
		}
		if(up)
		{
			for(int r = length-1; r>=0; r--) 
			{
				snakeXlength[r+1]= snakeXlength[r];
			}
			for(int r = length-1; r>=0; r--) 
			{
				if(r==0)
				{
					snakeYlength[r]= snakeYlength[r]-25;
				}
				else 
				{
					snakeYlength[r]=snakeYlength[r-1];
				}
				if(snakeYlength[r] < 75)
				{
					restart();
					gameover = true;
				}
			}
			repaint(); 
		}
		if(down)
		{
				for(int r = length-1; r>=1; r--) 
				{
					snakeXlength[r]= snakeXlength[r-1];
				}
				for(int r = length-1; r>=0; r--) 
				{
					if(r==0)
					{
						snakeYlength[r]= snakeYlength[r]+25;
					}
					else 
					{
						snakeYlength[r]=snakeYlength[r-1];
					}
					if(snakeYlength[r] > 625)
					{
						restart();
						gameover = true;
					}
				}
				repaint(); 
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode()== KeyEvent.VK_RIGHT) 
		{
			moves++;
			if (!left) 
			{
				right = true;
			}
			else 
			{
				left = true;
				right = false;
			}
			up = false;
			down = false;
		}		
		if(e.getKeyCode()== KeyEvent.VK_LEFT) 
		{
			moves++;
			if (!right) 
			{
				left = true;
			}
			else 
			{
				right = true;
				left = false;
			}
			up = false;
			down = false;
			if(moves==1) {
				restart();
			}
		}		
		if(e.getKeyCode()== KeyEvent.VK_UP) 
		{
			moves++;
			if (!down) 
			{
				up = true;
			}
			else 
			{
				down = true;
				up = false;
			}
			right = false;
			left = false;
		}		
		if(e.getKeyCode()== KeyEvent.VK_DOWN) 
		{
			moves++;
			if (!up) 
			{
				down = true;
			}
			else 
			{
				up = true;
				down = false;
			}
			left = false;
			right = false;
		}		
		if(e.getKeyCode()== KeyEvent.VK_SPACE) {
			
			
			gameover= false;
			restart();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x= e.getX();
		int y= e.getY();
		//PlayButton
		if(x>=325 && x<=575 ){
            if(y>= 200 && y<=250){
               State = STATE.GAME;
            }
        }
		
		 //QuitButton
		if(x>=325 && x<=575 ){
			if(y>= 300 && y<=350){
              System.exit(1);
            }
        }

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void restart() {
		
		this.moves=0;
		score=0;
		length=3;
		right= false;
		left= false;
		up= false;
		down= false;
		
		repaint();
	}
	

}
