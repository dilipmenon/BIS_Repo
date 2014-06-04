package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.List;

import GA.Generation;
import GA.Individual;


public class GenerationFacilityLayout extends Generation{

	private ConstructionSite site;
	private List<FacilityRelation> relations;
	public GenerationFacilityLayout(int numIndividuals, int numberOfGenes, int valueSystem, ConstructionSite site, List<FacilityRelation> relations) {

		//Set Information on Super Class
		super(numberOfGenes,1,valueSystem);
		this.numberOfIndividuals = numIndividuals;
		this.individual = new FacilityLayout[numIndividuals];
		this.site = site;
		this.relations = relations;
		// TODO Auto-generated constructor stub
	}
	
	public static void main (String args[])
	{
		
	}
	
	// Initialize randomly the chromosome with values according to the integer number system valueSystem
		public void initializeRandomlyGeneration() {
			for (int i=0; i<numberOfIndividuals; i++){
				individual[i] =createNewFacilityLayout();
				individual[i].initializeRandomlyIndividual();
			}
		}

		// Initialize the chromosome with the value 0
		public void initializeEmptyGeneration() {
			for (int i=0; i<numberOfIndividuals; i++){
				individual[i] =createNewFacilityLayout();
				individual[i].initializeEmptyIndividual();
			}
		}
		
		private FacilityLayout createNewFacilityLayout()
		{
			ConstructionSite newSite = new ConstructionSite(this.site.getIntitialHeight(), this.site.getInitialWidth());
			newSite.getFacilities().putAll(this.site.cloneFacilities());
			newSite.getConstructedBuildings().putAll(this.site.getConstructedBuildings());
			 
			return new FacilityLayout(newSite,this.relations, this.numberOfGenes, this.valueSystem);
		
		}
		public void setIndividual(int i, Individual indi){
			super.setIndividual(i, indi);
			((FacilityLayout)individual[i]).resetLocationReferences((FacilityLayout)indi);
		}
		
		
		// Print best individual of generation
		public void printBestIndividual() {
			int bestIndividual = getBestIndividualPosition();
			System.out.print("Best: \t");
			individual[bestIndividual].printIndividualShort();
			((FacilityLayout)individual[bestIndividual]).getSite().printCurrentLayout();
		}
		
		public int getBestIndividualPosition()
		{
			double value = individual[0].getFitnessValue();
			int pos = 0;
			for (int i=1; i<numberOfIndividuals; i++){
				if (individual[i].getFitnessValue() < value){
					value = individual[i].getFitnessValue();
					pos = i;
				}
			}	
		return pos;
		}
		
		public void printWorstIndividual() {
			int worstIndividual = getWorstIndividualPosition();
			System.out.print("Worst: \t");
			individual[worstIndividual].printIndividualShort();
			((FacilityLayout)individual[worstIndividual]).getSite().printCurrentLayout();
		}
		
		
		public int getWorstIndividualPosition()
		{
			double value = individual[0].getFitnessValue();
			int pos = 0;
			for (int i=1; i<numberOfIndividuals; i++){
				if (individual[i].getFitnessValue() > value){
					value = individual[i].getFitnessValue();
					pos = i;
				}
			}	
		return pos;
		}
		
		public void crossOverFromParents(int parentA, int parentB, int crossOverIndividual){
			int crossOverPosition = (int) (Math.random()*numberOfGenes);
			
			int numberOfExchangedGenes = 1;
			if(crossOverPosition < (numberOfGenes-1))
			{
				numberOfExchangedGenes =(int) ((numberOfGenes-crossOverPosition)*Math.random());
			}
			individual[crossOverIndividual].setChromosom(individual[parentA].getChromosom());
			individual[crossOverIndividual].setPartOfChromosom(crossOverPosition, individual[parentB].getPartOfChromosom(crossOverPosition, crossOverPosition+numberOfExchangedGenes-1));
			
			
			
		}
		
		public int selectRandomIndividual(int selectionScheme)
		{
			switch(selectionScheme){
			// Selecting randomly individuals 
					case 0: 
					{
						if (verboseLevel > 1) System.out.println("Selecting randomly individuals");
					
							return (int)(Math.random() * numberOfIndividuals);
						
						//break;
					}
					// Selecting best of two randomly chosen individuals 
					case 1: 
					{
						if (verboseLevel > 1) System.out.println("Selecting best of two randomly chosen individuals");
						
							int j = (int)(Math.random() * numberOfIndividuals);
							Individual indi1 = this.getIndividual(j); 
							int k = (int)(Math.random() * numberOfIndividuals);
							Individual indi2 = this.getIndividual(k);
							if (indi1.getRankingValue() < indi2.getRankingValue()){
								if (verboseLevel > 2) System.out.println("Individuum selected:" + j);
								return k;
							}
							else {
								if (verboseLevel > 2) System.out.println("Individuum selected:" + k);			
								return j;													
							}
					}
					// Selecting individuals if their ranking is smaller as a random number
					// If not take randomly another individual
					case 2: 
					{
						if (verboseLevel > 1) System.out.println("Selecting individuals if their ranking is smaller as a random number");
						int j = (int)(Math.random() * numberOfIndividuals);
						Individual indi = this.getIndividual(j); 
						double ranking = indi.getRankingValue();
							if (Math.random() > ranking){
								
								if (verboseLevel > 2) System.out.println("Individuum selected:" + j);
								return j;
							}
							else{
								j = (int)(Math.random()*numberOfIndividuals);
							
								if (verboseLevel > 2) System.out.println("Individuum selected:" + j);
								return j;
							}
					}
					// Selecting individuals if their ranking is smaller as a random number
					// If not, start again selecting individuals if their ranking is smaller as a random number
					// If not, take best individual
					case 3: 
					{
						if (verboseLevel > 1) System.out.println("Selecting individuals if their ranking is smaller as a random number. If not, start again");
						int j = (int)(Math.random() * numberOfIndividuals);
						Individual indi = this.getIndividual(j); 
						double ranking = indi.getRankingValue();
						if (Math.random() > ranking){
							
							if (verboseLevel > 2) System.out.println("Individuum selected:" + j);
							return j;
						}
						else{
							j = (int)(Math.random()*numberOfIndividuals);
							indi = this.getIndividual(j);
							ranking = indi.getRankingValue();
							if (Math.random() > ranking){
								if (verboseLevel > 2) System.out.println("Individuum selected:" + j);
								return j;
							}
							else {
								if (verboseLevel > 2) System.out.println("Individuum selected:" + 0);
								return 0;
							}
						}
					}
			}
			return 0;
		}
		


}
