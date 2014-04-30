package ch.fhnw.msc.bis.mso.jfuzzy;
import java.awt.geom.*;
import java.util.ArrayList;

public class Building {
	
	int positionx = 3;
	int positiony = 2;
	boolean zeroRotation;
	String id;
	int height;
	int width;
	ArrayList<Coordinate> blockedPositions;
	ArrayList <Coordinate> centerOfGravity;
	

	public ArrayList<Coordinate> getCenterOfGravity() {
		return centerOfGravity;
	}
	public void setCenterOfGravity(ArrayList<Coordinate> centerOfGravity) {
		this.centerOfGravity = centerOfGravity;
	}
	public int getPositionx()
	{
		return positionx;
	}
	public int getPositiony()
	{
		return positiony;
	}
	public boolean getRotation()
	{
		return zeroRotation;
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
	
	
	public Building(int x, int y, boolean rotation, String name, int height, int width)
	{
		this.positionx = x;
		this.positiony = y;
		this.height = height;
		this.zeroRotation = rotation;
		this.width = width;
		this.id = name;
		blockedPositions = new ArrayList<Coordinate>();
		
	}
	
	public void setPosition(int x, int y, boolean rotation)
	{
		this.positionx = x;
		this.positiony = y;
		blockedPositions.clear();
		
		for (int i=0; i<width; i++)
		  {
			if (rotation == false)
			{
			blockedPositions.add(new Coordinate(x+i,y));
			}
			else
			{
			blockedPositions.add(new Coordinate(y+i,x));
			}
				
		  }
		
		for (int i=0; i<=height; i++)
		  {
			if (rotation == false)
			{
			blockedPositions.add(new Coordinate(x,i+y));
			}
			else
			{
			blockedPositions.add(new Coordinate(y,x+i));	
			}
		  }
		  
		setGravity(x,y);
		// Schwerpunkt berechnen		
		
	}
	
	public void setGravity(int x, int y)
	{
		double temp1;
		double temp2;
		
		
		temp1 = (x + (width-1))/2;
		temp2 = (y + (height-1))/2;
		
		centerOfGravity.add(new Coordinate(temp1, temp2));
		
	}
}
