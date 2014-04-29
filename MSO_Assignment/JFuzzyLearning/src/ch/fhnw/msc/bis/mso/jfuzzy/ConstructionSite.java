package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.ArrayList;

public class ConstructionSite {
	private int intitialHeight;
	private int initialWidth;
	private int currentWidth;
	private int currentHeight;
	
	private Building[] facilities;
	private Building[] constructedBuildings;
	private ArrayList<Coordinate> blockedFields;
	
	public ConstructionSite(Building[] facilities, Building[] constructedBuildings, int height, int width)
	{
		this.facilities = facilities;
		this.constructedBuildings = constructedBuildings;
		this.initialWidth = width;
		this.intitialHeight = height;
	}
	
	public int getIntitialHeight() {
		return intitialHeight;
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
	
	public boolean isCollisionDetected(Building newBuilding)
	{
		//Vergleiche mit den anderen Gebäuden -- ausser mit sich selber
		for (Building building : facilities) {
			if(building.id.equals(newBuilding.id))
				continue;
		
		//Vergleiche mit L
		//Vergleiche im Bereich Construction Site
		}
		return false;
	}
	
	public void initializeRandomLayout()
	{
		
	}
	
	public void printCurrentLayout()
	{
		
	}
	
	
	
}
