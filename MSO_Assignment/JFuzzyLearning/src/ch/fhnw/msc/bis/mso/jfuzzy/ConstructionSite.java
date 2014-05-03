package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConstructionSite {
	private int initialHeight;
	private int initialWidth;
	private int currentWidth;
	private int currentHeight;

	private Map<String, Building> facilities;
	private Map<Integer, Building> constructedBuildings;

	public Map<String, Building> getFacilities() {
		return facilities;
	}

	public Map<Integer, Building> getConstructedBuildings() {
		return constructedBuildings;
	}

	public ConstructionSite(int height, int width) {
		this.facilities = new HashMap<String, Building>();
		this.constructedBuildings = new HashMap<Integer, Building>();
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

	public ArrayList<Building> getAllBuildings() {
		ArrayList<Building> allBuildings = new ArrayList<Building>(facilities
				.values());
		allBuildings
				.addAll(new ArrayList<Building>(constructedBuildings.values()));
		return allBuildings;
	}

	public boolean hasBuildingValidPosition(Building testBuilding) {
		ArrayList<Building> allBuildings = getAllBuildings();
		for (Building building : allBuildings) {
			if (building.id.equals(testBuilding.id))
				continue;

			if (doBuildingsOverlap(testBuilding, building))
				return true;
		}
		if (isBuildingOutsideBoundary(testBuilding))
			return true;
		return false;
	}

	private boolean doBuildingsOverlap(Building firstBuilding,
			Building secondBuilding) {
		for (BuildingBlock currentBlock : firstBuilding.occupiedBlocks) {
			if (secondBuilding.occupiedBlocks.contains(currentBlock))
				return true;

		}
		return false;
	}

	private boolean isBuildingOutsideBoundary(Building building) {
		for (BuildingBlock position : building.occupiedBlocks) {
			double x = position.getX();
			double y = position.getY();
			if (x < 1 || x > currentWidth || y < 1 || y > currentHeight)
				return true;
		}
		return false;
	}

	public boolean placeFacilityRandomly(String id) {
		Building facility = this.facilities.get(id);

		if (facility != null)
			facility.occupiedBlocks.clear();

		ArrayList<BuildingBlock> availableBlocks = getFreeBuildingBlocks();
		availableBlocks.size();
		BuildingBlock randomBlock = null;

		boolean facilityPlaced = false;

		while (!facilityPlaced && availableBlocks.size() > 0) {

			facilityPlaced = true;
			randomBlock = availableBlocks
					.get((int) (Math.random() * availableBlocks.size()));
			
			//1st attempt
			facility.setPosition((int) randomBlock.getX(),
					(int) randomBlock.getY(), false);

			if (!hasBuildingValidPosition(facility)) {
				//2nd attempt: Rotate and try again
				facility.setPosition((int) randomBlock.getX(),
						(int) randomBlock.getY(), true);

				
				if (!hasBuildingValidPosition(facility)) {
					// Even not valid with rotation
					availableBlocks.remove(randomBlock);
					
					facilityPlaced = false;
				}

			}
		}
		return facilityPlaced;

	}

	private ArrayList<BuildingBlock> getFreeBuildingBlocks() {
		ArrayList<BuildingBlock> freeBlocks = new ArrayList<BuildingBlock>();
		for (int currentX = 1; currentX < this.currentWidth; currentX++) {
			for (int currentY = 1; currentY < this.currentHeight; currentY++) {
				freeBlocks.add(new BuildingBlock(currentX, currentY));
			}
		}
		freeBlocks.removeAll(getOccupiedBuildingBlocks());
		return freeBlocks;

	}

	private ArrayList<BuildingBlock> getOccupiedBuildingBlocks() {
		ArrayList<Building> allBuildings = getAllBuildings();
		ArrayList<BuildingBlock> occupiedBlocks = new ArrayList<BuildingBlock>();
		for (Building building : allBuildings) {
			occupiedBlocks.addAll(building.occupiedBlocks);

		}
		return occupiedBlocks;
	}

	public void printCurrentLayout() {

	}

}
