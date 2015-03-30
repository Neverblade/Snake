import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class Board extends JPanel
{
	Cell[][] grid;
	
	public Board(Cell[][] grid)
	{
		this.grid = grid;
	}
	
	public void paintComponent(Graphics g)
	{
		int width = (int) Math.round(getWidth() / grid[0].length);
		int height = (int) Math.round(getHeight() / grid.length);
		
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				g.setColor(Color.WHITE);
				if (grid[i][j].getState() == 1) g.setColor(Color.LIGHT_GRAY);
				else if (grid[i][j].getState() == 2) g.setColor(Color.GRAY);
				else if (grid[i][j].getState() == 3) g.setColor(Color.RED);
				g.fillRect(j*width, i*height, width, height);
			}
		}
	}
}
