package ch.fhnw.msc.bis.mso.jfuzzy;
import java.util.ArrayList;

public class Building {
	
	int positionx = 3;
	int positiony = 2;
	boolean zeroRotation;
	String id;
	int height;
	int width;
	ArrayList<BuildingBlock> blockedPositions;
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
	
	
	public Building(String name, int height, int width)
	{
	
		this.height = height;
		this.width = width;
		this.id = name;
		blockedPositions = new ArrayList<BuildingBlock>();
		
	}
	
	public Building(String name, int height, int width, int x, int y, boolean rotation)
	{
	
		this.height = height;
		this.width = width;
		this.id = name;
		blockedPositions = new ArrayList<BuildingBlock>();
		setPosition(x, y, rotation);
		
	}
	
	public void setPosition(int x, int y, boolean rotation)
	{
		this.positionx = x;
		this.positiony = y;
		blockedPositions.clear();
		
		for (int i=x; i<=width; i++)
		  {
			rotation = 
			blockedPositions.add(new BuildingBlock(i,y));
		  }
		
		for (int i=y; i<=height; i++)
		  {
			blockedPositions.add(new BuildingBlock(x,i));
		  }
		  
		// Schwerpunkt berechnen
	
		
		
	}
}
