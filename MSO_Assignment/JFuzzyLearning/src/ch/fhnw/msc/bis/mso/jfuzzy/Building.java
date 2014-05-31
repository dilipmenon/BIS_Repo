package ch.fhnw.msc.bis.mso.jfuzzy;
import java.util.ArrayList;
import java.awt.Point;

public class Building {
	
	String id;
	int height;
	int width;

	private Point positionOfUpperLeftCorner = new Point();
	Point centreOfGravity = new Point();

	ArrayList<BuildingBlock> occupiedBlocks;
	

	

	public Point getCentreOfGravity() {
		return centreOfGravity;
	}
	public void setCenterOfGravity(Point centreOfGravity) {
		this.centreOfGravity = centreOfGravity;
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
		occupiedBlocks = new ArrayList<BuildingBlock>();
		
	}
	
	public Building(String name, int height, int width, int x, int y)
	{
	
		this.height = height;
		this.width = width;
		this.id = name;
		occupiedBlocks = new ArrayList<BuildingBlock>();
		setPosition(x, y, false);
		
	}
	
	public void setPosition(int x, int y, boolean rotate)
	{
		this.positionOfUpperLeftCorner.setLocation(x, y);
		occupiedBlocks.clear();
		
		if(rotate){
			int originalHeight = this.height;
			this.height = this.width;
			this.width = originalHeight;	
		}
			
		
		for (int i=y ;i<y+height;i++)
		{
			
			for (int z=x;z<x+width;z++)
			{
			occupiedBlocks.add(new BuildingBlock(z,i));				
			}
				
		}
		setCentreOfGravity(x,y);
	}
	
	public void setCentreOfGravity(int x, int y)
	{
		double temp1;
		double temp2;
			
		temp1 = (x + (width-1))/2;
		temp2 = (y + (height-1))/2;
		
		centreOfGravity.setLocation(temp1, temp2);;
	}
	
	Point getPositionOfUpperLeftCorner() {
		return positionOfUpperLeftCorner;
	}
	
	public int getLocationReference(int totalWidth)
	{
		return  totalWidth*(positionOfUpperLeftCorner.y-1)+positionOfUpperLeftCorner.x;
	
	}
	public void setLocationReference(int reference, int totalWidth) {
		int y = reference%totalWidth+1;
		int x = reference - totalWidth*(y-1);
		setPosition(x, y, false);
	}
	
	
}
