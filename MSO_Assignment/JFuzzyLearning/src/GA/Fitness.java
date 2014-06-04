package GA;
/** @project: genetic algorithm
 * @date: 2012-02-25
 * @author: Rolf Dornberger
 * @copyright: 2006, FHNW
 * @description: Fitness Class is the static class for computing the fitness
 */

import java.io.*;
import java.text.DecimalFormat;

public class Fitness {
	private static int verboseLevel = 0;  // Verbose level from 0 (no output) to 3 (full output)

	private static int numberOfGenes;    // Number of genes in a chromosome
	private static int lengthOfOneGene;  // Length (number of positions) of one gene
	private static int sizeOfChromosome; // Length (number of positions) of one chromosome = numberOfGenes*lengthOfOneGene
	private static int valueSystem;      // Value system of which the chromosome exists: e.g. 2 = binary, 10 = decimal
	
	// Range where fitness function is evaluated
	private static double xMin = -10.0;
	private static double xMax =  10.0;
	
	// Define file writer
	private static FileWriter fw = null;
	private static boolean printDataInFile = false;

	
	public static double computeFitness(Individual indi){
		
		// Define variables
		double value = 0;    // The range of value is from 0 to (valueSystem^sizeOfChromosom)
		double xRange;       // Range between xMax and xMin
		double x;            // The range of x is from xMin to xMax 
		double fitness;      // Fitness is the computed fitness function.
		
		// Get data and chromosome of individual
		numberOfGenes   = indi.getNumberOfGenes();
		lengthOfOneGene = indi.getLengthOfGene();
		sizeOfChromosome = numberOfGenes*lengthOfOneGene;
		valueSystem     = indi.getValueSystem();
		int[] chromosome = new int[sizeOfChromosome];
		chromosome       = indi.getChromosom();
		
		// Convert int-values of the genes in the chromosomes in a double-value for x
		// The range of value is from 0 to (valueSystem^sizeOfChromosom)
		for (int i=0; i<sizeOfChromosome; i++){
			if (verboseLevel > 1) System.out.println("value = " + value);
			value += Math.pow(valueSystem,(sizeOfChromosome-i-1)) * chromosome[i];
		}
		if (verboseLevel > 0) System.out.print("value = " + value+ " \t");
		
		// Scale range that x is between xMin and xMax
		xRange = xMax - xMin; // Range between xMax and xMin
		x = value / (Math.pow(valueSystem,sizeOfChromosome)-1) * xRange + xMin;
		
		// Print x value
		DecimalFormat myFormatter = new DecimalFormat("##0.000000"); // Show exactly 6 fraction digits
		if (verboseLevel > 0) System.out.print("x = " + myFormatter.format(x) + " \t");
		
		// *** COMPUTE FITNESS FUNCTIONS *********************************************************************************************************************
		// *** Compute fitness = y = (x-3)^2-1 
		fitness = Math.pow(x-3.0,2)-1.0;
		
		// *** Compute fitness = y = (0.5*x-1)^4-10*(0.5*x-1.1)^2+10 
		//fitness = Math.pow(0.5*x-1.0,4)-10.0*Math.pow(0.5*x-1.1,2)+10.0;
		// ************************************************************************************************************************
		
		// Print fitness value
		if (verboseLevel > 0) System.out.print("Fitness = y = " + myFormatter.format(fitness) + " \t");
		
		// Print data in file?
		if (printDataInFile){
			try {
				fw.write(myFormatter.format(x) + " \t" + myFormatter.format(fitness) + "  \t");
				for (int i=0; i<sizeOfChromosome; i++){
					fw.write(""+chromosome[i]);
				}
				fw.write("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}

		return fitness;
	}
	
	// TEST METHOD
	// main method for testing Fitness function
	public static void main(String[] args){
		
		final int NUMBEROFGENES = 15;
		final int LENGTHOFGENES = 1;  // default 1 for binary setting
		final int VALUESYSTEM   = 2;  // default 2 = binary
		
		int sizeOfChrom = NUMBEROFGENES*LENGTHOFGENES;
		int[] feld = new int[sizeOfChrom];
		int[] chromosome = new int[sizeOfChrom];
		Individual indi = new Individual(NUMBEROFGENES, LENGTHOFGENES, VALUESYSTEM);
		
		// Allow data print in file and open file dataxy.txt
		printDataInFile = true;
		try{
			fw = new FileWriter("dataxy.txt");
			fw.write("x-Wert  \t\t y-Wert  \t\t chromosom\n");
		}catch(IOException e){
			System.out.println(e.toString());
		}

		// Create all possible chromosomes from 000000 to 111111 in the full size
		verboseLevel = 1;
		feld = new int[sizeOfChrom];
		chromosome = new int[sizeOfChrom];
		indi = new Individual(NUMBEROFGENES, LENGTHOFGENES, VALUESYSTEM);
		System.out.println("Chromosom data");
		indi.initializeEmptyIndividual();
		
		for (long i=0; i<Math.pow(VALUESYSTEM,sizeOfChrom); i++){
			long j = i;
			int pos = 0;
			while (j > 0){
				feld[pos++] = (int)(j%VALUESYSTEM);
				j /= VALUESYSTEM;
			}
			for (int k=0; k<sizeOfChrom; k++){
				chromosome[k] = feld[sizeOfChrom-k-1];
			}
			indi.setChromosom(chromosome);
			indi.computeFitnessValue();
			indi.printIndividualShort();
		}
		
		// Close file
		try{
			fw.close();
		}catch(IOException e){
			System.out.println(e.toString());
		}
	}
}
