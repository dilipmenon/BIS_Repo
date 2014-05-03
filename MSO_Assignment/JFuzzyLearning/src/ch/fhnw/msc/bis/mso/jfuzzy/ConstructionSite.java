package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConstructionSite {
	private int initialHeight;
	private int initialWidth;
	private int currentWidth;
	private int currentHeight;
	
	private Map<Integer,Building> facilities;
	private Map<Integer,Building> constructedBuildings;
	
	
	public Map<Integer,Building> getFacilities() {
		return facilities;
	}

	public Map<Integer,Building> getConstructedBuildings() {
		return constructedBuildings;
	}
	
	public ConstructionSite(int height, int width)
	{
		this.facilities = new HashMap<Integer,Building>();
		this.constructedBuildings = new HashMap<Integer,Building>();
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
	
	public ArrayList<Building> getAllBuildings()
	{
		ArrayList<Building> allBuildings = (ArrayList<Building>) facilities.values();
		allBuildings.addAll( (ArrayList<Building>)constructedBuildings.values());
		return allBuildings;
	}
	
	public boolean hasBuildingValidPosition(Building testBuilding)
	{
		ArrayList<Building> allBuildings = getAllBuildings();
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
		for (BuildingBlock currentBlock: firstBuilding.blockedPositions)
		{
			if (secondBuilding.blockedPositions.contains(currentBlock))
				return true;
			
		}
		return false;
	}
	
	private boolean isBuildingOutsideBoundary(Building building)
	{
		for(BuildingBlock position: building.blockedPositions)
		{
		double x =position.getX();
		double y = position.getY();
		if(x < 0 || x > currentWidth || y < 0 || y > currentHeight) 
			return true;
		}
		return false;
	}
	
	public boolean placeFacilityRandomly(String id)
	{
		Building building = this.facilities.get(id);
		
		if (building != null)
			building.blockedPositions.clear();
		
		ArrayList<BuildingBlock> availableBlocks = getFreeBlocks();
		BuildingBlock randomBlock = null;
		boolean facilityPlaced = false;
		
		while(!facilityPlaced || availableBlocks.size() > 0)
		{
			facilityPlaced = true;
		randomBlock = availableBlocks.get((int)(Math.random()*availableBlocks.size()));
		building.setPosition((int)randomBlock.getX() ,(int)randomBlock.getY(),false);
		
		if (!hasBuildingValidPosition(building))
		{
			building.setPosition((int)randomBlock.getX() ,(int)randomBlock.getY(),false);
			if (!hasBuildingValidPosition(building))
			{
				availableBlocks.remove(randomBlock);
				facilityPlaced = false;
			}
			
		}
		
		}
		
		return facilityPlaced;
	
	}
		
	private ArrayList<BuildingBlock> getFreeBlocks() {
		ArrayList<BuildingBlock> freeBlocks = new ArrayList<BuildingBlock>();
		for (int currentX=0;currentX < this.currentWidth;currentX++)
		{
			for(int currentY = 0; currentY < this.currentHeight;currentY++)
			{
				freeBlocks.add(new BuildingBlock(currentX,currentY));
			}
		}
		freeBlocks.removeAll(getOccupiedBuildingBlocks());
		return freeBlocks;
		
	}

	private ArrayList<BuildingBlock> getOccupiedBuildingBlocks() {
		ArrayList<Building> allBuildings = getAllBuildings();
		ArrayList<BuildingBlock> occupiedBlocks = new ArrayList<BuildingBlock>();
		for (Building building : allBuildings) {
			occupiedBlocks.addAll(building.blockedPositions);
		
		}
		return occupiedBlocks;
	}

	public void printCurrentLayout()
	{
		
	}
	
	
	
}
