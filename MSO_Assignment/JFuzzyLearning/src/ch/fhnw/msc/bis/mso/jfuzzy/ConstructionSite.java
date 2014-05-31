package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ConstructionSite   {
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
		ArrayList<Building> allBuildings = new ArrayList<Building>(
				facilities.values());
		allBuildings.addAll(new ArrayList<Building>(constructedBuildings
				.values()));
		return allBuildings;
	}
	

	public boolean hasBuildingValidPosition(Building testBuilding) {
		if (isBuildingOutsideBoundary(testBuilding))
			return false;
		
		ArrayList<Building> allBuildings = getAllBuildings();
		
		for (Building building : allBuildings) {
			if (building.id.equals(testBuilding.id))
				continue;

			if (doBuildingsOverlap(testBuilding, building))
				return false;
		}
		
		return true;
	}
	
	public double getDistance(Building firstBuilding, Building secondBuilding)
	{
		return Math.pow(Math.pow(firstBuilding.centreOfGravity.x - secondBuilding.centreOfGravity.x,2)
		+Math.pow(firstBuilding.centreOfGravity.y-secondBuilding.centreOfGravity.y, 2),0.5);
	}
	
	public double getCloseness(Building firstBuilding, Building secondBuilding)
	{
	return 0.00;
	}
	
	private boolean doBuildingsOverlap(Building firstBuilding,
			Building secondBuilding) {
		for (BuildingBlock currentBlock : firstBuilding.occupiedBlocks) {
			if (secondBuilding.occupiedBlocks.contains(currentBlock))
				return true;

		}
		return false;
	}
	
	public ArrayList<Building> getBuildingById(int id)
	{
	 ArrayList<Building> result =  new ArrayList<Building>();
	 //First try to receive a facility with given id
	 Building facility = getFacilities().get(id);
	 
	 if(facility != null)
	 {
		 result.add(facility);
	 }
	 //Otherwise try to retrieve construction building(s) with given id
	 else {
			for (Building construction : getConstructedBuildings().values()) {
				if(Integer.parseInt(construction.getId()) == id)
				{
					result.add(construction);
				}
			}
	 }
	 return result;
	 
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
		
		BuildingBlock randomBlock = null;

		boolean facilityPlaced = false;

		while (!facilityPlaced && availableBlocks.size() > 0) {

			facilityPlaced = true;
			randomBlock = availableBlocks
					.get((int) (Math.random() * availableBlocks.size()));

			// 1st attempt
			facility.setPosition((int) randomBlock.getX(),
					(int) randomBlock.getY(), false);

			if (!hasBuildingValidPosition(facility)) {
				// 2nd attempt: Rotate and try again
				facility.setPosition((int) randomBlock.getX(),
						(int) randomBlock.getY(), true);

				if (!hasBuildingValidPosition(facility)) {
					// Even not valid after rotation
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

	private String retrieveBuildingIdOfGivenBlock(BuildingBlock myBlock) {
		for (Building building : getAllBuildings()) {
			if (building.occupiedBlocks.contains(myBlock)) {
				return building.id;
			}
		}
		return "+";
	}

	public void printCurrentLayout() {
		for (int currentY = 1; currentY <= this.currentHeight; currentY++) {
			for (int currentX = 1; currentX <= this.currentWidth; currentX++) {
            System.out.print(retrieveBuildingIdOfGivenBlock(new BuildingBlock(
								currentX, currentY)));
			}
			System.out.println();
		}
	}
	
	public double getDistance(int idFirstBuilding, int idSecondBuilding){
	double distance = 0.00;	
	int numberOfFirstBuildings = 0;
	int numberOfSecondBuildings = 0;
	
	
		for (Building buildingFirst :  getBuildingById(idFirstBuilding)) {
			numberOfFirstBuildings++;
			for (Building buildingSecond :  getBuildingById(idFirstBuilding)) {
				distance += buildingFirst.getCentreOfGravity().distance(buildingSecond.getCentreOfGravity());
				numberOfSecondBuildings++;
			}
		}
		int numberOfCalculations = numberOfFirstBuildings*numberOfSecondBuildings;
		//if one facility or construction building consists of multiple building take the average of the distances
		if(numberOfCalculations > 1)
			distance = distance / numberOfCalculations;
		
		return distance;
	}
}
	
	