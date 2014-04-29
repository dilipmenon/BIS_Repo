package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.ArrayList;

public class ConstructionSite {
	private int initialHeight;
	private int initialWidth;
	private int currentWidth;
	private int currentHeight;
	
	private ArrayList<Building> facilities;
	private ArrayList<Building> constructedBuildings;
	
	
	public ArrayList<Building> getFacilities() {
		return facilities;
	}

	public ArrayList<Building> getConstructedBuildings() {
		return constructedBuildings;
	}
	
	public ConstructionSite(int height, int width)
	{
		this.facilities = new ArrayList<Building>();
		this.constructedBuildings = new ArrayList<Building>();
		this.initialWidth = width;
		this.initialHeight = height;
		currentHeight = initialHeight;
		currentWidth = initialWidth;
	}
	
	public int getIntitialHeight() {
		return initialHeight;
	}
	
	public int getInitialWidth() {
		return initialWidth;
	}
	
	public int getCurrentWidth() {
		return currentWidth;
	}
	public void setCurrentWidth(int currentWidth) {
		this.currentWidth = currentWidth;
	}
	public int getCurrentHeight() {
		return currentHeight;
	}
	public void setCurrentHeight(int currentHeight) {
		this.currentHeight = currentHeight;
	}
	
	public boolean hasBuildingValidPosition(Building testBuilding)
	{
		ArrayList<Building> allBuildings = (ArrayList<Building>) facilities.clone();
		allBuildings.addAll(constructedBuildings);
		
		for (Building building : allBuildings) {
			if(building.id.equals(testBuilding.id))
				continue;
			
			if (doBuildingsOverlap(testBuilding,building))
				return true;
		}
		if (isBuildingOutsideBoundary(testBuilding))
			return true;
		return false;
	}
	
	private boolean doBuildingsOverlap(Building firstBuilding, Building secondBuilding)
	{
		for (Coordinate currentBlock: firstBuilding.blockedPositions)
		{
			if (secondBuilding.blockedPositions.contains(currentBlock))
				return true;
			
		}
		return false;
	}
	
	private boolean isBuildingOutsideBoundary(Building building)
	{
		for(Coordinate position: building.blockedPositions)
		{
		double x =position.getX();
		double y = position.getY();
		if(x < 0 || x > currentWidth || y < 0 || y > currentHeight) 
			return true;
		}
		return false;
	}
		
	public void printCurrentLayout()
	{
		
	}
	
	
	
}
