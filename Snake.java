import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class Snake
{
	public static int size = 15;
	public static Cell[][] grid = new Cell[40][40];
	public static int dir = 1;
	public static int medDir = 1;
	public static ArrayList<Point> creature = new ArrayList<Point>();
	public static boolean playTime = false;
	public static int delayIndex = 1;
	public static int[] delays = {70, 50, 30};
	public static int score = 0;
	
	//                        D  W   A  S
	public static int[] dX = {1, 0, -1, 0};
	public static int[] dY = {0, -1, 0, 1};
	
	public static void main(String[] args)
	{
		//create the grid
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				grid[i][j] = new Cell();
			}
		}
		
		//create the board and add the keylistener
		Board board = new Board(size, grid);
		board.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				char input = e.getKeyChar();
				if (input == 'd' || input == 'D') medDir = 0;
				if (input == 'w' || input == 'W') medDir = 1;
				if (input == 'a' || input == 'A') medDir = 2;
				if (input == 's' || input == 'S') medDir = 3;
			}
		});
		
		//make the reset button
		JButton playButton = new JButton("   Play   ");
		playButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!playTime) playTime = true;
			}
		});
		
		//make the difficulty button
		JButton diffButton = new JButton("Difficulty: Medium");
		diffButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!playTime)
				{
					String[] diffNames = {"Easy", "Medium", "Hard"};
					delayIndex = (delayIndex + 1) % 3;
					diffButton.setText("Difficulty: " + diffNames[delayIndex]);
				}
			}
		});
		
		//create the score label
		JLabel scoreLabel = new JLabel("Score: " + score);
		
		//create the button panel and add components
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(playButton);
		buttonPanel.add(diffButton);
		buttonPanel.add(scoreLabel);
		
		//create the frame
		JFrame frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(board, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		frame.validate();
		frame.pack();
		frame.setVisible(true);
		
		
		//run the game loop
		while (true)
		{
			if (playTime)
			{
				playGame(board, scoreLabel);
				playButton.setText("Play Again");
				playTime = false;
			}
		}
	}
	
	public static void createCherry()
	{
		Random r = new Random();
		while (true)
		{
			int tempX = r.nextInt(grid.length);
			int tempY = r.nextInt(grid[0].length);
			if (grid[tempY][tempX].getState() == 0)
			{
				grid[tempY][tempX].setState(3);
				break;
			}
		}
	}
	
	public static void playGame(Board board, JLabel scoreLabel)
	{	
		/* Initialize */
		
		iniBoard(board);
		
		/*Run the Loop*/
		while (true)
		{
			//constantly grab focus
			board.setFocusable(true);
			board.requestFocusInWindow();
			
			//check the dir
			if ((medDir + 2) % 4 != dir) dir = medDir;
			
			//set current point and the new space to move to
			Point head = creature.get(0);
			int newX = head.getX() + dX[dir];
			int newY = head.getY() + dY[dir];
			
			//check for out of bounds
			if (newX < 0 || newX >= grid[0].length || newY < 0 || newY >= grid.length || grid[newY][newX].getState() == 1)
			{
				System.out.println("Failed");
				break;
			}
			
			//check if it's a cherry
			boolean ateCherry = false;
			if (grid[newY][newX].getState() == 3)
			{
				ateCherry = true;
				score++;
				scoreLabel.setText("Score: " + score);
				
			}
			
			//move the head
			creature.add(0, new Point(newX, newY));
			grid[newY][newX].setState(2);
			grid[head.getY()][head.getX()].setState(1);
			
			//move the tail (unless a cherry was eaten)
			if (!ateCherry)
			{
				Point tail = creature.remove(creature.size() - 1);
				grid[tail.getY()][tail.getX()].setState(0);
			}
			
			//if a cherry was eaten, make a new one
			if (ateCherry) createCherry();
			
			//paint and sleep
			board.repaint();
			try{Thread.sleep(delays[delayIndex]);} catch (Exception e){}
		}		
	}
	
	//initialize the board
	public static void iniBoard(Board board)
	{
		//grab focus
		board.setFocusable(true);
		board.requestFocusInWindow();
		
		/* Reset all the grid values and the creature list*/
		
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				grid[i][j].setState(0);
			}
		}
		creature = new ArrayList<Point>();
		
		//directions
		dir = 1;
		medDir = 1;
		
		//put a block in the center
		creature.add(new Point(grid[0].length / 2, grid.length / 2));
		grid[grid.length / 2][grid[0].length / 2].setState(2);
		
		//now build the starting tail
		int tempY = grid.length / 2;
		int tempX = grid[0].length / 2;
		for (int i = 1; i <= 3; i++)
		{
			creature.add(new Point(tempX, tempY + i));
			grid[tempY + i][tempX].setState(1);
		}

		//create an initial cherry
		createCherry();
		
		//set the initial board
		board.repaint();
		try{Thread.sleep(500);} catch (Exception e){}		
	}
}
