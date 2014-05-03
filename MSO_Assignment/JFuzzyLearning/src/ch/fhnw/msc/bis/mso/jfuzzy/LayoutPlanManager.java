package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.ArrayList;

public class LayoutPlanManager {
	
	
	
	public static void main(String[] args)
	{
		int siteHeight = 100;
		int siteWidth = 200;
		
		ConstructionSite mySite = new ConstructionSite(siteHeight,siteWidth);
		
		//Initialize Constructed Building
		mySite.getConstructedBuildings().put(0,new Building("6",12,6,2,5,false));
		mySite.getConstructedBuildings().put(1,new Building("6",6,12,8, 11, false));
		
		//Initialize Facility Building Base Data
		mySite.getFacilities().put(1,new Building("1",4,2));
		mySite.getFacilities().put(2,new Building("2",3,4));
		mySite.getFacilities().put(3,new Building("3",5,5));
		mySite.getFacilities().put(4,new Building("4",3,2));
		mySite.getFacilities().put(5,new Building("5",3,1));
		
		//Place Facilities on Site Randomly
		ArrayList<Building> temporaryCollection = (ArrayList<Building>)mySite.getFacilities().values();
		while (temporaryCollection.size() > 0)
		{
		Building randomFacility = temporaryCollection.get((int)(Math.random()*temporaryCollection.size()));
	
		mySite.placeFacilityRandomly(randomFacility.id);
		temporaryCollection.remove(randomFacility);
		
		
		}
		
		
		
		
		
		
		
	}

}
