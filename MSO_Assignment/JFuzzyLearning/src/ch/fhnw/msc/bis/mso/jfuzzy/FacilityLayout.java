package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import GA.Individual;

@SuppressWarnings("unused")
public class FacilityLayout extends Individual {
	// final static int DECIMAL_CODE = 10;
	final static int GENE_LENGTH_ONE = 1;
	private ConstructionSite site;
	private static int THRESHOLD_COUNTER = 100;
	private boolean valid = true;
	List<FacilityRelation> facilityRelations = new ArrayList<FacilityRelation>();
	

	public FacilityLayout(ConstructionSite site, List<FacilityRelation> facilityRelations, int numberOfGenes, int valueSystem) {
		
		super(numberOfGenes, GENE_LENGTH_ONE, valueSystem);
		this.facilityRelations = facilityRelations;
		this.site = site;

	}

	private void validateCurrentLayout() {

		for (Building building : getSite().getFacilities().values()) {
			if (!getSite().hasBuildingValidPosition(building)) {
				valid = false;
				return;
			}
		}
		valid = true;
	}

	public void encodeGeneFromLayout() {
		for (Building building : getSite().getFacilities().values()) {
			(this.getChromosom())[Integer.parseInt(building.id) - 1] = building
					.getLocationReference(getSite().getInitialWidth());
		}
	}

	public void decodeGeneToLayout() {
		for (Building building : getSite().getFacilities().values()) {
			building.setLocationReference(
					this.getChromosom()[Integer.parseInt(building.id) - 1],
					getSite().getInitialWidth(),false);
		}
		
	}
	
	public void resetLocationReferences(FacilityLayout layout)
	{
		this.getSite().getFacilities().clear();
		for (Building building : layout.getSite().getFacilities().values()) {
			this.getSite().getFacilities().put(building.id, new Building(building.id, building.height, building.width,
					building.getPositionOfUpperLeftCorner().x, building.getPositionOfUpperLeftCorner().y));
		}
		
	}
	

	public void initializeRandomlyIndividual()  {
		
		ArrayList<Building> temporaryCollection = new ArrayList<Building>(getSite()
				.getFacilities().values());
		int failedAttempt = 0;
		while (temporaryCollection.size() > 0
				&& failedAttempt < THRESHOLD_COUNTER) {
			Building randomFacility = temporaryCollection.get((int) (Math
					.random() * temporaryCollection.size()));

			if (!getSite().placeFacilityRandomly(randomFacility.id)) {
				System.out.println(randomFacility.id
						+ ": failed to place it on Site without violation");
				failedAttempt++;
				valid = false;
			} else {
				
				temporaryCollection.remove(randomFacility);
				failedAttempt = 0;
				valid =true;
			}
		}
		encodeGeneFromLayout();
		computeFitnessValue();
	}

	public boolean checkValidity() {
		decodeGeneToLayout();
		validateCurrentLayout();
		return valid;
	}
	
	public double computeFitnessValue() {
		double fitness = 0.00;
		for (FacilityRelation relation : this.facilityRelations) {
		    fitness += getSite().getDistance(relation.getFirstId(),relation.getSecondId())*relation.GetCalculatedAverageClosenessValue();
		      // total += relation.GetCalculatedAverageClosenessValue()
		}
		this.fitnessValue = fitness; 
		return fitness;
	}

	public ConstructionSite getSite() {
		return site;
	}

	

}
