package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GA.Individual;
import GA.Population;

public class PopulationFacilityLayout extends Population {
	
	private List<FacilityRelation> facilityRelations;
	private ConstructionSite site;
	private int numberOfInfeasibleSolutions = 0;
	

	public PopulationFacilityLayout(int numberOfIndividuals, int numberOfGenerations,ConstructionSite site, List<FacilityRelation> facilityRelations) {
		
		super(site.getFacilities().size(), 1, site.getInitialWidth()*site.getIntitialHeight()-1, numberOfIndividuals);
		this.numberOfGenerations = numberOfGenerations;
		this.generation = new GenerationFacilityLayout[numberOfGenerations];
		this.site = site;
	
		this.facilityRelations = facilityRelations;
	}
	
	private Map<String, Building> cloneBuildings(Map<String, Building> buildingToClone)
	{
		Map<String, Building> clone = new HashMap<String,Building>();
		for(Map.Entry<String, Building> entry: buildingToClone.entrySet())
		{
			Building building = entry.getValue();
			clone.put(building.id, new Building(building.id,building.height,building.width));
		}
		
		return clone;
		
	}
	
	// Create a given new generation
	public void createGivenNewGeneration(int numGen) {
		generation[numGen] = new GenerationFacilityLayout(this.numberOfIndividuals, this.numberOfGenes, this.valueSystem, 
				this.site, this.facilityRelations);
	}
	
	// Print best individual in a given generation
		public void printWorstIndividualInGivenGeneration(int numGen) {
			((GenerationFacilityLayout)generation[numGen]).printWorstIndividual();
		}
		
		public void cloneGeneration(int generationToClone, int nextGeneration)
		{
			
			if (verboseLevel > 1) System.out.println("Cloned generation " + generationToClone + " to " + nextGeneration);
			for (int i=0; i<numberOfIndividuals; i++){
				
				Individual indi = generation[generationToClone].getIndividual(i); 
				generation[nextGeneration].setIndividual(i, indi);
				((FacilityLayout)(generation[nextGeneration].getIndividual(i))).decodeGeneToLayout();
				
			}
			
		}
		
		public void replaceWorstIndividualInGivenGeneration(int givenGeneration, double mutationProability)
		{
			boolean feasibleSolutionFound = false;
			numberOfInfeasibleSolutions = 0;
		//Step 1: Get worst individual in given Generation
			int worstIndividualPos = ((GenerationFacilityLayout)generation[givenGeneration]).getWorstIndividualPosition();
			int[] worstIndividualChromosom = generation[givenGeneration].getIndividual(worstIndividualPos).getChromosom();
			double worstIndividualFitnessValue = generation[givenGeneration].getIndividual(worstIndividualPos).getFitnessValue();
		//Step 2: replace worst Individual by either Mutation or Selection
			while(!feasibleSolutionFound)
			{
				numberOfInfeasibleSolutions++;
				if(Math.random() > mutationProability )
				{
					GenerationFacilityLayout currentGeneration = ((GenerationFacilityLayout)generation[givenGeneration]);
					int parentA = currentGeneration.selectRandomIndividual(3);
					int parentB = currentGeneration.selectRandomIndividual(3);
					currentGeneration.crossOverFromParents(parentA, parentB, worstIndividualPos);
				}
				else {
					generation[givenGeneration].getIndividual(worstIndividualPos).mutateOnePosition(1.0);
				}
				
				feasibleSolutionFound = ((FacilityLayout)generation[givenGeneration].getIndividual(worstIndividualPos)).checkValidity();
			}
			numberOfInfeasibleSolutions--;
			
			double newOffspringFitness = generation[givenGeneration].getIndividual(worstIndividualPos).computeFitnessValue();
			//Keep current worst value individual if the new does not outperform the current worst solution
			if((newOffspringFitness) > worstIndividualFitnessValue)
			{
				 generation[givenGeneration].getIndividual(worstIndividualPos).setChromosom(worstIndividualChromosom);
				 ((FacilityLayout)generation[givenGeneration].getIndividual(worstIndividualPos)).checkValidity();
				
			}
			
			
		
		}

		public int getNumberOfInfeasibleSolutions() {
			return numberOfInfeasibleSolutions;
		}

		
		

}
