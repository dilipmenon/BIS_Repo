package ch.fhnw.msc.bis.mso.jfuzzy;
import java.util.ArrayList;

public class Building {
	
	int positionx;
	int positiony;
	boolean zeroRotation;
	String id;
	int height;
	int width;


	ArrayList<BuildingBlock> blockedPositions;
	BuildingBlock centerOfGravity;

	

	public BuildingBlock getCenterOfGravity() {
		return centerOfGravity;
	}
	public void setCenterOfGravity(BuildingBlock centerOfGravity) {
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
		this.zeroRotation = rotation;
		blockedPositions.clear();
		
		
		for (int i=positiony;i<positiony+height;i++)
		{
			blockedPositions.add(new BuildingBlock(positionx,positiony));
			for (int z=positionx;z<positionx+width;z++)
			{
				positionx++;
				blockedPositions.add(new BuildingBlock(positionx,positiony));				
			}
			positiony++;
				
		}
		
		
		/*
		for (int i=0; i<width; i++)
		  {
		
			blockedPositions.add(new BuildingBlock(i,y));
			if (rotation == false)
			{
			blockedPositions.add(new BuildingBlock(x+i,y));
			}
			else
			{
			blockedPositions.add(new BuildingBlock(y+i,x));
			}
				

		  }
		
		for (int i=0; i<=height; i++)
		  {

			blockedPositions.add(new BuildingBlock(x,i));

			if (rotation == false)
			{
			blockedPositions.add(new BuildingBlock(x,i+y));
			}
			else
			{
			blockedPositions.add(new BuildingBlock(y,x+i));	
			}

		  }
		  */

		setGravity(x,y);
	
	}
	
	public void setGravity(int x, int y)
	{
		double temp1;
		double temp2;
		

		
		temp1 = (x + (width-1))/2;
		temp2 = (y + (height-1))/2;
		
		centerOfGravity = new BuildingBlock(temp1, temp2);
		
	}
}
