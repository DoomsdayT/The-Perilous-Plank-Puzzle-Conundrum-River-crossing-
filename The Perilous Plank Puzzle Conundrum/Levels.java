
/**
 * 在这里给出对类 finalv 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
 
import java.awt.HeadlessException;    
import java.awt.BorderLayout;    
import java.awt.FlowLayout;    
import java.awt.Font;       

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;  
import java.io.FileReader;  
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.FileNotFoundException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.io.*;

public class Levels
{
    int Row=13;
    int Col=9;
    int c=0;
    int r=0;
    int lastR=0;
    int lastC=0;
    int gridWidth=9;
    int gridHeight=13;
    int panelHeight=150;
    int panelWidth=675;
    int aWidth=505;
    int aHeight=512;
    int cpHeight= aHeight-(panelHeight*2);
    int btnHeight=40;
    int btnWidth=200;
    int lvl=1;
    int plank=0;
    int manR=0;
    int manC=0;
    int thisScore=0;
    int [] highScore= new int[4];
    JPanel map = new JPanel();
    JButton[][] Map = new JButton[Row][Col];
    JLabel instructions = new JLabel();
    private static final long serialVersionUID = 1L;
    private long programStart = System.currentTimeMillis();
    private long pauseStart = programStart;   
    private long pauseCount = 0; 
    JLabel timerlabel = new JLabel("00:00");
    JLabel highscore = new JLabel(String.format("Highscore: --"));
    JButton[][] show = new JButton[3][3];
    
    private JFrame window;
    
    public Levels()
    {
        window =new JFrame();
        GridLayout Glayout=new GridLayout(13,9);
        BorderLayout Blayout=new BorderLayout();
        ImageIcon bank1 = new ImageIcon("bank1.jpg");
        ImageIcon bank2 = new ImageIcon("bank2.jpg");
        ImageIcon water1 = new ImageIcon("water1.jpg");
        ImageIcon plankman1 = new ImageIcon("plank1_man.jpg");
        ImageIcon plank1 = new ImageIcon("plank1.jpg");
        ImageIcon plankman2 = new ImageIcon("plank2_man.jpg");
        ImageIcon plank2 = new ImageIcon("plank2.jpg");
        ImageIcon stumpman1 = new ImageIcon("stump1_man.jpg");
        ImageIcon stump1 = new ImageIcon("stump1.jpg");
        ImageIcon stumpman2 = new ImageIcon("stump2_man.jpg");
        ImageIcon stump2 = new ImageIcon("stump2.jpg");
        ImageIcon stumpman3 = new ImageIcon("stump3_man.jpg");
        ImageIcon stump3 = new ImageIcon("stump3.jpg");
        ImageIcon water2 = new ImageIcon("water2.jpg");
        ImageIcon water3 = new ImageIcon("water3.jpg");
        ImageIcon water4 = new ImageIcon("water4.jpg");
        
        //Read the highscore from the file
        try 
        {
            BufferedReader in = new BufferedReader(new FileReader("/Users/tony/Documents/course work/The Perilous Plank Puzzle Conundrum/highscore.txt"));
            for(int i=0;i<4;i++)
            {
                highScore[i]=Integer.parseInt(in.readLine());
            }
            in.close();
        }
        catch(FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch(IOException e1)
        {
            e1.printStackTrace();
        }
        //Panel for the map
        map.setSize(288,416);
        map.setLayout(new GridLayout(gridHeight, gridWidth));
	window.add(map, BorderLayout.CENTER);
	
	//Adds a JPanel for the Users heads up display
	JPanel Right = new JPanel();
	Right.setSize(aWidth-panelWidth, cpHeight);
	Right.setLayout(new BorderLayout());
	window.add(Right, BorderLayout.EAST);
				
	//Adds a JPanel to hold the restart Map
	JPanel Restart = new JPanel();
	Right.add(Restart, BorderLayout.NORTH);
	
	//This is the JButton for the restart Map (added to the JPanel)
	JButton restart = new JButton("RESTART LEVEL");
	restart.setPreferredSize(new Dimension(btnWidth, btnHeight));
		
	//This adds an action listener to the reset Map, allowing you to input what function you need the Map the perform. In this case, restart the level.
	restart.addActionListener(new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			restart();
		}
	});
	Restart.add(restart, BorderLayout.NORTH);
        
        //This is the JButton for the quit Map (added to the JPanel)
	JButton quit = new JButton("QUIT");
	quit.setPreferredSize(new Dimension(btnWidth, btnHeight));
	restart.setPreferredSize(new Dimension(btnWidth, btnHeight));
	
	//Adds a JPanel for the quit Map
	JPanel qHud = new JPanel();
	Right.add(qHud, BorderLayout.SOUTH);
		
	//This adds an action listener to the quit Map, allowing you to input what function you need the Map the perform. In this case, close the window.
	quit.addActionListener(new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			window.dispose();
		}
	});
	qHud.add(quit, BorderLayout.SOUTH);
	
	//Adds a JPanel to show the items that the player is currently holding
	JPanel holding = new JPanel();
	holding.setLayout(new GridLayout(3,0));
	Right.add(holding, BorderLayout.CENTER);
				
	//Creates a JLabel saying "You are carrying:"
	JLabel carry = new JLabel("You are carrying:");
	holding.add(carry, BorderLayout.NORTH);
	
	//Creates a JLabel showing what the user is carrying
	JPanel Show = new JPanel();
	Show.setLayout(new GridLayout(3,3,0,0));
	Show.setPreferredSize(new Dimension(100, 100));
	holding.add(Show, BorderLayout.CENTER);
	for(int row=0;row<3;row++)
        {
            for(int col=0;col<3;col++) 
            {
                show[row][col]= new JButton(water1);
                show[row][col].setPreferredSize(new Dimension(32, 32));
                show[row][col].setBorderPainted(true);
                Show.add(show[row][col]);
            }
        }
		
	//Creates a north JPanel that holds the high score and timer JLabels
	JPanel north = new JPanel();
	north.setSize(aWidth, panelHeight);
	north.setLayout(new FlowLayout(FlowLayout.CENTER));
	north.setBackground(Color.GREEN);
	window.add(north, BorderLayout.NORTH);
				
	//Adds a JPanel to north that holds the high score
	JPanel nWest = new JPanel();
	nWest.setSize(aWidth/2, panelHeight);
	nWest.setBackground(Color.GREEN);
	nWest.setLayout(new FlowLayout(FlowLayout.LEFT));
	north.add(nWest);
				
	//Adds a JPanel to north that holds the high score
	JPanel nEast = new JPanel();
	nEast.setSize(aWidth/2, panelHeight);
	nEast.setBackground(Color.GREEN);
	nEast.setLayout(new FlowLayout(FlowLayout.RIGHT));
	north.add(nEast);
	
	//Adds a JPanel to count the time
	JPanel Timer = new JPanel(new BorderLayout());
	Timer.setSize(300,101);
	Timer.setBackground(Color.GREEN);
	Timer.setLayout(new FlowLayout());
	Timer.add(timerlabel);
	nWest.add(Timer);
	
				
	//Creates a south JPanels that hold the instructions
	JPanel south = new JPanel();
	south.setSize(aWidth, panelHeight);
	south.setLayout(new FlowLayout(FlowLayout.LEFT));
	south.setBackground(Color.GREEN);
	window.add(south, BorderLayout.SOUTH);
				
	//Creates a JLabel that can hold the high score
        instructions.setText("Right click: move.  Left click on plank or water: collect plank or put plank.");
	south.add(instructions);
	//Creates a JLabel that can hold the high score
	nWest.add(highscore);
	
	//Creates a JLabel that hold the usage of the time for each level
	
	 class CountingThread extends Thread { 
	     
        public boolean stopped = false;  
        int minute, second;
     
        private CountingThread() {    
            setDaemon(true);    
        }    
  
        
        @Override    
        public void run() {    
            while (true) {    
                if (!stopped) {    
                    long elapsed = System.currentTimeMillis() - programStart - pauseCount;    
                    timerlabel.setText(format(elapsed));
                    if(lvl<=1)
                    {
                        highscore.setText("Highscore: "+ highScore[lvl-1]);
                    }
                    else
                    {
                        highscore.setText("Highscore for level"+lvl+" is : "+ highScore[lvl-1]+" Highscore for level"+(lvl-1)+" now is : "+ highScore[lvl-2]);
                    }
                } 
                try {    
                    sleep(1);  //Refresh every millisecond 
                } catch (InterruptedException e) {    
                    e.printStackTrace();    
                    System.exit(1);    
                }    
            }    
        }    
     
        //set a format for the timer
        private String format(long elapsed) {    
            
            elapsed = elapsed / 1000; 
            second = (int) (elapsed % 60);    
            elapsed = elapsed / 60;    
     
            minute = (int) (elapsed % 60);    
            elapsed = elapsed / 60; 
            thisScore=90-(minute*60+second);
            
            return String.format("%02d:%02d", minute, second);    
        }    
    }    
        CountingThread thread = new CountingThread();
        thread.start();
	
	for(int row=0;row<13;row++)
        {
            for(int col=0;col<9;col++) 
            {
                if(row==0)
                {
                    Map[row][col]= new JButton(bank2);
                    Map[row][col].setName("bank2");
                }
                else if(row==12)
                {
                    Map[row][col]= new JButton(bank1);
                    Map[row][col].setName("bank1");
                }
                else
                {
                    Map[row][col]= new JButton(water1);
                    Map[row][col].setName("water1");
                }
                Map[row][col].setBorderPainted(false);
                map.add(Map[row][col]);
            }
        }
        if(lvl==1)
        {
                Map[12][4].setIcon(stumpman2);
		Map[12][4].setName("stumpman2");
		Map[11][4].setIcon(plank2);
		Map[11][4].setName("plank2");
		Map[10][4].setIcon(plank2);
		Map[10][4].setName("plank2");
		Map[9][4].setIcon(plank2);
		Map[9][4].setName("plank2");
		Map[8][4].setIcon(stump1);
		Map[8][4].setName("stump1");
		Map[4][4].setIcon(stump1);
		Map[4][4].setName("stump1");
		Map[0][4].setIcon(stump3);
		Map[0][4].setName("stump3");
		manR=12;
		manC=4;
        }
        if(lvl==2)
        {
		Map[12][4].setIcon(stumpman2);
		Map[12][4].setName("stumpman2");
		Map[11][4].setIcon(plank2);
		Map[11][4].setName("plank2");
		Map[10][4].setIcon(plank2);
		Map[10][4].setName("plank2");
		Map[9][4].setIcon(plank2);
		Map[9][4].setName("plank2");
		Map[8][4].setIcon(stump1);
		Map[8][4].setName("stump1");
		Map[10][0].setIcon(stump1);
		Map[10][0].setName("stump1");
		Map[9][0].setIcon(plank2);
		Map[9][0].setName("plank2");
		Map[8][0].setIcon(stump1);
		Map[8][0].setName("stump1");
		Map[6][0].setIcon(stump1);
		Map[6][0].setName("stump1");
		Map[6][2].setIcon(stump1);
		Map[6][2].setName("stump1");
		Map[6][3].setIcon(plank1);
		Map[6][3].setName("plank1");
		Map[6][4].setIcon(plank1);
		Map[6][4].setName("plank1");
		Map[6][5].setIcon(plank1);
		Map[6][5].setName("plank1");
		Map[6][6].setIcon(stump1);
		Map[6][6].setName("stump1");
		Map[4][4].setIcon(stump1);
		Map[4][4].setName("stump1");
		Map[2][0].setIcon(stump1);
		Map[2][0].setName("stump1");
		Map[0][4].setIcon(stump3);
		Map[0][4].setName("stump3");
		manR=12;
		manC=4;
	}	
	else if(lvl==3)
	{
	        Map[12][2].setIcon(stumpman2);
	        Map[12][2].setName("stumpman2");
	        Map[11][2].setIcon(plank2);
		Map[11][2].setName("plank2");
		Map[10][2].setIcon(stump1);
		Map[10][2].setName("stump1");
		Map[10][0].setIcon(stump1);
		Map[10][0].setName("stump1");
		Map[10][6].setIcon(stump1);
		Map[10][6].setName("stump1");
		Map[10][8].setIcon(stump1);
		Map[10][8].setName("stump1");
		Map[9][0].setIcon(plank2);
		Map[9][0].setName("plank2");
		Map[9][8].setIcon(plank2);
		Map[9][8].setName("plank2");
		Map[8][0].setIcon(plank2);
		Map[8][0].setName("plank2");
		Map[8][8].setIcon(plank2);
		Map[8][8].setName("plank2");
		Map[8][6].setIcon(stump1);
		Map[8][6].setName("stump1");
		Map[7][0].setIcon(plank2);
		Map[7][0].setName("plank2");
		Map[7][8].setIcon(plank2);
		Map[7][8].setName("plank2");
		Map[7][6].setIcon(plank2);
		Map[7][6].setName("plank2");
		Map[6][0].setIcon(stump1);
		Map[6][0].setName("stump1");
		Map[6][4].setIcon(stump1);
		Map[6][4].setName("stump1");
		Map[6][8].setIcon(stump1);
		Map[6][8].setName("stump1");
		Map[6][6].setIcon(stump1);
		Map[6][6].setName("stump1");
		Map[5][6].setIcon(plank2);
		Map[5][6].setName("plank2");
		Map[4][6].setIcon(plank2);
		Map[4][6].setName("plank2");
		Map[4][2].setIcon(stump1);
		Map[4][2].setName("stump1");
		Map[4][8].setIcon(stump1);
		Map[4][8].setName("stump1");
		Map[3][6].setIcon(plank2);
		Map[3][6].setName("plank2");
		Map[2][0].setIcon(stump1);
		Map[2][0].setName("stump1");
		Map[2][6].setIcon(stump1);
		Map[2][6].setName("stump1");
		Map[2][8].setIcon(stump1);
		Map[2][8].setName("stump3");
		Map[0][2].setIcon(stump3);
		Map[0][2].setName("stump3");
		Map[8][2].setIcon(stump1);
		Map[8][2].setName("stump1");
		manR=12;
		manC=2;
	}
	if(lvl==4)
	{
	        Map[12][1].setIcon(stumpman2);
	        Map[12][1].setName("stumpman2");
	        Map[11][1].setIcon(plank2);
	        Map[11][1].setName("plank2");
	        Map[10][1].setIcon(plank2);
	        Map[10][1].setName("plank2");
		Map[9][1].setIcon(stump1);
		Map[9][1].setName("stump1");
	        Map[8][1].setIcon(plank2);
	        Map[8][1].setName("plank2");
	        Map[7][1].setIcon(plank2);
	        Map[7][1].setName("plank2");
	        Map[6][1].setIcon(plank2);
	        Map[6][1].setName("plank2");
		Map[5][1].setIcon(stump1);
		Map[5][1].setName("stump1");
	        Map[4][1].setIcon(plank2);
	        Map[4][1].setName("plank2");
	        Map[3][1].setIcon(plank2);
	        Map[3][1].setName("plank2");
		Map[2][1].setIcon(stump1);
		Map[2][1].setName("stump1");
		Map[5][4].setIcon(stump1);
		Map[5][4].setName("stump1");
		Map[5][7].setIcon(stump1);
		Map[5][7].setName("stump1");
	        Map[6][7].setIcon(plank2);
	        Map[6][7].setName("plank2");
		Map[7][7].setIcon(stump1);
		Map[7][7].setName("stump1");
		Map[3][4].setIcon(stump1);
		Map[3][4].setName("stump1");
		Map[0][4].setIcon(stump3);
		Map[0][4].setName("stump3");
		manR=12;
		manC=1;
	}
	if(lvl==5)
	{
		Map[0][0].setIcon(stump3);
		Map[0][0].setName("stump3");
		Map[0][8].setIcon(stump3);
		Map[0][8].setName("stump3");
		Map[1][0].setIcon(stump1);
		Map[1][0].setName("stump1");
		Map[2][0].setIcon(stump1);
		Map[2][0].setName("stump1");
		Map[3][1].setIcon(stump1);
		Map[3][1].setName("stump1");
		Map[4][1].setIcon(stump1);
		Map[4][1].setName("stump1");
		Map[5][1].setIcon(stump1);
		Map[5][1].setName("stump1");
		Map[6][2].setIcon(stump1);
		Map[6][2].setName("stump1");
		Map[7][2].setIcon(stump1);
		Map[7][2].setName("stump1");
		Map[8][2].setIcon(stump1);
		Map[8][2].setName("stump1");
		Map[9][3].setIcon(stump1);
		Map[9][3].setName("stump1");
		Map[10][3].setIcon(stump1);
		Map[10][3].setName("stump1");
		Map[11][3].setIcon(stump1);
		Map[11][3].setName("stump1");
		Map[1][8].setIcon(stump1);
		Map[1][8].setName("stump1");
		Map[2][8].setIcon(stump1);
		Map[2][8].setName("stump1");
		Map[3][7].setIcon(stump1);
		Map[3][7].setName("stump1");
		Map[4][7].setIcon(stump1);
		Map[4][7].setName("stump1");
		Map[5][7].setIcon(stump1);
		Map[5][7].setName("stump1");
		Map[6][6].setIcon(stump1);
		Map[6][6].setName("stump1");
		Map[7][6].setIcon(stump1);
		Map[7][6].setName("stump1");
		Map[8][6].setIcon(stump1);
		Map[8][6].setName("stump1");
		Map[9][5].setIcon(stump1);
		Map[9][5].setName("stump1");
		Map[10][5].setIcon(stump1);
		Map[10][5].setName("stump1");
		Map[11][5].setIcon(stump1);
		Map[11][5].setName("stump1");
		Map[12][4].setIcon(stump2);
		Map[12][4].setName("stump2");
	        thread.stopped = true; 
                instructions.setText("Haha! You made it!!!");
	}
        for(int row=0;row<13;row++)
        {
            for(int col=0;col<9;col++)
            {
                
                
                
                
                
                
                Map[row][col].addActionListener(new ActionListener() 
                {
			public void actionPerformed(ActionEvent e)
			{
			        for(r=0;r<Row;r++)
				{
					for(c=0;c<Col;c++)
				        {
						//Creates integers that hold the co-ordinates of the last place that the user clicked.
						if(Map[r][c]==(JButton) e.getSource())
						{
							lastR=r;
							lastC=c;
						}
					}
				}
				//If the plank length is one
				               if(Map[lastR][lastC].getName()=="water1" && plank==1 
				                   && (((Map[lastR+1][lastC].getName()=="stumpman1" || Map[lastR+1][lastC].getName()=="stumpman2" || Map[lastR+1][lastC].getName()=="stumpman3") 
				                       && (Map[lastR-1][lastC].getName()=="stump1" || Map[lastR-1][lastC].getName()=="stump2" || Map[lastR-1][lastC].getName()=="stump3")) || ((Map[lastR+1][lastC].getName()=="stump1" || Map[lastR+1][lastC].getName()=="stump2" || Map[lastR+1][lastC].getName()=="stump3") 
				                           && (Map[lastR-1][lastC].getName()=="stumpman1" || Map[lastR-1][lastC].getName()=="stumpman2" || Map[lastR-1][lastC].getName()=="stumpman3"))))
						{
							Map[lastR][lastC].setIcon(plank2);
							Map[lastR][lastC].setName("plank2");
							show[1][1].setIcon(water1);
							plank=0; 
						}
						
						//Checks to see if the user clicked on a  single plank. If its true it sets the square of the plank to water and the user picks up the plank.
						else if(plank==0 && Map[lastR][lastC].getName()=="plank2" 
						    && (((Map[lastR-1][lastC].getName()=="stump1" || Map[lastR-1][lastC].getName()=="stump2" || Map[lastR-1][lastC].getName()=="stump3") 
						        && (Map[lastR+1][lastC].getName()=="stumpman1" || Map[lastR+1][lastC].getName()=="stumpman2" || Map[lastR+1][lastC].getName()=="stumpman3")) || ((Map[lastR+1][lastC].getName()=="stump1" || Map[lastR+1][lastC].getName()=="stump2" || Map[lastR+1][lastC].getName()=="stump3") 
						            && (Map[lastR-1][lastC].getName()=="stumpman1" || Map[lastR-1][lastC].getName()=="stumpman2" || Map[lastR-1][lastC].getName()=="stumpman3"))))
						{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								show[1][1].setIcon(plank2);
								plank=1;
						}
						
						//Horizontal plank 1
						if(Map[lastR][lastC].getName()=="water1" && plank==1 
						    && (((Map[lastR][lastC+1].getName()=="stumpman1" || Map[lastR][lastC+1].getName()=="stumpman2" || Map[lastR][lastC+1].getName()=="stumpman3") 
						        && (Map[lastR][lastC-1].getName()=="stump1" || Map[lastR][lastC-1].getName()=="stump2" || Map[lastR][lastC-1].getName()=="stump3")) || ((Map[lastR][lastC+1].getName()=="stump1" || Map[lastR][lastC+1].getName()=="stump2" || Map[lastR][lastC+1].getName()=="stump3") 
						            && (Map[lastR][lastC-1].getName()=="stumpman1" || Map[lastR][lastC-1].getName()=="stumpman2" || Map[lastR][lastC-1].getName()=="stumpman3"))))
						{
							Map[lastR][lastC].setIcon(plank1);
							Map[lastR][lastC].setName("plank1");
							show[1][1].setIcon(water1);
							plank=0; 
						}
						
						//Checks to see if the user clicked on a  single plank. If its true it sets the square of the plank to water and the user picks up the plank.
						else if(plank==0 && Map[lastR][lastC].getName()=="plank1" 
						    && (((Map[lastR][lastC-1].getName()=="stump1" || Map[lastR][lastC-1].getName()=="stump2" || Map[lastR][lastC-1].getName()=="stump3") 
						        && (Map[lastR][lastC+1].getName()=="stumpman1" || Map[lastR][lastC+1].getName()=="stumpman2" || Map[lastR+1][lastC].getName()=="stumpman3")) || ((Map[lastR][lastC+1].getName()=="stump1" || Map[lastR][lastC+1].getName()=="stump2" || Map[lastR][lastC+1].getName()=="stump3") 
						            && (Map[lastR][lastC-1].getName()=="stumpman1" || Map[lastR][lastC-1].getName()=="stumpman2" || Map[lastR][lastC-1].getName()=="stumpman3"))))
						{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
							        show[1][1].setIcon(plank1);
								plank=1;
						}

						if(Map[lastR][lastC].getName()=="plank2" && plank==0 && (Map[lastR+1][lastC].getName()=="plank2" || Map[lastR-1][lastC].getName()=="plank2"))
						{
							if(Map[lastR][lastC].getName()=="plank2" && Map[lastR-1][lastC].getName()!="plank2" 
							    && (((Map[lastR-1][lastC].getName()=="stumpman1" || Map[lastR-1][lastC].getName()=="stumpman2" || Map[lastR-1][lastC].getName()=="stumpman3") 
							        && (Map[lastR+2][lastC].getName()=="stump1" || Map[lastR+2][lastC].getName()=="stump2" || Map[lastR+2][lastC].getName()=="stump3")) || ((Map[lastR-1][lastC].getName()=="stump1" || Map[lastR-1][lastC].getName()=="stump2" || Map[lastR-1][lastC].getName()=="stump3") 
							            && (Map[lastR+2][lastC].getName()=="stumpman1" || Map[lastR+2][lastC].getName()=="stumpman2" || Map[lastR+2][lastC].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR+1][lastC].setIcon(water1);
								Map[lastR+1][lastC].setName("water1");
							        show[1][1].setIcon(plank2);
							        show[2][1].setIcon(plank2);
								plank=2;
							}
							
							else if(Map[lastR][lastC].getName()=="plank2" && Map[lastR+1][lastC].getName()!="plank2" 
							    && (((Map[lastR+1][lastC].getName()=="stumpman1" || Map[lastR+1][lastC].getName()=="stumpman2" || Map[lastR+1][lastC].getName()=="stumpman3") 
							        && (Map[lastR-2][lastC].getName()=="stump1" || Map[lastR-2][lastC].getName()=="stump2" || Map[lastR-2][lastC].getName()=="stump3")) || ((Map[lastR+1][lastC].getName()=="stump1" || Map[lastR+1][lastC].getName()=="stump2" || Map[lastR+1][lastC].getName()=="stump3") 
							            && (Map[lastR-2][lastC].getName()=="stumpman1" || Map[lastR-2][lastC].getName()=="stumpman2" || Map[lastR-2][lastC].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR-1][lastC].setIcon(water1);
								Map[lastR-1][lastC].setName("water1");
							        show[1][1].setIcon(plank2);
							        show[2][1].setIcon(plank2);
								plank=2;
							}
						}
						
						else if(plank==2 && Map[lastR][lastC].getName()=="water1")
						{
							if(Map[lastR-1][lastC].getName()=="water1" 
							    && ((Map[lastR+1][lastC].getName()=="stump1" || Map[lastR+1][lastC].getName()=="stump2" || Map[lastR+1][lastC].getName()=="stump3") 
							        && (Map[lastR-2][lastC].getName()=="stumpman1" || Map[lastR-2][lastC].getName()=="stumpman2" || Map[lastR-2][lastC].getName()=="stumpman3")) || ((Map[lastR-2][lastC].getName()=="stump1" || Map[lastR-2][lastC].getName()=="stump2" || Map[lastR-2][lastC].getName()=="stump3") 
							            && (Map[lastR+1][lastC].getName()=="stumpman1" || Map[lastR+1][lastC].getName()=="stumpman2" || Map[lastR+1][lastC].getName()=="stumpman3")))
							{
								Map[lastR][lastC].setIcon(plank2);
								Map[lastR][lastC].setName("plank2");
								Map[lastR-1][lastC].setIcon(plank2);
								Map[lastR-1][lastC].setName("plank2");
							        show[1][1].setIcon(water1);
							        show[1][2].setIcon(water1);
							        show[2][1].setIcon(water1);
								plank=0;
							}
							
							else if(Map[lastR+1][lastC].getName()=="water1" 
							    && ((Map[lastR-1][lastC].getName()=="stump1" || Map[lastR-1][lastC].getName()=="stump2" ||  Map[lastR-1][lastC].getName()=="stump3") 
							        && (Map[lastR+2][lastC].getName()=="stumpman1" || Map[lastR+2][lastC].getName()=="stumpman2" || Map[lastR+2][lastC].getName()=="stumpman3")) || ((Map[lastR+2][lastC].getName()=="stump1" || Map[lastR+2][lastC].getName()=="stump2" || Map[lastR+2][lastC].getName()=="stump3") 
							             && (Map[lastR-1][lastC].getName()=="stumpman1" || Map[lastR-1][lastC].getName()=="stumpman2" || Map[lastR-1][lastC].getName()=="stumpman3")))
							{
								Map[lastR][lastC].setIcon(plank2);
								Map[lastR][lastC].setName("plank2");
								Map[lastR+1][lastC].setIcon(plank2);
								Map[lastR+1][lastC].setName("plank2");
							        show[1][1].setIcon(water1);
							        show[1][2].setIcon(water1);
							        show[2][1].setIcon(water1);
								plank=0;
							}
						}
						
						//Horizontal 2 length planks
						if(Map[lastR][lastC].getName()=="plank1" && plank==0 && (Map[lastR][lastC+1].getName()=="plank1" || Map[lastR][lastC-1].getName()=="plank1"))
						{
							if(Map[lastR][lastC].getName()=="plank1" && Map[lastR][lastC-1].getName()!="plank1" 
							    && (((Map[lastR][lastC-1].getName()=="stumpman1" || Map[lastR][lastC-1].getName()=="stumpman2" || Map[lastR][lastC-1].getName()=="stumpman3") 
							        && (Map[lastR][lastC+2].getName()=="stump1" || Map[lastR][lastC+2].getName()=="stump2" || Map[lastR][lastC+2].getName()=="stump3")) || ((Map[lastR][lastC-1].getName()=="stump1" || Map[lastR][lastC-1].getName()=="stump2" || Map[lastR][lastC-1].getName()=="stump3") 
							            && (Map[lastR][lastC+2].getName()=="stumpman1" || Map[lastR][lastC+2].getName()=="stumpman2" || Map[lastR][lastC+2].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR][lastC+1].setIcon(water1);
								Map[lastR][lastC+1].setName("water1");
							        show[1][1].setIcon(plank1);
							        show[1][2].setIcon(plank1);
								plank=2;
							}
							
							else if(Map[lastR][lastC].getName()=="plank1" && Map[lastR][lastC+1].getName()!="plank1" 
							    && (((Map[lastR][lastC+1].getName()=="stumpman1" || Map[lastR][lastC+1].getName()=="stumpman2" || Map[lastR][lastC+1].getName()=="stumpman3") 
							        && (Map[lastR][lastC-2].getName()=="stump1" || Map[lastR][lastC-2].getName()=="stump2" || Map[lastR][lastC-2].getName()=="stump3")) || ((Map[lastR][lastC+1].getName()=="stump1" || Map[lastR][lastC+1].getName()=="stump2" || Map[lastR][lastC+1].getName()=="stump3") 
							            && (Map[lastR][lastC-2].getName()=="stumpman1" || Map[lastR][lastC-2].getName()=="stumpman2" || Map[lastR][lastC-2].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR][lastC-1].setIcon(water1);
								Map[lastR][lastC-1].setName("water1");
							        show[1][1].setIcon(plank1);
							        show[1][2].setIcon(plank1);
								plank=2;
							}
						}
						
						else if(plank==2 && Map[lastR][lastC].getName()=="water1")
						{
							if(Map[lastR][lastC-1].getName()=="water1" 
							    && ((Map[lastR][lastC+1].getName()=="stump1" || Map[lastR][lastC+1].getName()=="stump2" || Map[lastR][lastC+1].getName()=="stump3") 
							        && (Map[lastR][lastC-2].getName()=="stumpman1" || Map[lastR][lastC-2].getName()=="stumpman2" || Map[lastR][lastC-2].getName()=="stumpman3")) || ((Map[lastR][lastC-2].getName()=="stump1" || Map[lastR][lastC-2].getName()=="stump2" || Map[lastR][lastC-2].getName()=="stump3") 
							            && (Map[lastR][lastC+1].getName()=="stumpman1" || Map[lastR][lastC+1].getName()=="stumpman2" || Map[lastR][lastC+1].getName()=="stumpman3")))
							{
								Map[lastR][lastC].setIcon(plank1);
								Map[lastR][lastC].setName("plank1");
								Map[lastR][lastC-1].setIcon(plank1);
								Map[lastR][lastC-1].setName("plank1");
							        show[1][1].setIcon(water1);
							        show[1][2].setIcon(water1);
							        show[2][1].setIcon(water1);
								plank=0;
							}
							
							else if(Map[lastR][lastC+1].getName()=="water1" 
							    && ((Map[lastR][lastC-1].getName()=="stump1" || Map[lastR][lastC-1].getName()=="stump2" ||  Map[lastR][lastC-1].getName()=="stump3") 
							        && (Map[lastR][lastC+2].getName()=="stumpman1" || Map[lastR][lastC+2].getName()=="stumpman2" || Map[lastR][lastC+2].getName()=="stumpman3")) || ((Map[lastR][lastC+2].getName()=="stump1" || Map[lastR][lastC+2].getName()=="stump2" || Map[lastR][lastC+2].getName()=="stump3") 
							            && (Map[lastR][lastC-1].getName()=="stumpman1" || Map[lastR][lastC-1].getName()=="stumpman2" || Map[lastR][lastC-1].getName()=="stumpman3")))
							{
								Map[lastR][lastC].setIcon(plank1);
								Map[lastR][lastC].setName("plank1");
								Map[lastR][lastC+1].setIcon(plank1);
								Map[lastR][lastC+1].setName("plank1");
							        show[1][1].setIcon(water1);
							        show[1][2].setIcon(water1);
							        show[2][1].setIcon(water1);
								plank=0;
							}
						}
						
						if(Map[lastR][lastC].getName()=="plank2" && plank==0)	
						{
							if(Map[lastR][lastC].getName()=="plank2" && Map[lastR+1][lastC].getName()=="plank2" && Map[lastR+2][lastC].getName()=="plank2" 
							    && (((Map[lastR-1][lastC].getName()=="stump1" || Map[lastR-1][lastC].getName()=="stump2" || Map[lastR-1][lastC].getName()=="stump3") 
							        && (Map[lastR+3][lastC].getName()=="stumpman1" || Map[lastR+3][lastC].getName()=="stumpman2" || Map[lastR+3][lastC].getName()=="stumpman3") || (Map[lastR+3][lastC].getName()=="stump1" || Map[lastR+3][lastC].getName()=="stump2" || Map[lastR+3][lastC].getName()=="stump3") 
							            && (Map[lastR-1][lastC].getName()=="stumpman1" || Map[lastR-1][lastC].getName()=="stumpman2" || Map[lastR-1][lastC].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR+1][lastC].setIcon(water1);
								Map[lastR+1][lastC].setName("water1");
								Map[lastR+2][lastC].setIcon(water1);
								Map[lastR+2][lastC].setName("water1");
							        show[0][1].setIcon(plank2);
							        show[1][1].setIcon(plank2);
							        show[2][1].setIcon(plank2);
								plank=3;
							}
							
							else if(Map[lastR][lastC].getName()=="plank2" && Map[lastR-1][lastC].getName()=="plank2" && Map[lastR+1][lastC].getName()=="plank2" 
							    && (((Map[lastR-2][lastC].getName()=="stump1" || Map[lastR-2][lastC].getName()=="stump2" || Map[lastR-2][lastC].getName()=="stump3") 
							        && (Map[lastR+2][lastC].getName()=="stumpman1" || Map[lastR+2][lastC].getName()=="stumpman2" || Map[lastR+2][lastC].getName()=="stumpman3") || (Map[lastR+2][lastC].getName()=="stump1" || Map[lastR+2][lastC].getName()=="stump2" || Map[lastR+2][lastC].getName()=="stump3") 
							            && (Map[lastR-2][lastC].getName()=="stumpman1" || Map[lastR-2][lastC].getName()=="stumpman2" || Map[lastR-2][lastC].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR+1][lastC].setIcon(water1);
								Map[lastR+1][lastC].setName("water1");
								Map[lastR-1][lastC].setIcon(water1);
								Map[lastR-1][lastC].setName("water1");
							        show[0][1].setIcon(plank2);
							        show[1][1].setIcon(plank2);
							        show[2][1].setIcon(plank2);
								plank=3;
							}
							
							else if(Map[lastR][lastC].getName()=="plank2" && Map[lastR-1][lastC].getName()=="plank2" && Map[lastR-2][lastC].getName()=="plank2" 
							    && ((Map[lastR+1][lastC].getName()=="stumpman1" || Map[lastR+1][lastC].getName()=="stumpman2" || Map[lastR+1][lastC].getName()=="stumpman3") 
							        && (Map[lastR-3][lastC].getName()=="stump1" || Map[lastR-3][lastC].getName()=="stump2" || Map[lastR-3][lastC].getName()=="stump3")) || ((Map[lastR+1][lastC].getName()=="stump1" || Map[lastR+1][lastC].getName()=="stump2" || Map[lastR+1][lastC].getName()=="stump3") 
							            && (Map[lastR-3][lastC].getName()=="stumpman1" || Map[lastR-3][lastC].getName()=="stumpman2" || Map[lastR-3][lastC].getName()=="stumpman3")))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR-1][lastC].setIcon(water1);
								Map[lastR-1][lastC].setName("water1");
								Map[lastR-2][lastC].setIcon(water1);
								Map[lastR-2][lastC].setName("water1");
							        show[0][1].setIcon(plank2);
							        show[1][1].setIcon(plank2);
							        show[2][1].setIcon(plank2);
								plank=3;
							}
						}
							
						else if(plank==3 && Map[lastR][lastC].getName()=="water1")
						{
							if(plank==3 && (Map[lastR+1][lastC].getName()=="water1" && Map[lastR+2][lastC].getName()=="water1") 
							    && (((Map[lastR-1][lastC].getName()=="stump1" || Map[lastR-1][lastC].getName()=="stump2" || Map[lastR-1][lastC].getName()=="stump3") 
							        && (Map[lastR+3][lastC].getName()=="stumpman1" || Map[lastR+3][lastC].getName()=="stumpman2" || Map[lastR+3][lastC].getName()=="stumpman3") || (Map[lastR+3][lastC].getName()=="stump1" || Map[lastR+3][lastC].getName()=="stump2" || Map[lastR+3][lastC].getName()=="stump3") 
							             && (Map[lastR-1][lastC].getName()=="stumpman1" || Map[lastR-1][lastC].getName()=="stumpman2" || Map[lastR-1][lastC].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(plank2);
								Map[lastR][lastC].setName("plank2");
								Map[lastR+1][lastC].setIcon(plank2);
								Map[lastR+1][lastC].setName("plank2");
								Map[lastR+2][lastC].setIcon(plank2);
								Map[lastR+2][lastC].setName("plank2");
							        show[0][1].setIcon(water1);
							        show[1][1].setIcon(water1);
							        show[2][1].setIcon(water1);
							        show[1][0].setIcon(water1);
							        show[1][2].setIcon(water1);
								plank=0;
							}
							
							else if(plank==3 && (Map[lastR-1][lastC].getName()=="water1" && Map[lastR-2][lastC].getName()=="water1") 
							    && (((Map[lastR+1][lastC].getName()=="stump1" || Map[lastR+1][lastC].getName()=="stump2" || Map[lastR+1][lastC].getName()=="stump3") 
							        && (Map[lastR-3][lastC].getName()=="stumpman1" || Map[lastR-3][lastC].getName()=="stumpman2" || Map[lastR-3][lastC].getName()=="stumpman3")) || ((Map[lastR-3][lastC].getName()=="stump1" || Map[lastR-3][lastC].getName()=="stump2" || Map[lastR-3][lastC].getName()=="stump3") 
							            && (Map[lastR+1][lastC].getName()=="stumpman1"  || Map[lastR+1][lastC].getName()=="stumpman2" || Map[lastR+1][lastC].getName()=="stumpman2"))))
							{
								Map[lastR][lastC].setIcon(plank2);
								Map[lastR][lastC].setName("plank2");
								Map[lastR-1][lastC].setIcon(plank2);
								Map[lastR-1][lastC].setName("plank2");
								Map[lastR-2][lastC].setIcon(plank2);
								Map[lastR-2][lastC].setName("plank2");
							        show[0][1].setIcon(water1);
							        show[1][1].setIcon(water1);
							        show[2][1].setIcon(water1);
							        show[1][0].setIcon(water1);
							        show[1][2].setIcon(water1);
								plank=0;
							}
							
							else if(plank==3 && (Map[lastR-1][lastC].getName()=="water1" && Map[lastR+1][lastC].getName()=="water1") 
							    && (((Map[lastR-2][lastC].getName()=="stump1" || Map[lastR-2][lastC].getName()=="stump2" || Map[lastR-2][lastC].getName()=="stump3") 
							        && (Map[lastR+2][lastC].getName()=="stumpman1" || Map[lastR+2][lastC].getName()=="stumpman2" || Map[lastR+2][lastC].getName()=="stumpman3")) || ((Map[lastR-2][lastC].getName()=="stumpman1" || Map[lastR-2][lastC].getName()=="stumpman2" || Map[lastR-2][lastC].getName()=="stumpman3") 
							            && (Map[lastR+2][lastC].getName()=="stump1" || Map[lastR+2][lastC].getName()=="stump2" || Map[lastR+2][lastC].getName()=="stump3"))))
							{
								Map[lastR][lastC].setIcon(plank2);
								Map[lastR][lastC].setName("plank2");
								Map[lastR+1][lastC].setIcon(plank2);
								Map[lastR+1][lastC].setName("plank2");
								Map[lastR-1][lastC].setIcon(plank2);
								Map[lastR-1][lastC].setName("plank2");
							        show[0][1].setIcon(water1);
							        show[1][1].setIcon(water1);
							        show[2][1].setIcon(water1);
							        show[1][0].setIcon(water1);
							        show[1][2].setIcon(water1);
								plank=0;
							}
						}
						
						//horizontal 3 length
						if(Map[lastR][lastC].getName()=="plank1" && plank==0)	
						{
							if(Map[lastR][lastC].getName()=="plank1" && Map[lastR][lastC+1].getName()=="plank1" && Map[lastR][lastC+2].getName()=="plank1" 
							    && (((Map[lastR][lastC-1].getName()=="stump1" || Map[lastR][lastC-1].getName()=="stump2" || Map[lastR][lastC-1].getName()=="stump3") 
							        && (Map[lastR][lastC+3].getName()=="stumpman1" || Map[lastR][lastC+3].getName()=="stumpman2" || Map[lastR][lastC+3].getName()=="stumpman3") || (Map[lastR][lastC+3].getName()=="stump1" || Map[lastR][lastC+3].getName()=="stump2" || Map[lastR][lastC+3].getName()=="stump3") 
							            && (Map[lastR][lastC-1].getName()=="stumpman1" || Map[lastR][lastC-1].getName()=="stumpman2" || Map[lastR][lastC-1].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR][lastC+1].setIcon(water1);
								Map[lastR][lastC+1].setName("water1");
								Map[lastR][lastC+2].setIcon(water1);
								Map[lastR][lastC+2].setName("water1");
							        show[1][0].setIcon(plank1);
							        show[1][1].setIcon(plank1);
							        show[1][2].setIcon(plank1);
								plank=3;
							}
							
							else if(Map[lastR][lastC].getName()=="plank1" && Map[lastR][lastC-1].getName()=="plank1" && Map[lastR][lastC+1].getName()=="plank1" 
							    && (((Map[lastR][lastC-2].getName()=="stump1" || Map[lastR][lastC-2].getName()=="stump2" || Map[lastR][lastC-2].getName()=="stump3") 
							        && (Map[lastR][lastC+2].getName()=="stumpman1" || Map[lastR][lastC+2].getName()=="stumpman2" || Map[lastR][lastC+2].getName()=="stumpman3") || (Map[lastR][lastC+2].getName()=="stump1" || Map[lastR][lastC+2].getName()=="stump2" || Map[lastR][lastC+2].getName()=="stump3") 
							            && (Map[lastR][lastC-2].getName()=="stumpman1" || Map[lastR][lastC-2].getName()=="stumpman2" || Map[lastR][lastC-2].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR][lastC+1].setIcon(water1);
								Map[lastR][lastC+1].setName("water1");
								Map[lastR][lastC-1].setIcon(water1);
								Map[lastR][lastC-1].setName("water1");
							        show[1][0].setIcon(plank1);
							        show[1][1].setIcon(plank1);
							        show[1][2].setIcon(plank1);
								plank=3;
							}
							
							else if(Map[lastR][lastC].getName()=="plank1" && Map[lastR][lastC-1].getName()=="plank1" && Map[lastR][lastC-2].getName()=="plank1" 
							    && ((Map[lastR][lastC+1].getName()=="stumpman1" || Map[lastR][lastC+1].getName()=="stumpman2" || Map[lastR][lastC+1].getName()=="stumpman3") 
							        && (Map[lastR][lastC-3].getName()=="stump1" || Map[lastR][lastC-3].getName()=="stump2" || Map[lastR][lastC-3].getName()=="stump3")) || ((Map[lastR][lastC+1].getName()=="stump1" || Map[lastR][lastC+1].getName()=="stump2" || Map[lastR][lastC+1].getName()=="stump3") 
							             && (Map[lastR][lastC-3].getName()=="stumpman1" || Map[lastR][lastC-3].getName()=="stumpman2" || Map[lastR][lastC-3].getName()=="stumpman3")))
							{
								Map[lastR][lastC].setIcon(water1);
								Map[lastR][lastC].setName("water1");
								Map[lastR][lastC-1].setIcon(water1);
								Map[lastR][lastC-1].setName("water1");
								Map[lastR][lastC-2].setIcon(water1);
								Map[lastR][lastC-2].setName("water1");
							        show[1][0].setIcon(plank1);
							        show[1][1].setIcon(plank1);
							        show[1][2].setIcon(plank1);
								plank=3;
							}
						}
							
						else if(plank==3 && Map[lastR][lastC].getName()=="water1")
						{
							if(plank==3 && (Map[lastR][lastC+1].getName()=="water1" && Map[lastR][lastC+2].getName()=="water1") 
							    && (((Map[lastR][lastC-1].getName()=="stump1" || Map[lastR][lastC-1].getName()=="stump2" || Map[lastR][lastC-1].getName()=="stump3") 
							        && (Map[lastR][lastC+3].getName()=="stumpman1" || Map[lastR][lastC+3].getName()=="stumpman2" || Map[lastR][lastC+3].getName()=="stumpman3") || (Map[lastR][lastC+3].getName()=="stump1" || Map[lastR][lastC+3].getName()=="stump2" || Map[lastR][lastC+3].getName()=="stump3") 
							            && (Map[lastR][lastC-1].getName()=="stumpman1" || Map[lastR][lastC-1].getName()=="stumpman2" || Map[lastR][lastC-1].getName()=="stumpman3"))))
							{
								Map[lastR][lastC].setIcon(plank1);
								Map[lastR][lastC].setName("plank1");
								Map[lastR][lastC+1].setIcon(plank1);
								Map[lastR][lastC+1].setName("plank1");
								Map[lastR][lastC+2].setIcon(plank1);
								Map[lastR][lastC+2].setName("plank1");
							        show[1][0].setIcon(water1);
							        show[1][1].setIcon(water1);
							        show[1][2].setIcon(water1);
							        show[0][1].setIcon(water1);
							        show[2][1].setIcon(water1);
								plank=0;
							}
							
							else if(plank==3 && (Map[lastR][lastC-1].getName()=="water1" && Map[lastR][lastC-2].getName()=="water1") 
							    && (((Map[lastR][lastC+1].getName()=="stump1" || Map[lastR][lastC+1].getName()=="stump2" || Map[lastR][lastC+1].getName()=="stump3") 
							        && (Map[lastR][lastC-3].getName()=="stumpman1" || Map[lastR][lastC-3].getName()=="stumpman2" || Map[lastR][lastC-3].getName()=="stumpman3")) || ((Map[lastR][lastC-3].getName()=="stump1" || Map[lastR][lastC-3].getName()=="stump2" || Map[lastR][lastC-3].getName()=="stump3") 
							            && (Map[lastR][lastC+1].getName()=="stumpman1"  || Map[lastR][lastC+1].getName()=="stumpman2" || Map[lastR][lastC+1].getName()=="stumpman2"))))
							{
								Map[lastR][lastC].setIcon(plank1);
								Map[lastR][lastC].setName("plank1");
								Map[lastR][lastC-1].setIcon(plank1);
								Map[lastR][lastC-1].setName("plank1");
								Map[lastR][lastC-2].setIcon(plank1);
								Map[lastR][lastC-2].setName("plank1");
							        show[1][0].setIcon(water1);
							        show[1][1].setIcon(water1);
							        show[1][2].setIcon(water1);
							        show[0][1].setIcon(water1);
							        show[2][1].setIcon(water1);
								plank=0;
							}
							
							else if(plank==3 && (Map[lastR][lastC-1].getName()=="water1" && Map[lastR][lastC+1].getName()=="water1") 
							    && (((Map[lastR][lastC-2].getName()=="stump1" || Map[lastR][lastC-2].getName()=="stump2" || Map[lastR][lastC-2].getName()=="stump3") 
							        && (Map[lastR][lastC+2].getName()=="stumpman1" || Map[lastR][lastC+2].getName()=="stumpman2" || Map[lastR+2][lastC].getName()=="stumpman3")) || ((Map[lastR][lastC-2].getName()=="stumpman1" || Map[lastR][lastC-2].getName()=="stumpman2" || Map[lastR][lastC-2].getName()=="stumpman3") 
							            && (Map[lastR][lastC+2].getName()=="stump1" || Map[lastR][lastC+2].getName()=="stump2" || Map[lastR][lastC+2].getName()=="stump3"))))
							{
								Map[lastR][lastC].setIcon(plank1);
								Map[lastR][lastC].setName("plank1");
								Map[lastR][lastC+1].setIcon(plank1);
								Map[lastR][lastC+1].setName("plank1");
								Map[lastR][lastC-1].setIcon(plank1);
								Map[lastR][lastC-1].setName("plank1");
							        show[1][0].setIcon(water1);
							        show[1][1].setIcon(water1);
							        show[1][2].setIcon(water1);
							        show[0][1].setIcon(water1);
							        show[2][1].setIcon(water1);
								plank=0;
							}
						}
						
				
				
                       }
			
		});
            
                Map[row][col].addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent a)
					{
						
						if(SwingUtilities.isRightMouseButton(a))
						{				
							for(r=0;r<Row;r++)
							{
								for(c=0;c<Col;c++)
								{
									//Creates integers that hold the co-ordinates of the last place that the user clicked.
									if(Map[r][c]==(JButton) a.getSource())
									{
										lastR=r;
										lastC=c;
									}
									
								}
							}
							
							
							if(Map[0][4].getName()=="stumpman3"||Map[0][2].getName()=="stumpman3")
							{
								programStart = System.currentTimeMillis();
                                                                if(thisScore>highScore[lvl-1]&&lvl<5)
                                                                {
                                                                    highScore[lvl-1]=thisScore;
                                                                }
                                                                try
                                                                {
                                                                    BufferedWriter out = new BufferedWriter(new FileWriter("/Users/tony/Documents/course work/The Perilous Plank Puzzle Conundrum/highscore.txt"));
                                                                    for(int i=0;i<4;i++)
                                                                    {
                                                                        out.write(Integer.toString(highScore[i]));
                                                                        out.write("\r\n");
                                                                    }
                                                                    out.close();
                                                                }
                                                                catch(FileNotFoundException f)
                                                                {
                                                                    f.printStackTrace();
                                                                }
                                                                catch(IOException f)
                                                                {
                                                                    f.printStackTrace();
                                                                }
								lvl++;
                                                                restart();
							}
							//Check if is horizontal
							else if(lastR==manR&&lastC!=manC)
							{
							    //If man is left to the plank
							    if(lastC>manC)
							        for(int j=manC; Map[lastR][j+1].getName()!="water1" && j+1<9 && j!=lastC ; j++) 
							        {
							            
							            if(Map[lastR][j].getName()=="stumpman1" && Map[lastR][j+1].getName()=="plank1")
								  {
									Map[lastR][j+1].setIcon(plankman1);
									Map[lastR][j+1].setName("plankman1");
									Map[lastR][j].setIcon(stump1);
									Map[lastR][j].setName("stump1");
									manC++;
								  }  

								  if(Map[lastR][j].getName()=="plankman1" && Map[lastR][j+1].getName()=="plank1")
								  {
									Map[lastR][j+1].setIcon(plankman1);
									Map[lastR][j+1].setName("plankman1");
									Map[lastR][j].setIcon(plank1);
									Map[lastR][j].setName("plank1");
									manC++;
								  }   
								
								  if(Map[lastR][j].getName()=="plankman1" && Map[lastR][j+1].getName()=="stump1")
								  {
									Map[lastR][j+1].setIcon(stumpman1);
									Map[lastR][j+1].setName("stumpman1");
									Map[lastR][j].setIcon(plank1);
									Map[lastR][j].setName("plank1");
									manC++;
								  }
								
							        }
							        //If man is right tothe plank
							        else if(manC>lastC)
							        {
							            for(int j=manC; Map[lastR][j-1].getName()!="water1" && j-1>-1 && j!=lastC ; j--) 
							        {
							            
							            if(Map[lastR][j].getName()=="stumpman1" && Map[lastR][j-1].getName()=="plank1")
								  {
									Map[lastR][j-1].setIcon(plankman1);
									Map[lastR][j-1].setName("plankman1");
									Map[lastR][j].setIcon(stump1);
									Map[lastR][j].setName("stump1");
									manC--;
								  }

								  if(Map[lastR][j].getName()=="plankman1" && Map[lastR][j-1].getName()=="plank1")
								  {
									Map[lastR][j-1].setIcon(plankman1);
									Map[lastR][j-1].setName("plankman1");
									Map[lastR][j].setIcon(plank1);
									Map[lastR][j].setName("plank1");
									manC--;
								  }
								
								  if(Map[lastR][j].getName()=="plankman1" && Map[lastR][j-1].getName()=="stump1")
								  {
									Map[lastR][j-1].setIcon(stumpman1);
									Map[lastR][j-1].setName("stumpman1");
									Map[lastR][j].setIcon(plank1);
									Map[lastR][j].setName("plank1");
									manC--;
							  	  }
								
							        }
							        }
							}
							//Check if is vertical
							else if(lastC==manC && lastR!=manR)
							{
							    //If man is under the plank
							    if(manR>lastR)
							    {
							        for(int j=manR; Map[j-1][manC].getName()!="water1" && j-1>-1 && j!=lastR; j--) 
							        {
							            if(Map[j][lastC].getName()=="stumpman2" && Map[j-1][lastC].getName()=="plank2")
								    {
									Map[j-1][lastC].setIcon(plankman2);
									Map[j-1][lastC].setName("plankman2");
									Map[j][lastC].setIcon(stump2);
									Map[j][lastC].setName("stump2");
									manR--;
								    }
								    
								    if(Map[j][lastC].getName()=="stumpman1" && Map[j-1][lastC].getName()=="plank2")
								    {
									Map[j-1][lastC].setIcon(plankman2);
									Map[j-1][lastC].setName("plankman2");
									Map[j][lastC].setIcon(stump1);
									Map[j][lastC].setName("stump1");
									manR--;
								    }
								    
								    if(Map[j][lastC].getName()=="plankman2" && Map[j-1][lastC].getName()=="plank2")
								    {
									Map[j-1][lastC].setIcon(plankman2);
									Map[j-1][lastC].setName("plankman2");
									Map[j][lastC].setIcon(plank2);
									Map[j][lastC].setName("plank2");
									manR--;
								    }
								    
								    if(Map[j][lastC].getName()=="plankman2" && Map[j-1][lastC].getName()=="stump1")
								    {
									Map[j-1][lastC].setIcon(stumpman1);
									Map[j-1][lastC].setName("stumpman1");
									Map[j][lastC].setIcon(plank2);
									Map[j][lastC].setName("plank2");
									manR--;
								    }
								    
								    if(Map[j][lastC].getName()=="plankman2" && Map[j-1][lastC].getName()=="stump3")
								    {
									Map[j-1][lastC].setIcon(stumpman3);
									Map[j-1][lastC].setName("stumpman3");
									Map[j][lastC].setIcon(plank2);
									Map[j][lastC].setName("plank2");
									manR--;
								    }
							        }
							    }
							    //If man is above the plank
							    else if(manR<lastR)
							    {
							        for(int j=manR; Map[j+1][manC].getName()!="water1" && j+1<13 && j!=lastR; j++) 
							        {
							            if(Map[j][lastC].getName()=="stumpman1" && Map[j+1][lastC].getName()=="plank2")
								    {
									Map[j+1][lastC].setIcon(plankman2);
									Map[j+1][lastC].setName("plankman2");
									Map[j][lastC].setIcon(stump1);
									Map[j][lastC].setName("stump1");
									manR++;
								    }
								    
								    if(Map[j][lastC].getName()=="plankman2" && Map[j+1][lastC].getName()=="plank2")
								    {
									Map[j+1][lastC].setIcon(plankman2);
									Map[j+1][lastC].setName("plankman2");
									Map[j][lastC].setIcon(plank2);
									Map[j][lastC].setName("plank2");
									manR++;
								    }
								    
								    if(Map[j][lastC].getName()=="plankman2" && Map[j+1][lastC].getName()=="stump1")
								    {
									Map[j+1][lastC].setIcon(stumpman1);
									Map[j+1][lastC].setName("stumpman1");
									Map[j][lastC].setIcon(plank2);
									Map[j][lastC].setName("plank2");
									manR++;
								    }
							        }
							    }
							}
						}
					}
				});
			}
		}
                 
	
	
	
        
        window.setTitle("The Perilous Plank Puzzle Conundrum");
	window.setSize(aWidth, aHeight);
	window.setResizable(false);
	window.setVisible(true);
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void restart()
	{
	    map.removeAll();
	    
         programStart = System.currentTimeMillis();
         pauseStart = programStart;   
         pauseCount = 0; 
         thisScore=0;
	    
	    
        
        ImageIcon bank1 = new ImageIcon("bank1.jpg");
        ImageIcon bank2 = new ImageIcon("bank2.jpg");
        ImageIcon water1 = new ImageIcon("water1.jpg");
        ImageIcon plankman1 = new ImageIcon("plank1_man.jpg");
        ImageIcon plank1 = new ImageIcon("plank1.jpg");
        ImageIcon plankman2 = new ImageIcon("plank2_man.jpg");
        ImageIcon plank2 = new ImageIcon("plank2.jpg");
        ImageIcon stumpman1 = new ImageIcon("stump1_man.jpg");
        ImageIcon stump1 = new ImageIcon("stump1.jpg");
        ImageIcon stumpman2 = new ImageIcon("stump2_man.jpg");
        ImageIcon stump2 = new ImageIcon("stump2.jpg");
        ImageIcon stumpman3 = new ImageIcon("stump3_man.jpg");
        ImageIcon stump3 = new ImageIcon("stump3.jpg");
        ImageIcon water2 = new ImageIcon("water2.jpg");
        ImageIcon water3 = new ImageIcon("water3.jpg");
        ImageIcon water4 = new ImageIcon("water4.jpg");
        
        
	
	
	
	 
	
	for(int row=0;row<13;row++)
        {
            for(int col=0;col<9;col++)
            {
                if(row==0)
                {
                    Map[row][col].setIcon(bank2);
                    Map[row][col].setName("bank2");
                }
                else if(row==12)
                {
                    Map[row][col].setIcon(bank1);
                    Map[row][col].setName("bank1");
                }
                else
                {
                    Map[row][col].setIcon(water1);
                    Map[row][col].setName("water1");
                }
                Map[row][col].setBorderPainted(false);
                map.add(Map[row][col]);
            }
        }
        if(lvl==1)
        {
                Map[12][4].setIcon(stumpman2);
		Map[12][4].setName("stumpman2");
		Map[11][4].setIcon(plank2);
		Map[11][4].setName("plank2");
		Map[10][4].setIcon(plank2);
		Map[10][4].setName("plank2");
		Map[9][4].setIcon(plank2);
		Map[9][4].setName("plank2");
		Map[8][4].setIcon(stump1);
		Map[8][4].setName("stump1");
		Map[4][4].setIcon(stump1);
		Map[4][4].setName("stump1");
		Map[0][4].setIcon(stump3);
		Map[0][4].setName("stump3");
		manR=12;
		manC=4;
        }
        if(lvl==2)
        {
		Map[12][4].setIcon(stumpman2);
		Map[12][4].setName("stumpman2");
		Map[11][4].setIcon(plank2);
		Map[11][4].setName("plank2");
		Map[10][4].setIcon(plank2);
		Map[10][4].setName("plank2");
		Map[9][4].setIcon(plank2);
		Map[9][4].setName("plank2");
		Map[8][4].setIcon(stump1);
		Map[8][4].setName("stump1");
		Map[10][0].setIcon(stump1);
		Map[10][0].setName("stump1");
		Map[9][0].setIcon(plank2);
		Map[9][0].setName("plank2");
		Map[8][0].setIcon(stump1);
		Map[8][0].setName("stump1");
		Map[6][0].setIcon(stump1);
		Map[6][0].setName("stump1");
		Map[6][2].setIcon(stump1);
		Map[6][2].setName("stump1");
		Map[6][3].setIcon(plank1);
		Map[6][3].setName("plank1");
		Map[6][4].setIcon(plank1);
		Map[6][4].setName("plank1");
		Map[6][5].setIcon(plank1);
		Map[6][5].setName("plank1");
		Map[6][6].setIcon(stump1);
		Map[6][6].setName("stump1");
		Map[4][4].setIcon(stump1);
		Map[4][4].setName("stump1");
		Map[2][0].setIcon(stump1);
		Map[2][0].setName("stump1");
		Map[0][4].setIcon(stump3);
		Map[0][4].setName("stump3");
		manR=12;
		manC=4;
	}	
	else if(lvl==3)
	{
	        Map[12][2].setIcon(stumpman2);
	        Map[12][2].setName("stumpman2");
	        Map[11][2].setIcon(plank2);
		Map[11][2].setName("plank2");
		Map[10][2].setIcon(stump1);
		Map[10][2].setName("stump1");
		Map[10][0].setIcon(stump1);
		Map[10][0].setName("stump1");
		Map[10][6].setIcon(stump1);
		Map[10][6].setName("stump1");
		Map[10][8].setIcon(stump1);
		Map[10][8].setName("stump1");
		Map[9][0].setIcon(plank2);
		Map[9][0].setName("plank2");
		Map[9][8].setIcon(plank2);
		Map[9][8].setName("plank2");
		Map[8][0].setIcon(plank2);
		Map[8][0].setName("plank2");
		Map[8][8].setIcon(plank2);
		Map[8][8].setName("plank2");
		Map[8][6].setIcon(stump1);
		Map[8][6].setName("stump1");
		Map[7][0].setIcon(plank2);
		Map[7][0].setName("plank2");
		Map[7][8].setIcon(plank2);
		Map[7][8].setName("plank2");
		Map[7][6].setIcon(plank2);
		Map[7][6].setName("plank2");
		Map[6][0].setIcon(stump1);
		Map[6][0].setName("stump1");
		Map[6][4].setIcon(stump1);
		Map[6][4].setName("stump1");
		Map[6][8].setIcon(stump1);
		Map[6][8].setName("stump1");
		Map[6][6].setIcon(stump1);
		Map[6][6].setName("stump1");
		Map[5][6].setIcon(plank2);
		Map[5][6].setName("plank2");
		Map[4][6].setIcon(plank2);
		Map[4][6].setName("plank2");
		Map[4][2].setIcon(stump1);
		Map[4][2].setName("stump1");
		Map[4][8].setIcon(stump1);
		Map[4][8].setName("stump1");
		Map[3][6].setIcon(plank2);
		Map[3][6].setName("plank2");
		Map[2][0].setIcon(stump1);
		Map[2][0].setName("stump1");
		Map[2][6].setIcon(stump1);
		Map[2][6].setName("stump1");
		Map[2][8].setIcon(stump1);
		Map[2][8].setName("stump3");
		Map[0][2].setIcon(stump3);
		Map[0][2].setName("stump3");
		Map[8][2].setIcon(stump1);
		Map[8][2].setName("stump1");
		manR=12;
		manC=2;
	}
	if(lvl==4)
	{
	        Map[12][1].setIcon(stumpman2);
	        Map[12][1].setName("stumpman2");
	        Map[11][1].setIcon(plank2);
	        Map[11][1].setName("plank2");
	        Map[10][1].setIcon(plank2);
	        Map[10][1].setName("plank2");
		Map[9][1].setIcon(stump1);
		Map[9][1].setName("stump1");
	        Map[8][1].setIcon(plank2);
	        Map[8][1].setName("plank2");
	        Map[7][1].setIcon(plank2);
	        Map[7][1].setName("plank2");
	        Map[6][1].setIcon(plank2);
	        Map[6][1].setName("plank2");
		Map[5][1].setIcon(stump1);
		Map[5][1].setName("stump1");
	        Map[4][1].setIcon(plank2);
	        Map[4][1].setName("plank2");
	        Map[3][1].setIcon(plank2);
	        Map[3][1].setName("plank2");
		Map[2][1].setIcon(stump1);
		Map[2][1].setName("stump1");
		Map[5][4].setIcon(stump1);
		Map[5][4].setName("stump1");
		Map[5][7].setIcon(stump1);
		Map[5][7].setName("stump1");
	        Map[6][7].setIcon(plank2);
	        Map[6][7].setName("plank2");
		Map[7][7].setIcon(stump1);
		Map[7][7].setName("stump1");
		Map[3][4].setIcon(stump1);
		Map[3][4].setName("stump1");
		Map[0][4].setIcon(stump3);
		Map[0][4].setName("stump3");
		manR=12;
		manC=1;
	}
	if(lvl==5)
	{
		Map[0][0].setIcon(stump3);
		Map[0][0].setName("stump3");
		Map[0][8].setIcon(stump3);
		Map[0][8].setName("stump3");
		Map[1][0].setIcon(stump1);
		Map[1][0].setName("stump1");
		Map[2][0].setIcon(stump1);
		Map[2][0].setName("stump1");
		Map[3][1].setIcon(stump1);
		Map[3][1].setName("stump1");
		Map[4][1].setIcon(stump1);
		Map[4][1].setName("stump1");
		Map[5][1].setIcon(stump1);
		Map[5][1].setName("stump1");
		Map[6][2].setIcon(stump1);
		Map[6][2].setName("stump1");
		Map[7][2].setIcon(stump1);
		Map[7][2].setName("stump1");
		Map[8][2].setIcon(stump1);
		Map[8][2].setName("stump1");
		Map[9][3].setIcon(stump1);
		Map[9][3].setName("stump1");
		Map[10][3].setIcon(stump1);
		Map[10][3].setName("stump1");
		Map[11][3].setIcon(stump1);
		Map[11][3].setName("stump1");
		Map[1][8].setIcon(stump1);
		Map[1][8].setName("stump1");
		Map[2][8].setIcon(stump1);
		Map[2][8].setName("stump1");
		Map[3][7].setIcon(stump1);
		Map[3][7].setName("stump1");
		Map[4][7].setIcon(stump1);
		Map[4][7].setName("stump1");
		Map[5][7].setIcon(stump1);
		Map[5][7].setName("stump1");
		Map[6][6].setIcon(stump1);
		Map[6][6].setName("stump1");
		Map[7][6].setIcon(stump1);
		Map[7][6].setName("stump1");
		Map[8][6].setIcon(stump1);
		Map[8][6].setName("stump1");
		Map[9][5].setIcon(stump1);
		Map[9][5].setName("stump1");
		Map[10][5].setIcon(stump1);
		Map[10][5].setName("stump1");
		Map[11][5].setIcon(stump1);
		Map[11][5].setName("stump1");
		Map[12][4].setIcon(stump2);
		Map[12][4].setName("stump2");
                System.out.println("ji");
                instructions.setText("Haha! You made it!!!");
	}
        
		map.repaint();
	
       }
}