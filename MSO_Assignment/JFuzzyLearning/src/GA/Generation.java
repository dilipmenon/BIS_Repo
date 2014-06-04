package GA;

/** @project: genetic algorithm
 * @date: 2012-02-25
 * @author: Rolf Dornberger
 * @copyright: 2006, FHNW
 * @description: Generation Class is the class for defining the generations of individuals
 */

public class Generation {
	
	protected static int verboseLevel = 0;  // Verbose level from 0 (no output) to 3 (full output)

	protected int numberOfGenes;            // Number of genes in a chromosome
	private int lengthOfOneGene;          // Length (number of positions) of one gene
	protected int valueSystem;              // Value of number system of which the chromosome exists: e.g. 2 = binary, 10 = decimal
	protected int numberOfIndividuals;      // Number of individuals in a generation
	protected Individual[] individual;      // Generation is given as an array of individual objects
	 
	// Create generation with a set of objects of the class individual
	public Generation(int numOfGene, int lenOfGene, int valSystem, int numIndividuals) {
		numberOfGenes       = numOfGene;
		lengthOfOneGene     = lenOfGene;
		valueSystem         = valSystem;
		numberOfIndividuals = numIndividuals;
		individual          = new Individual[numberOfIndividuals];
	}
	protected Generation(int numOfGene, int lenOfGene, int valSystem)
	{
		numberOfGenes       = numOfGene;
		lengthOfOneGene     = lenOfGene;
		valueSystem         = valSystem;
	}

	// Initialize randomly the chromosome with values according to the integer number system valueSystem
	public void initializeRandomlyGeneration() {
		for (int i=0; i<numberOfIndividuals; i++){
			individual[i] = new Individual(numberOfGenes, lengthOfOneGene, valueSystem);
			individual[i].initializeRandomlyIndividual();
		}
	}

	// Initialize the chromosome with the value 0
	public void initializeEmptyGeneration() {
		for (int i=0; i<numberOfIndividuals; i++){
			individual[i] = new Individual(numberOfGenes, lengthOfOneGene, valueSystem);
			individual[i].initializeEmptyIndividual();
		}
	}

	// Get number of individuals
	public int getNumberOfIndividuals() {
		return numberOfIndividuals;
	}

	// Get individual
	public Individual getIndividual(int numIndi) {
		if (numIndi <= numberOfIndividuals){
			return individual[numIndi];			
		}
		else{
			return null;
		}
	}

	// Set individual (by copying chromosome and fitness value)
	public void setIndividual(int numIndi, Individual indi) {
		if (numIndi <= numberOfIndividuals){
			individual[numIndi].setChromosom(indi.getChromosom());
			individual[numIndi].setFitnessValue(indi.getFitnessValue());
		}
	}

	// Print all data of generation
	public void printGeneration() {
		if (verboseLevel > 1) System.out.println("Number of Individuals = " + numberOfIndividuals);
		for (int i=0; i<numberOfIndividuals; i++){
			System.out.print("#" + i + " \t");
			individual[i].printIndividualShort();
		}
	}

	// Print best individual of generation
	public void printBestIndividual() {
		double value = individual[0].getFitnessValue();
		int pos = 0;
		for (int i=1; i<numberOfIndividuals; i++){
			if (individual[i].getFitnessValue() < value){
				value = individual[i].getFitnessValue();
				pos = i;
			}
		}
		System.out.print("Best: \t");
		individual[pos].printIndividualShort();
	}

	// Sort ascendingly all individuals according to their fitness values
	public void sortAscendinglyIndividuals() {
		
		Individual temp = new Individual(numberOfGenes, lengthOfOneGene, valueSystem);
		double value;
		int pos;
		
		// Search over all individuals in order to find the smallest ones
		// If found store it at the top of the array and 
		// continue the search in the rest of the array for the second smallest individual and so on
		for (int i=0; i<numberOfIndividuals; i++){
			value = individual[i].getFitnessValue();
			pos = i;
			
			// Search from individual i t the rest the individual with the smallest fitness and store its position pos
			for (int j=i; j<numberOfIndividuals; j++){
				if (value > individual[j].getFitnessValue()){
					value = individual[j].getFitnessValue();
					pos = j;
				}
			}
			// Smallest fitness found at position pos -> exchange individuals at position i and pos
			if (i != pos){
				temp = individual[i];
				individual[i] = individual[pos];
				individual[pos] = temp;
				individual[i].computeFitnessValue();
				individual[pos].computeFitnessValue();				
			}
		}
	}
	// Sort descendingly all individuals according to their fitness values
	public void sortDescendinglyIndividuals() {
		
		Individual temp = new Individual(numberOfGenes, lengthOfOneGene, valueSystem);
		double value;
		int pos;
		
		// Search over all individuals in order to find the smallest ones
		// If found store it at the top of the array and 
		// continue the search in the rest of the array for the second smallest individual and so on
		for (int i=0; i<numberOfIndividuals; i++){
			value = individual[i].getFitnessValue();
			pos = i;
			
			// Search from individual i t the rest the individual with the smallest fitness and store its position pos
			for (int j=i; j<numberOfIndividuals; j++){
				if (value < individual[j].getFitnessValue()){
					value = individual[j].getFitnessValue();
					pos = j;
				}
			}
			// Smallest fitness found at position pos -> exchange individuals at position i and pos
			if (i != pos){
				temp = individual[i];
				individual[i] = individual[pos];
				individual[pos] = temp;
				individual[i].computeFitnessValue();
				individual[pos].computeFitnessValue();				
			}
		}
	}
	
	// Rank all individuals according to their fitness values
	public void rankIndividuals() {
				
		double value, minValue, maxValue;
		
		/*
		// Get sum of all ranking values
		double sum = 0.0;
		for (int i=0; i<numberOfIndividuals; i++){
			sum += individual[i].getRankingValue();
		}
		
		// Divide all ranking values with the sum of all ranking values
		// Division only necessary if abs(sum) > epsilon
		double epsilon1 = 1.0e-8;
		if (Math.abs(sum) > epsilon1){
			for (int i=0; i<numberOfIndividuals; i++){
				value = individual[i].getRankingValue() / sum;
				individual[i].setRankingValue(value);
			}			
			if (verboseLevel > 2) System.out.println("Divide all ranking values with the sum of all ranking values: sum = " + sum);
			if (verboseLevel > 1) this.printGeneration();
		}
		*/

		/*
		// Sum up individually all ranking values to the next individual
		// this.sortAscendinglyIndividuals();
		value = 0.0;
		for (int i=0; i<numberOfIndividuals; i++){
			value += individual[i].getRankingValue();
			individual[i].setRankingValue(value);
		}			
		if (verboseLevel > 2) System.out.println("Sum up individually all ranking values to the next individual");
		if (verboseLevel > 1) this.printGeneration();
		*/

		// Find minimum fitness value
		minValue = individual[0].getFitnessValue();
		for (int i=1; i<numberOfIndividuals; i++){
			value = individual[i].getFitnessValue();
			if (value < minValue){
				minValue = value;
			}
		}
		
		// Copy fitness values to ranking values and 
		// Subtract minimum fitness value from all values
		// Consequence: Lowest ranking value is 0.0
		if (verboseLevel > 2) System.out.println("Copy fitness values to ranking values, find minimum ranking value and add it to all ranking values: minValue = " + minValue);
		for (int i=0; i<numberOfIndividuals; i++){
			value = individual[i].getFitnessValue() - minValue;
			individual[i].setRankingValue(value);
		}
		if (verboseLevel > 1) this.printGeneration();
		
		// Find maximum ranking value
		maxValue = individual[0].getRankingValue();
		for (int i=1; i<numberOfIndividuals; i++){
			value = individual[i].getRankingValue();
			if (value > maxValue){
				maxValue = value;
			}
		}
		
		// Normalize all ranking values with maximum ranking value
		// Consequence: Lowest ranking value stays at 0.0 and highest at 1.0
		// Division only necessary if maxValue > epsilon
		double epsilon2 = 1.0e-8;
		if (Math.abs(maxValue) > epsilon2){
			if (verboseLevel > 2) System.out.println("Normalize all ranking values with maximum ranking value: maxValue = " + maxValue);
			for (int i=0; i<numberOfIndividuals; i++){
				value = individual[i].getRankingValue() / maxValue;
				individual[i].setRankingValue(value);
			}
			if (verboseLevel > 1) this.printGeneration();
		}

		// Stretch range of all ranking values at the higher end 
		// Consequence: Lower ranking values stay nearer at 0.0 and higher ranking values nearer at 1.0
		double exponent = 0.5;
		if (verboseLevel > 2) System.out.println("Stretch range of all ranking values: exponent = " + exponent);
		for (int i=0; i<numberOfIndividuals; i++){
			value = Math.pow(individual[i].getRankingValue(), exponent);
			individual[i].setRankingValue(value);
		}
		if (verboseLevel > 1) this.printGeneration();
	}
	
	// Crossover the chromosomes of two individuals with given probability
	// The crossover position and the individuals are chosen randomly
	// Only one crossover position
	public void doSimpleCrossover(double probabilityOfCrossover) {
		if (Math.random() < probabilityOfCrossover){
			int[] chromosom1Part, chromosom2Part;
			int numberOfIndividual1 = (int)(Math.random()* numberOfIndividuals);
			int numberOfIndividual2 = (int)(Math.random()* numberOfIndividuals);
			int sizeOfChromosom = numberOfGenes* lengthOfOneGene;
			int positionOfCrossover  = (int)(Math.random()* sizeOfChromosom);
			
			if (verboseLevel > 0) System.out.println("Simple crossover: random Indi1, random Indi2, Pos = " + "  " + numberOfIndividual1 + "  " + numberOfIndividual2 + "  " + positionOfCrossover);
		
			chromosom1Part = individual[numberOfIndividual1].getPartOfChromosom(0, positionOfCrossover);
			chromosom2Part = individual[numberOfIndividual2].getPartOfChromosom(0, positionOfCrossover);
		
			individual[numberOfIndividual1].setPartOfChromosom(0, chromosom2Part);
			individual[numberOfIndividual2].setPartOfChromosom(0, chromosom1Part);
			
			individual[numberOfIndividual1].computeFitnessValue();
			individual[numberOfIndividual2].computeFitnessValue();
		}
	}
	
	// Crossover the chromosomes of two individuals with given probability
	// The crossover positions and the individuals are chosen randomly
	// Two crossover positions
	public void doDoubleCrossover(double probabilityOfCrossover) {
		if (Math.random() < probabilityOfCrossover){
			int[] chromosom1Part, chromosom2Part;
			int numberOfIndividual1 = (int)(Math.random()* numberOfIndividuals);
			int numberOfIndividual2 = (int)(Math.random()* numberOfIndividuals);
			int sizeOfChromosom = numberOfGenes* lengthOfOneGene;
			int positionLeftOfCrossover   = (int)(Math.random()* sizeOfChromosom);
			int positionRightOfCrossover  = (int)(Math.random()* sizeOfChromosom);
			
			if (positionLeftOfCrossover > positionRightOfCrossover){
				int temp = positionRightOfCrossover;
				positionRightOfCrossover = positionLeftOfCrossover;
				positionLeftOfCrossover = temp;
			}
		
			if (verboseLevel > 0) System.out.println("Double crossover: random Indi1, random Indi2, PosLeft, PosRight = " + "  " + numberOfIndividual1 + "  " + numberOfIndividual2 + "  " + positionLeftOfCrossover + "  " + positionRightOfCrossover);

			chromosom1Part = individual[numberOfIndividual1].getPartOfChromosom(positionLeftOfCrossover, positionRightOfCrossover);
			chromosom2Part = individual[numberOfIndividual2].getPartOfChromosom(positionLeftOfCrossover, positionRightOfCrossover);
		
			individual[numberOfIndividual1].setPartOfChromosom(positionLeftOfCrossover, chromosom2Part);
			individual[numberOfIndividual2].setPartOfChromosom(positionLeftOfCrossover, chromosom1Part);
			
			individual[numberOfIndividual1].computeFitnessValue();
			individual[numberOfIndividual2].computeFitnessValue();
		}
	}
		
	// Crossover the chromosomes of two GIVEN individuals with given probability
	// The crossover position is chosen randomly
	// Only one crossover position
	public void doSingleCrossoverOfTwoIndividuals(double probabilityOfCrossover, Individual indi1, Individual indi2) {
		if (Math.random() < probabilityOfCrossover){
			int[] chromosom1Part, chromosom2Part;
			int sizeOfChromosom = numberOfGenes* lengthOfOneGene;
			int positionOfCrossover  = (int)(Math.random()* sizeOfChromosom);
		
			if (verboseLevel > 0) System.out.println("Single crossover, given individuals: PosLeft = " + positionOfCrossover);

			chromosom1Part = indi1.getPartOfChromosom(0, positionOfCrossover);
			chromosom2Part = indi2.getPartOfChromosom(0, positionOfCrossover);
		
			indi1.setPartOfChromosom(0, chromosom2Part);
			indi2.setPartOfChromosom(0, chromosom1Part);
			
			indi1.computeFitnessValue();
			indi2.computeFitnessValue();
		}
	}
	
	// Crossover the chromosomes of two GIVEN individuals with given probability
	// The crossover positions are chosen randomly
	// Two crossover positions
	public void doDoubleCrossoverOfTwoIndividuals(double probabilityOfCrossover, Individual indi1, Individual indi2) {
		if (Math.random() < probabilityOfCrossover){
			int[] chromosom1Part, chromosom2Part;
			int sizeOfChromosome = numberOfGenes* lengthOfOneGene;
			int positionLeftOfCrossover   = (int)(Math.random()* sizeOfChromosome);
			int positionRightOfCrossover  = (int)(Math.random()* sizeOfChromosome);

			if (positionLeftOfCrossover > positionRightOfCrossover){
				int temp = positionRightOfCrossover;
				positionRightOfCrossover = positionLeftOfCrossover;
				positionLeftOfCrossover = temp;
			}

			if (verboseLevel > 0) System.out.println("Double crossover, given individuals: PosLeft, PosRight = " + "  " + positionLeftOfCrossover + "  " + positionRightOfCrossover);

			chromosom1Part = indi1.getPartOfChromosom(positionLeftOfCrossover, positionRightOfCrossover);
			chromosom2Part = indi2.getPartOfChromosom(positionLeftOfCrossover, positionRightOfCrossover);
		
			indi1.setPartOfChromosom(positionLeftOfCrossover, chromosom2Part);
			indi2.setPartOfChromosom(positionLeftOfCrossover, chromosom1Part);

			indi1.computeFitnessValue();
			indi2.computeFitnessValue();
		}
	}
	
	// Mutate one position of the chromosomes of one individual with given probability
	// The mutation position and the individual are chosen randomly
	public void doMutationOnePosition(double probabilityOfMutation) {
		if (Math.random() < probabilityOfMutation){
			int numberOfIndividual = (int)(Math.random()*numberOfIndividuals);
			
			if (verboseLevel > 0) System.out.println("Mutation in individual " + numberOfIndividual);
				
			individual[numberOfIndividual].mutateOnePosition(probabilityOfMutation);
			individual[numberOfIndividual].computeFitnessValue();
		}
	}
		
	// Mutate one gene of the chromosomes of one individual with given probability
	// The mutation position and the individual are chosen randomly
	public void doMutationOneGene(double probabilityOfMutation) {
		if (Math.random() < probabilityOfMutation){
			int numberOfIndividual = (int)(Math.random()*numberOfIndividuals);
				
			if (verboseLevel > 0) System.out.println("Mutation in individual " + numberOfIndividual);

			individual[numberOfIndividual].mutateOneGene(probabilityOfMutation);
			individual[numberOfIndividual].computeFitnessValue();
		}
	}
	
	
	
	// TEST METHOD
	// main method for testing classes Individual and Fitness
	public static void main(String[] args)	
	{
		final int NUMBEROFGENES = 10;
		final int LENGTHOFGENES = 1;  // default 1 for binary setting
		final int VALUESYSTEM   = 2;  // default 2 = binary
		final int NUMBEROFINDIVIDUALS = 20;
		
		verboseLevel = 3;

		// Create one generation of individuals and print it
		System.out.println("Generation 1: ");
		Generation gen1 = new Generation(NUMBEROFGENES, LENGTHOFGENES, VALUESYSTEM, NUMBEROFINDIVIDUALS);
		gen1.initializeRandomlyGeneration();
		gen1.printGeneration();
		
		// Crossover (simple) two randomly chosen individuals in generation and print them
		System.out.println("Generation 1: Crossover (simple)");
		gen1.doSimpleCrossover(1.0);
		gen1.printGeneration();
		
		// Crossover (double) two randomly chosen individuals in generation and print them
		System.out.println("Generation 1: Crossover (double)");
		gen1.doDoubleCrossover(1.0);
		gen1.printGeneration();
		
		// Crossover (simple) two GIVEN individuals in generation and print them
		System.out.println("Generation 1: Crossover (simple) given Individual");
		int numIndi1 = (int)(Math.random()* NUMBEROFINDIVIDUALS);
		Individual indi1 = gen1.getIndividual(numIndi1);
		int numIndi2 = (int)(Math.random()* NUMBEROFINDIVIDUALS);
		Individual indi2 = gen1.getIndividual(numIndi2);
		System.out.println("Individuals: " + numIndi1 + "   " + numIndi2);
		gen1.doDoubleCrossoverOfTwoIndividuals(1.0, indi1, indi2);
		gen1.printGeneration();
		
		// Mutate one randomly chosen individual at one position
		System.out.println("Generation 1: Mutation (position)");
		gen1.doMutationOnePosition(1.0);
		gen1.printGeneration();
		
		// Mutate one randomly chosen individual at one gene
		System.out.println("Generation 1: Mutation (position)");
		gen1.doMutationOneGene(1.0);
		gen1.printGeneration();
		
		// Sort ascendingly individuals in generation and print them
		System.out.println("Generation 1: Sort ascendingly individuals");
		gen1.sortAscendinglyIndividuals();
		gen1.printGeneration();
		
		// Sort decendingly individuals in generation and print them
		System.out.println("Generation 1: Sort decendingly individuals");
		gen1.sortDescendinglyIndividuals();
		gen1.printGeneration();
		
		// Rank individuals in generation and print them
		System.out.println("Generation 1: Rank individuals");
		gen1.rankIndividuals();
		gen1.printGeneration();

		verboseLevel = 1;
		
		// Create one generation of individuals and print it
		System.out.println("Generation 2: ");
		gen1.initializeRandomlyGeneration();
		gen1.printGeneration();

		// Rank individuals in generation and print them
		System.out.println("Generation 2: Rank individuals");
		gen1.rankIndividuals();
		gen1.printGeneration();
		
		// Sort ascendingly individuals in generation and print them
		System.out.println("Generation 2: Sort ascendingly individuals");
		gen1.sortAscendinglyIndividuals();
		gen1.printGeneration();
		
		
	}


}
