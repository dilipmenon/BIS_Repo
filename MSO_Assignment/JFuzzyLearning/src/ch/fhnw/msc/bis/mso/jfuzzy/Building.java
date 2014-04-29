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
	double centerOfGravity;
	
	public double getCenterOfGravity() 
	{
		return centerOfGravity;
	}
	public void setCenterOfGravity(double centerOfGravity) {
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
		
		for (int i=x; i<=width; i++)
		  {
			rotation = 
			blockedPositions.add(new Coordinate(i,y));
		  }
		
		for (int i=y; i<=height; i++)
		  {
			blockedPositions.add(new Coordinate(x,i));
		  }
		  
		// Schwerpunkt berechnen
		  
		  
		// x + y neu setzen
		// berechnung blockedposition

		
		
		
	}
}
