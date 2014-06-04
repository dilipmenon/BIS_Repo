package GA;
/** @project: genetic algorithm
 * @date: 2012-02-25
 * @author: Rolf Dornberger
 * @copyright: 2006, FHNW
 * @description: Individual Class is the class for defining the individuals (as objects)
 */

import java.text.*;

public class Individual {
	
	private static int verboseLevel = 0;  // Verbose level from 0 (no output) to 3 (full output)

	private int numberOfGenes;         // Number of genes in a chromosome
	private int lengthOfOneGene;       // Length (number of positions) of one gene
	private int sizeOfChromosome;      // Length (number of positions) of one chromosome = numberOfGenes*lengthOfOneGene
	private int valueSystem;           // Value of number system of which the chromosome exists: e.g. 2 = binary, 10 = decimal
	private int[] chromosom;           // Chromosome given as array of integer 
	protected double fitnessValue = 0.0; // (Initialization of) Fitness of the chromosome computed by the fitness function
	protected double rankingValue = 0.0; // (Initialization of) Normalized fitness value between 0 and 1
	 
	
	// Create individual with a chromosome as array of integer values with the size of numberOfGenes * lengthOfGenes
	public Individual(int numOfGene, int lenOfGene, int valSystem) {
		numberOfGenes   = numOfGene;
		lengthOfOneGene = lenOfGene;
		sizeOfChromosome = numOfGene*lenOfGene;
		valueSystem     = valSystem;
		chromosom       = new int[sizeOfChromosome];
	}

	// Initialize an empty chromosome in the individual with the value 0
	public void initializeEmptyIndividual() {
		for (int i=0; i<sizeOfChromosome; i++){
			chromosom[i] = 0;
		}
	}
	
	// Initialize randomly the chromosome in the individual with values according to the integer value system
	public void initializeRandomlyIndividual() {
		for (int i=0; i<sizeOfChromosome; i++){
			chromosom[i] = (int) (Math.random() * valueSystem);
		}
		/*Slightly adapted code by DME, 26.05.2014, suppress direct calculation of 
		 * Fitness - until feasible solution is confirmed*/
		//fitnessValue = Fitness.computeFitness(this);
	}

	// Get chromosome
	public int[] getChromosom() {
		return chromosom;
	}

	// Set chromosome
	public void setChromosom(int[] chromo) {
		chromosom = (int[]) chromo.clone();
	}

	// Get part of chromosome from startPostion to endPosition
	public int[] getPartOfChromosom(int startPosition, int endPosition){
		if ((startPosition<sizeOfChromosome) && (endPosition<sizeOfChromosome)){
			int partChromosomSize = endPosition - startPosition + 1;
			int[] partChromosom = new int[partChromosomSize];
			int pos = 0;
			for (int i=startPosition; i<=endPosition; i++){
				partChromosom[pos] = chromosom[i];
				pos++;
			}
			return partChromosom;
		} else {
			return null;
		}	
	}

	// Exchange (set) part of chromosome from startPostion with chromosomPart
	public void setPartOfChromosom(int startPosition, int[] chromosomPart){
		int partChromosomSize = chromosomPart.length;
		if ((startPosition<sizeOfChromosome) && ((startPosition+partChromosomSize-1)<sizeOfChromosome)){
			int pos = 0;
			for (int i=startPosition; i<partChromosomSize; i++){
				chromosom[i] = chromosomPart[pos];
				pos++;
			}
		}
	}

	// Mutate one position on chromosome with given probability
	// The position is chosen randomly
	// The new value is dependent on the value system
	public void mutateOnePosition(double probabilityOfMutation){
		if (Math.random() < probabilityOfMutation){
			int position = (int) (Math.random() * sizeOfChromosome);
			int oldValue = chromosom[position];
			int newValue = valueSystem - 1 - oldValue;
			chromosom[position] = newValue;
			/*Slightly adapted code by DME, 26.05.2014, suppress direct calculation of 
			 * Fitness - until feasible solution is confirmed*/
			//fitnessValue = Fitness.computeFitness(this);
			
		}
	}

	// Mutate one gene on chromosome with given probability
	// The position is chosen randomly
	// The new value is dependent on the value system
	public void mutateOneGene(double probabilityOfMutation){
		if (Math.random() < probabilityOfMutation){
			int position = (int) (Math.random() * numberOfGenes);
			int oldValue, newValue;
			for (int i=0; i<lengthOfOneGene; i++){
				oldValue = chromosom[position*lengthOfOneGene + i];
				newValue = valueSystem - 1 - oldValue;
				chromosom[position*lengthOfOneGene + i] = newValue;
			}
			/*Slightly adapted code by DME, 26.05.2014, suppress direct calculation of 
			 * Fitness - until feasible solution is confirmed*/
			//fitnessValue = Fitness.computeFitness(this);
		}
	}

	// Get fitness value
	public double getFitnessValue() {
		return fitnessValue;
	}

	// Set fitness value
	public void setFitnessValue(double fitVal) {
		fitnessValue = fitVal;
	}

	// Re-compute fitness value
	public double computeFitnessValue() {
		fitnessValue = Fitness.computeFitness(this);
		return fitnessValue;
	}

	// Get ranking value
	public double getRankingValue() {
		return rankingValue;
	}

	// Set ranking value
	public void setRankingValue(double rank) {
		rankingValue = rank;
	}

	// Get number of genes
	public int getNumberOfGenes() {
		return numberOfGenes;
	}
	
	// Get length of one gene
	public int getLengthOfGene() {
		return lengthOfOneGene;
	}

	// Get value system
	public int getValueSystem() {
		return valueSystem;
	}

	// Print all data of individual
	public void printIndividualLong() {
		System.out.print("Chromosom = \t\t");
		int pos = 0;
		for (int i=0; i<numberOfGenes; i++){
			for (int j=0; j<lengthOfOneGene; j++){
				System.out.print(chromosom[pos] + " ");
				pos++;
			}
			System.out.print(" ");
		}
		System.out.println("");
		System.out.println("Fitness = \t\t" + fitnessValue);
		System.out.println("Ranking = \t\t" + rankingValue);
		System.out.println("Number of genes = \t" + numberOfGenes);
		System.out.println("Length of one gene = \t" + lengthOfOneGene);
		System.out.println("Size of chromosom = \t" + sizeOfChromosome);
		System.out.println("Value system = \t\t" + valueSystem);
	}
	
	// Print chromosome, fitness and ranking value of chromosome
	public void printIndividualShort() {
		int pos = 0;
		for (int i=0; i<numberOfGenes; i++){
			for (int j=0; j<lengthOfOneGene; j++){
				System.out.print(chromosom[pos] + " ");
				pos++;
			}
			System.out.print(" ");
		}
		
		// Format the number printing
		DecimalFormat myFormatter = new DecimalFormat("0.000000"); // Show exactly 6 fraction and 1 integer digits
//		DecimalFormat myFormatter = new DecimalFormat("0.00000000"); // Show exactly 8 fraction and 1 integer digits
		//DecimalFormat myFormatter = new DecimalFormat("###.########"); // Show 8 fraction and 3 integer digits
		//DecimalFormat myFormatter = new DecimalFormat("###.####E0"); // Use scientific number format
		if (verboseLevel > 0) 
			System.out.println("\t --> \t" + myFormatter.format(fitnessValue) + "\t --> \t" + myFormatter.format(rankingValue));
		else
			System.out.println("\t --> \t" + myFormatter.format(fitnessValue));
	}
	
	
	// TEST METHOD
	// main method for testing classes Individual and Fitness
	public static void main(String[] args)	
	{
		final int NUMBEROFGENES = 10;
		final int LENGTHOFGENES = 1;  // default 1 for binary setting
		final int VALUESYSTEM   = 2;  // default 2 = binary

		double fitness;

		// Create two individuals and print them in long and short form
		System.out.println("Individual 1: Long data");
		Individual indi1 = new Individual(NUMBEROFGENES, LENGTHOFGENES, VALUESYSTEM);
		indi1.initializeRandomlyIndividual();
		indi1.printIndividualLong();
		System.out.println("\nIndividual 2: Short data");
		Individual indi2 = new Individual(NUMBEROFGENES, LENGTHOFGENES, VALUESYSTEM);
		indi2.initializeRandomlyIndividual();
		indi2.printIndividualShort();
		
		// Get a part (40%) of the chromosome of individual 1 and print it
		System.out.println("\nIndividual 1: First 40% of chromosom");
		indi1.printIndividualShort();
		int partLength = (int) (0.4*NUMBEROFGENES*LENGTHOFGENES); 
		int[] partChromosom = new int [partLength];
		for (int i=0; i<partChromosom.length; i++){
			//System.out.print(partChromosom[i]);	
		}
		partChromosom = indi1.getPartOfChromosom(0, partLength-1);
		for (int i=0; i<partChromosom.length; i++){
			System.out.print(partChromosom[i]);	
		}
		System.out.println("");
		
		// Set a part (40%) of the chromosome of individual 1 to the chromosom of individual 2
		System.out.println("\nIndividual 2: Getting first 40% of chromosom of individual 1");
		indi2.setPartOfChromosom(0, partChromosom);
		indi2.printIndividualShort();
		
		// Mutation
		System.out.println("\nIndividual 1: Mutate randomly one position");
		indi1.printIndividualShort();
		indi1.mutateOnePosition(1.0);
		indi1.printIndividualShort();
		
		// Mutation
		System.out.println("\nIndividual 1: Mutate randomly one gene");
		indi1.printIndividualShort();
		indi1.mutateOneGene(1.0);
		indi1.printIndividualShort();
		
		// Get fitness print all data
		System.out.println("\nIndividual 1: Fitness and data");
		fitness = indi1.getFitnessValue();
		System.out.println("Fitness = " + fitness);
		indi1.printIndividualShort();
		indi1.printIndividualLong();
	
	}
}
