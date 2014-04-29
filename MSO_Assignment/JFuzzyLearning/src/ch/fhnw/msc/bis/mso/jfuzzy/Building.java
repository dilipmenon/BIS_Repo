package ch.fhnw.msc.bis.mso.jfuzzy;
import java.awt.geom.*;

public class Building {
	
	int positionx = 3;
	int positiony = 2;
	int rotation = 0;
	String id;
	int height;
	int width;
	int[][] blockedPositions; 
	
	public int getPositionx()
	{
		return positionx;
	}
	public int getPositiony()
	{
		return positiony;
	}
	public int getRotation()
	{
		return rotation;
	}
	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width;
	}
	public String getId()
	{
		return id;
	}
	
	
	public Building(int x, int y, int rotation, String name, int height, int width)
	{
		this.positionx = x;
		this.positiony = y;
		this.height = height;
		this.rotation = rotation;
		this.width = width;
		this.id = name;
		blockedPositions = new int[height*width][height*width];
		
	}
	
	public void resetPosition(int x, int y, int rotation)
	{
		 
		
		
		int counter=0;
		  while(counter<height*width)
		  {
		  blockedPositions[counter++]= 
		  }
		  
		// x + y neu setzen
		
		
		// berechnung blockedposition

		
		
		
	}
}
