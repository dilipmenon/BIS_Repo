package ch.fhnw.msc.bis.mso.jfuzzy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import ch.fhnw.msc.bis.mso.jfuzzy.FlowData.FlowType;

public class FacilityLayoutTester {
	
	static int SITE_HEIGHT = 19;
	static int SITE_WIDTH = 22;
	static ConstructionSite mySite;
	static List<FacilityRelation> facilityRelations;
	final static String referenceDataFile = "..\\JFuzzyLearning\\InputFiles\\FuzzyGeneticRefData.csv";
	private static int verboseLevel = 0;  // Verbose level from 0 (no output) to 3 (full output)
	
	public static void main(String[] args)
	{
		
		
		System.out.println("***Welcome!***");
		System.out.println();
		System.out.println("***Step 1: Calculate Closeness Relations by using JFuzzy Logic***");
		System.out.println();
		evaluateClosenessByFuzyyLogic();
		System.out.println();
		
		System.out.println("***Step 2: Initializing Site, construction building and facilities.***");
		initializeSite(); 
		
		//Place Facilities on Site Randomly
		System.out.println();
		System.out.println("***Step 3: Placing randomly the facilities on Site by using Brute Force***");
		placeFacilitiesByBruteForce();
		
	
		System.out.println();
		mySite.printCurrentLayout();
		
		System.out.println();
		System.out.println("***Step 4: Applying Genetic Algorithm to calculate optimal Solution***");
		runGA();
		
		
		
		
		
		System.out.println();
		System.out.println("***Exiting application.***");	
		System.out.println("***Have a nice day.***");
		
	}

	/*
	 * _Dfaf
	 */
	private static void runGA() {
		// *** CHANGE THE PARAMETERS OF THE GA *********************************************************************************************************
				
				final int NUMBEROFINDIVIDUALS = 2000;  // Number of individuals in a generation; must be >=1
				final int NUMBEROFGENERATIONS = 100; // Maximum number of generations; must be >=1
				
				
					
				final double MUTATIONPROBABILITY = 0.5; // Probability of mutation [0.0,1.0]
				

				// Verbose level from 0 (no output), 1 (few output), 2 (more output) to 3 (full output)
				verboseLevel = 3;
				
				
				// *** MAIN PROGRAM *****************************************************************************************************************************
				// Initialize the population object
				PopulationFacilityLayout population = new PopulationFacilityLayout(NUMBEROFINDIVIDUALS, NUMBEROFGENERATIONS, mySite, facilityRelations);
				
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
				population.printWorstIndividualInGivenGeneration(countGeneration);

				// Iterate over all generations
				
				while (countGeneration < (NUMBEROFGENERATIONS-1)){
					countGeneration++;
					population.createGivenNewGeneration(countGeneration);
					population.initializeGivenEmptyGeneration(countGeneration);
					population.cloneGeneration(countGeneration-1, countGeneration);
					population.replaceWorstIndividualInGivenGeneration(countGeneration, MUTATIONPROBABILITY);
					
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

	private static void evaluateClosenessByFuzyyLogic() {
		facilityRelations = new ArrayList<FacilityRelation>();
	  
	   initializeFacilityRelations(facilityRelations,referenceDataFile);
	  
		
	   
	   for(FacilityRelation fr: facilityRelations){
	   System.out.println("****"+fr.getRelationshipIdentifier()+"****");
	   
	   
	   System.out.println("MATERIAL : " + fr.getMaterialFlow().getCalculatedClosenessValue()+ " ("+ 
	   fr.getMaterialFlow().getReferenceClosenessValue()+ ")");
	   System.out.println("INFORMATION : " + fr.getInformationFlow().getCalculatedClosenessValue()+ " ("+ 
	   	    fr.getInformationFlow().getReferenceClosenessValue()+ ")");
	   System.out.println("EQUIPMENT : " + fr.getEquipmentFlow().getCalculatedClosenessValue()+ " ("+ 
	   	    fr.getEquipmentFlow().getReferenceClosenessValue()+ ")");
	   System.out.println("Average : " + fr.GetCalculatedAverageClosenessValue()+ " ("+ 
	   	    fr.getReferencedAverageClosenessValue()+ ")");
	   System.out.println();
	   
	   
	   }
	   
		
	   /*
	   FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);
		
		//JFuzzyChart.get().chart(fb);

		// Set inputs
		//fb.setVariable("informationFlow", 12);
		//fb.setVariable("weightFactor", 0.5);
		//fb.setVariable("materialFlow", 1200);
		//fb.setVariable("weightFactor", 0.379);
		fb.setVariable("equipmentFlow", 10);
		fb.setVariable("weightFactor", 0.331);


		// Evaluate
		fb.evaluate();

		// Show output variable's chart
		//Variable tip =  fb.getVariable("closenessRelation");
		//JFuzzyChart.get().chart(tip, tip.getDefuzzifier(),true);

		// Print ruleSet
		//System.out.println(fb);
		System.out.println("closenessRelation: " + fb.getVariable("closenessRelation").getValue());
		*/
		
	}

	private static void placeFacilitiesByBruteForce() {
		ArrayList<Building> temporaryCollection = new ArrayList<Building>(mySite.getFacilities().values());
		while (temporaryCollection.size() > 0)
		{
		Building randomFacility = temporaryCollection.get((int)(Math.random()*temporaryCollection.size()));
	
		if(!mySite.placeFacilityRandomly(randomFacility.id))
		{
			System.out.println(randomFacility.id + ": failed to place it on Site without violation");
		}
		else {
			System.out.println(randomFacility.id + ": placed successfully on Site with following coordinates: (" 
		+ randomFacility.getPositionOfUpperLeftCorner().x + "," + randomFacility.getPositionOfUpperLeftCorner().y+")");
			
		}
		temporaryCollection.remove(randomFacility);
		}
		
	}

	private static void initializeSite() {
		mySite = new ConstructionSite(SITE_HEIGHT,SITE_WIDTH);
		///Initialize Constructed Building
		mySite.getConstructedBuildings().put(0,new Building("6",12,6,3,6));
		mySite.getConstructedBuildings().put(1,new Building("6",6,12,9,12));
		
		//Initialize Facility Building Base Data
		mySite.getFacilities().put("1",new Building("1",4,2));
		mySite.getFacilities().put("2",new Building("2",3,4));
		mySite.getFacilities().put("3",new Building("3",5,5));
		mySite.getFacilities().put("4",new Building("4",3,2));
		mySite.getFacilities().put("5",new Building("5",3,1));
		
	}
	
	private static void initializeFacilityRelations(List<FacilityRelation> facilityRelations, String referenceDataFile)
	{
		//List<FacilityRelation> facilityRelations = new ArrayList<FacilityRelation>();
		
		 Map<String, String> fclFiles = new HashMap<>();
	    fclFiles.put(FlowType.INFORMATION.toString(),"..\\JFuzzyLearning\\InputFiles\\InformationFlow_WeightFactor.fcl" );
		fclFiles.put(FlowType.MATERIAL.toString(),"..\\JFuzzyLearning\\InputFiles\\MaterialFlow_WeightFactor.fcl" );
		fclFiles.put(FlowType.EQUIPMENT.toString(),"..\\JFuzzyLearning\\InputFiles\\EquipmentFlow_WeightFactor.fcl" );
		
	    InitializeReferenceDataFromFile(facilityRelations, referenceDataFile);
	    
	    for (String s : fclFiles.keySet()) {
	        ApplyFuzzyLogic(s,fclFiles.get(s),facilityRelations,false);
	    
	    }
	}
	
	private static void ApplyFuzzyLogic(String key, String filename,
			List<FacilityRelation> facilityRelations, boolean showFuzzyGraphs) {
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);
		if(showFuzzyGraphs)
		{
		JFuzzyChart.get().chart(fb);
		}
		for(FacilityRelation fr : facilityRelations)
		{
		FlowData fd = fr.getFlowDataByType(FlowType.valueOf(key));	
		fd.setCalculatedClosenessValue(CalculateFuzzyOutput(fd.getFlowValue(),fd.getWeightFactor(),fb,key));
		}
		
	}

	private static void InitializeReferenceDataFromFile(
			List<FacilityRelation> facilityRelations, String referenceDataFile) {

		 
		  BufferedReader br = null;  
		  String currentLine = "";  
		  String splitBy = ";";  
		  
		  try {  
		  
		   br = new BufferedReader(new FileReader(referenceDataFile));  
		   br.readLine();
		   while ((currentLine = br.readLine()) != null) {  
		   
			   facilityRelations.add(GetFacilityRelationFromRow(currentLine.split(splitBy)));
		     
		  		  
		   }  
		  
		  } catch (FileNotFoundException e) {  
		   e.printStackTrace();  
		  } catch (IOException e) {  
		   e.printStackTrace();  
		  } finally {  
		   if (br != null) {  
		    try {  
		     br.close();  
		    } catch (IOException e) {  
		     e.printStackTrace();  
		    }  
		   }  
		  }  
		  
		  
		 }

	private static FacilityRelation GetFacilityRelationFromRow(String[] split) {
		//Facility;MF;IF;EF;MFW;IFW;EFW;MF_CL;IF_CL;EF_CL
		FacilityRelation fr = new FacilityRelation(Integer.parseInt(split[0].split("-")[0]),Integer.parseInt(split[0].split("-")[1]));
		
		fr.getMaterialFlow().setFlowValue(Double.parseDouble(split[1]));
		fr.getInformationFlow().setFlowValue(Double.parseDouble(split[2]));
		fr.getEquipmentFlow().setFlowValue(Double.parseDouble(split[3]));
		
		fr.getMaterialFlow().setWeightFactor(Double.parseDouble(split[4]));
		fr.getInformationFlow().setWeightFactor(Double.parseDouble(split[5]));
		fr.getEquipmentFlow().setWeightFactor(Double.parseDouble(split[6]));
		
		fr.getMaterialFlow().setReferenceClosenessValue(Double.parseDouble(split[7]));
		fr.getInformationFlow().setReferenceClosenessValue(Double.parseDouble(split[8]));
		fr.getEquipmentFlow().setReferenceClosenessValue(Double.parseDouble(split[9]));
		
		fr.setReferencedAverageClosenessValue(Double.parseDouble(split[10]));
		
		return fr;
				
		
	}
	
	private static double CalculateFuzzyOutput(double flowValue, double weightFactor, FunctionBlock fb, String key)
	{
		double calculatedOutput = 0.00;
		fb.setVariable(key, flowValue);
		fb.setVariable("weightFactor", weightFactor);
	

		// Evaluate
		fb.evaluate();
		
		//JFuzzyChart.get().chart( fb.getVariable(key).getDefuzzifier(),key,true);
		calculatedOutput = fb.getVariable("closenessRelation").getValue();
		
		return calculatedOutput;
	}
	
	

}
