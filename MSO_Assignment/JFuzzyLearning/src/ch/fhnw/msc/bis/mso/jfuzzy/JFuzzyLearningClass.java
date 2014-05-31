package ch.fhnw.msc.bis.mso.jfuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  

import ch.fhnw.msc.bis.mso.jfuzzy.FlowData.FlowType;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class JFuzzyLearningClass {
	public static void main(String[] args) {
	   
		List<FacilityRelation> facilityRelations = new ArrayList<FacilityRelation>();
		 final String referenceDataFile = "..\\JFuzzyLearning\\InputFiles\\FuzzyGeneticRefData.csv";
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
	
	private static void initializeFacilityRelations(List<FacilityRelation> facilityRelations, String referenceDataFile)
	{
		//List<FacilityRelation> facilityRelations = new ArrayList<FacilityRelation>();
		
		 Map<String, String> fclFiles = new HashMap<>();
	    fclFiles.put(FlowType.INFORMATION.toString(),"..\\JFuzzyLearning\\InputFiles\\InformationFlow_WeightFactor.fcl" );
		fclFiles.put(FlowType.MATERIAL.toString(),"..\\JFuzzyLearning\\InputFiles\\MaterialFlow_WeightFactor.fcl" );
		fclFiles.put(FlowType.EQUIPMENT.toString(),"..\\JFuzzyLearning\\InputFiles\\EquipmentFlow_WeightFactor.fcl" );
		
	    InitializeReferenceDataFromFile(facilityRelations, referenceDataFile);
	    
	    for (String s : fclFiles.keySet()) {
	        ApplyFuzzyLogic(s,fclFiles.get(s),facilityRelations);
	    
	    }
	}
	
	private static void ApplyFuzzyLogic(String key, String filename,
			List<FacilityRelation> facilityRelations) {
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);
		JFuzzyChart.get().chart(fb);
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
 


