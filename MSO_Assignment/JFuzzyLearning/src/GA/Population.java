package GA;
/** @project: genetic algorithm
 * @date: 2012-02-25
 * @author: Rolf Dornberger
 * @copyright: 2006, FHNW
 * @description: Population Class is the class for defining the population consisting of generations of individuals
 */

public class Population {
	
	protected static int verboseLevel = 0;  // Verbose level from 0 (no output) to 3 (full output)

	protected int numberOfGenes;       // Number of genes in a chromosome
	protected int lengthOfOneGene;     // Length (number of positions) of one gene
	protected int valueSystem;         // Value of number system of which the chromosome string exists: e.g. 2 = binary, 10 = decimal
	protected int numberOfIndividuals; // Number of individuals in a generation
	protected int numberOfGenerations; // Maximum number of generations to be allowed (not necessarily to be created and used)
	protected Generation[] generation; // Population is given as an array of generation objects
	 
	
	// Create population as a set of objects of the class generation
	public Population(int numOfGene, int lenOfGene, int valSystem, int numIndividuals, int numGenerations) {
		numberOfGenes       = numOfGene;
		lengthOfOneGene     = lenOfGene;
		valueSystem         = valSystem;
		numberOfIndividuals = numIndividuals;
		numberOfGenerations = numGenerations;
		generation          = new Generation[numberOfGenerations];
	}
	
	protected Population(int numOfGene, int lenOfGene, int valSystem, int numIndividuals) {
		numberOfGenes       = numOfGene;
		lengthOfOneGene     = lenOfGene;
		valueSystem         = valSystem;
		numberOfIndividuals = numIndividuals;
		
	}
	
	

	// Get number of generations
	public int getNumberOfGenerations(){
		return numberOfGenerations;
	}
	
	// Create a given new generation
	public void createGivenNewGeneration(int numGen) {
		generation[numGen] = new Generation(numberOfGenes, lengthOfOneGene, valueSystem, numberOfIndividuals);
	}

	// Initialize randomly a given generation
	public void initializeGivenGeneration(int numGen) {
		generation[numGen].initializeRandomlyGeneration();
	}

	// Initialize a given generation with only 0
	public void initializeGivenEmptyGeneration(int numGen) {
		generation[numGen].initializeEmptyGeneration();
	}

	// Print a given generation
	public void printGivenGeneration(int numGen) {
		generation[numGen].printGeneration();
	}
	
	// Print best individual in a given generation
	public void printBestIndividualInGivenGeneration(int numGen) {
		generation[numGen].printBestIndividual();
	}

	// Get a given generation
	public Generation getGivenGeneration(int numGen) {
		return generation[numGen];
	}

	// Sort ascendingly individuals in generation
	public void doSortAscendinglyGeneration(int numGen) {
		generation[numGen].sortAscendinglyIndividuals();
	}
		
	// Sort descendingly individuals in generation
	public void doSortDescendinglyGeneration(int numGen) {
		generation[numGen].sortDescendinglyIndividuals();
	}
		
	// Rank individuals in generation
	public void doRankGeneration(int numGen) {
		generation[numGen].rankIndividuals();	
	}
		
	// Select good individuals of old generation (depending on selection scheme) and 
	// Copy them into next, new generation 
	public void doSelectionForFillingGeneration(int numOldGen, int numNewGen, int selectionScheme) {
		switch(selectionScheme){
		// Selecting randomly individuals 
		case 0: 
		{
			if (verboseLevel > 1) System.out.println("Selecting randomly individuals");
			for (int i=0; i<numberOfIndividuals; i++){
				int j = (int)(Math.random() * numberOfIndividuals);
				Individual indi = generation[numOldGen].getIndividual(j); 
				generation[numNewGen].setIndividual(i, indi);
				if (verboseLevel > 2) System.out.println("Individuum selected:" + j);
				}
			break;
		}
		// Selecting best of two randomly chosen individuals 
		case 1: 
		{
			if (verboseLevel > 1) System.out.println("Selecting best of two randomly chosen individuals");
			for (int i=0; i<numberOfIndividuals; i++){
				int j = (int)(Math.random() * numberOfIndividuals);
				Individual indi1 = generation[numOldGen].getIndividual(j); 
				int k = (int)(Math.random() * numberOfIndividuals);
				Individual indi2 = generation[numOldGen].getIndividual(k);
				if (indi1.getRankingValue() < indi2.getRankingValue()){
					generation[numNewGen].setIndividual(i, indi1);
					if (verboseLevel > 2) System.out.println("Individuum selected:" + j);
				}else{
					generation[numNewGen].setIndividual(i, indi2);
					if (verboseLevel > 2) System.out.println("Individuum selected:" + k);
				}
			}
			break;
		}
		// Selecting individuals if their ranking is smaller as a random number
		// If not take randomly another individual
		case 2: 
		{
			if (verboseLevel > 1) System.out.println("Selecting individuals if their ranking is smaller as a random number");
			for (int i=0; i<numberOfIndividuals; i++){
				Individual indi = generation[numOldGen].getIndividual(i); 
				double ranking = indi.getRankingValue();
				if (Math.random() > ranking){
					generation[numNewGen].setIndividual(i, indi);
					if (verboseLevel > 2) System.out.println("Individuum selected:" + i);
				}
				else{
					int j = (int)(Math.random()*numberOfIndividuals);
					indi = generation[numOldGen].getIndividual(j);
					if (verboseLevel > 2) System.out.println("Individuum selected:" + j);
					generation[numNewGen].setIndividual(i, indi);
				}
			}
			break;
		}
		// Selecting individuals if their ranking is smaller as a random number
		// If not, start again selecting individuals if their ranking is smaller as a random number
		// If not, take best individual
		case 3: 
		{
			if (verboseLevel > 1) System.out.println("Selecting individuals if their ranking is smaller as a random number. If not, start again");
			for (int i=0; i<numberOfIndividuals; i++){
				Individual indi = generation[numOldGen].getIndividual(i); 
				double ranking = indi.getRankingValue();
				if (Math.random() > ranking){
					generation[numNewGen].setIndividual(i, indi);
					if (verboseLevel > 2) System.out.println("Individuum selected:" + i);
				}
				else{
					for (int j=0; j<numberOfIndividuals; j++){
						indi = generation[numOldGen].getIndividual(j);
						ranking = indi.getRankingValue();
						if (Math.random() > ranking){
							generation[numNewGen].setIndividual(i, indi);
							if (verboseLevel > 2) System.out.println("Individuum selected:" + j);
							break;
						}
						indi = generation[numOldGen].getIndividual(0);
						if (verboseLevel > 2) System.out.println("Individuum selected:" + 0);
						generation[numNewGen].setIndividual(i, indi);
					}
				}
			}
			break;
		}
		}
		if (verboseLevel > 2) System.out.println("Generation "+numNewGen);
		if (verboseLevel > 3) generation[numNewGen].printGeneration();
	}

	// Crossover chromosomes in individuals with a given probability
	public void doCrossover(int numGen, int numIndi1, int numIndi2, int crossoverScheme, double probabilityCrossover, double numberOfCrossovers) {
		
		switch(crossoverScheme){
		// Crossover (simple) individuals randomly
		case 0: 
		{
			for (int i=0; i<numberOfCrossovers; i++){
				generation[numGen].doSimpleCrossover(probabilityCrossover); 
			}
			break;
		}
		// Crossover (double) individuals randomly
		case 1: 
		{
			for (int i=0; i<numberOfCrossovers; i++){
				generation[numGen].doDoubleCrossover(probabilityCrossover);
			}
			break;
		}
		// Crossover (simple) two given individuals
		case 2: 
		{
			if (verboseLevel > 2) System.out.println("Crossover (simple) two given individuals: "+numIndi1+"  "+numIndi2);
			Individual indi1 = generation[numGen].getIndividual(numIndi1); 
			Individual indi2 = generation[numGen].getIndividual(numIndi2); 
			generation[numGen].doSingleCrossoverOfTwoIndividuals(probabilityCrossover, indi1, indi2); 
			break;
		}
		// Crossover (double) two given individuals
		case 3: 
		{
			if (verboseLevel > 2) System.out.println("Crossover (double) two given individuals: "+numIndi1+"  "+numIndi2);
			Individual indi1 = generation[numGen].getIndividual(numIndi1); 
			Individual indi2 = generation[numGen].getIndividual(numIndi2); 
			generation[numGen].doDoubleCrossoverOfTwoIndividuals(probabilityCrossover, indi1, indi2); 
			break;
		}
		// Crossover (single) the first and the last individual, the second and the second last individual ...
		case 4: 
		{
			// As crossover needs 2 individuals, do crossover for half the number of individuals
			for (int i=0; i<(numberOfIndividuals/2); i++){
				if (verboseLevel > 2) System.out.println("Crossover (simple) two given individuals: "+i+"  "+(numberOfIndividuals-i-1));
				Individual indi1 = generation[numGen].getIndividual(i); 
				Individual indi2 = generation[numGen].getIndividual(numberOfIndividuals-i-1); 
				generation[numGen].doSingleCrossoverOfTwoIndividuals(probabilityCrossover, indi1, indi2); 
			}
			break;
		}
		// Crossover (double) the first and the last individual, the second and the second last individual ...
		case 5: 
		{
			// As crossover needs 2 individuals, do crossover for half the number of individuals
			for (int i=0; i<(numberOfIndividuals/2); i++){
				if (verboseLevel > 2) System.out.println("Crossover (double) two given individuals: "+i+"  "+(numberOfIndividuals-i-1));
				Individual indi1 = generation[numGen].getIndividual(i); 
				Individual indi2 = generation[numGen].getIndividual(numberOfIndividuals-i-1); 
				generation[numGen].doDoubleCrossoverOfTwoIndividuals(probabilityCrossover, indi1, indi2); 
			}
			break;
		}
		}
	}

	// Mutate chromosomes in individuals with a given probability
	public void doMutation(int numGen, int mutationScheme, double probabilityMutation, double numberOfMutations) {
		
		switch(mutationScheme){
		// Mutate one position in chromosome of individual
		case 0: 
		{
			for (int i=0; i<numberOfMutations; i++){
				generation[numGen].doMutationOnePosition(probabilityMutation);
			}
			break;
		}
		// Mutate one gene in chromosome of individual
		case 1: 
		{
			for (int i=0; i<numberOfMutations; i++){
				generation[numGen].doMutationOneGene(probabilityMutation); 
			}
			break;
		}
		}
	}

	

	// main method for running the GA
	public static void main(String[] args)	
	{
		// *** CHANGE THE PARAMETERS OF THE GA *********************************************************************************************************
		final int NUMBEROFGENES = 5;        // Number of genes in a chromosome; must be >=1
		final int LENGTHOFGENES = 8;         // Length (number of positions) of one gene; must be >=1; default 1 for binary setting
		final int VALUESYSTEM   = 2;         // Value of number system of which the chromosome string exists; must be >=2: e.g. 2 = binary, 10 = decimal
		final int NUMBEROFINDIVIDUALS = 200;  // Number of individuals in a generation; must be >=1
		final int NUMBEROFGENERATIONS = 100; // Maximum number of generations; must be >=1
		
		final int SELECTIONSCHEME = 1;  // Selection scheme to be applied {0,1,2,3}
		// 0: Selecting randomly individuals
		// 1: Selecting best of two randomly chosen individuals
		// 2: Selecting individuals if their ranking is smaller as a random number. 
		//    If not take randomly another individual.
		// 3: Selecting individuals if their ranking is smaller as a random number
		//    If not, start again selecting individuals if their ranking is smaller as a random number. 
		//    If not, take best individual. 
		
		final double CROSSOVERPROBABILITY = 0.5; // Probability of crossover [0.0,1.0]
		final int    CROSSOVERSCHEME = 1;        // Crossover scheme to be applied {0,1,2,3,4,5}
		// 0: Crossover (simple) individuals randomly
		// 1: Crossover (double) individuals randomly
		// 2: Crossover (simple) two given individuals
		// 3: Crossover (double) two given individuals
		// 4: Crossover (single) the first and the last individual, the second and the second last individual...
		// 5: Crossover (double) the first and the last individual, the second and the second last individual ...
		
		final double MUTATIONPROBABILITY = 0.1; // Probability of mutation [0.0,1.0]
		final int    MUTATIONSCHEME = 0;        // Mutation scheme to be applied {0,1}
		// 0: Mutate one position in chromosome of individual
		// 1: Mutate one gene in chromosome of individual 

		// Verbose level from 0 (no output), 1 (few output), 2 (more output) to 3 (full output)
		verboseLevel = 0;
		
		
		// *** MAIN PROGRAM *****************************************************************************************************************************
		// Initialize the population object
		Population population = new Population(NUMBEROFGENES, LENGTHOFGENES, VALUESYSTEM, NUMBEROFINDIVIDUALS, NUMBEROFGENERATIONS);
		
		// Create and initialize a new random initial generation
		int countGeneration = 0;
		if (verboseLevel > 0) System.out.println("--> Create and initialize new random generation " + countGeneration);
		population.createGivenNewGeneration(countGeneration);
		population.initializeGivenGeneration(countGeneration);		
		if (verboseLevel > 2) population.printGivenGeneration(countGeneration);
		
		// Sort population of initial generation
		if (verboseLevel > 0) System.out.println("--> Sort and rank generation " + countGeneration);
		population.doSortAscendinglyGeneration(countGeneration);
		population.doRankGeneration(countGeneration);
		if (verboseLevel > 1) population.printGivenGeneration(countGeneration);

		// Print best individual of the population of initial generation
		System.out.print("Generation = "+countGeneration+"  \t");
		population.printBestIndividualInGivenGeneration(countGeneration);

		// Iterate over all generations
		while (countGeneration < (NUMBEROFGENERATIONS-1)){
			countGeneration++;
			
			// Create and initialize next new empty generation
			if (verboseLevel > 0) System.out.println("--> Create and initialize next new empty generation " + countGeneration);
			population.createGivenNewGeneration(countGeneration);
			population.initializeGivenEmptyGeneration(countGeneration);
			if (verboseLevel > 2) population.printGivenGeneration(countGeneration);
			
			// Select individuals of old generation and fill new generation
			if (verboseLevel > 0) System.out.println("--> Select indiviuals of generation " + (countGeneration-1) + " and fill generation " + countGeneration);
			population.doSelectionForFillingGeneration((countGeneration-1), countGeneration, SELECTIONSCHEME);
			if (verboseLevel > 2) population.printGivenGeneration(countGeneration);
			
			// Preliminarily sort and rank individuals of population of new generation
			if (verboseLevel > 0) System.out.println("--> Sort and rank generation " + countGeneration);
			population.doSortAscendinglyGeneration(countGeneration);
			population.doRankGeneration(countGeneration);
			if (verboseLevel > 1) population.printGivenGeneration(countGeneration);
						
			// Crossover certain individuals of population of new generation
			if (verboseLevel > 0) System.out.println("--> Crossover generation " + countGeneration);
			int numIndi1 = (int) (Math.random() * NUMBEROFINDIVIDUALS); // Only for CROSSOVERSCHEME 2 and 3
			int numIndi2 = (int) (Math.random() * NUMBEROFINDIVIDUALS); // Only for CROSSOVERSCHEME 2 and 3
			population.doCrossover(countGeneration, numIndi1, numIndi2, CROSSOVERSCHEME, CROSSOVERPROBABILITY, NUMBEROFINDIVIDUALS);
			if (verboseLevel > 2) population.printGivenGeneration(countGeneration);
			
			// Mutate certain individuals of population of new generation
			if (verboseLevel > 0) System.out.println("--> Mutate generation " + countGeneration);
			population.doMutation(countGeneration, MUTATIONSCHEME, MUTATIONPROBABILITY, NUMBEROFINDIVIDUALS);
			if (verboseLevel > 2) population.printGivenGeneration(countGeneration);
			
			// Finally sort and rank individuals of population of new generation
			if (verboseLevel > 0) System.out.println("--> Sort and rank generation " + countGeneration);
			population.doSortAscendinglyGeneration(countGeneration);
			population.doRankGeneration(countGeneration);
			if (verboseLevel > 1) population.printGivenGeneration(countGeneration);

			// Print best individual of the population of initial generation
			System.out.print("Generation = "+countGeneration+"  \t");
			population.printBestIndividualInGivenGeneration(countGeneration);
		}

	}
}
