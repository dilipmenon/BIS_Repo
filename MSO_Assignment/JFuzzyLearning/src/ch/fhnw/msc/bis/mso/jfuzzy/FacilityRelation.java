package ch.fhnw.msc.bis.mso.jfuzzy;

import ch.fhnw.msc.bis.mso.jfuzzy.FlowData.FlowType;

public class FacilityRelation {
	private String relationshipIdentifier;
	private FlowData informationFlow;
	private FlowData materialFlow;
	private FlowData equipmentFlow;
	private double referencedAverageClosenessValue;
	
	public FacilityRelation(String relationshipIdentifier)
	{
		this.setRelationshipIdentifier(relationshipIdentifier);
		this.setEquipmentFlow(new FlowData(FlowType.EQUIPMENT));
		this.setInformationFlow(new FlowData(FlowType.INFORMATION));
		this.setMaterialFlow(new FlowData(FlowType.MATERIAL));
	}
	
	public double GetCalculatedAverageClosenessValue()
	{
		return (informationFlow.getCalculatedClosenessValue()+
				materialFlow.getCalculatedClosenessValue()+
				equipmentFlow.getCalculatedClosenessValue())/3;
		
	}
	
	

	public String getRelationshipIdentifier() {
		return relationshipIdentifier;
	}
	
	public void setRelationshipIdentifier(String relationshipIdentifier) {
		this.relationshipIdentifier = relationshipIdentifier;
	}

	public FlowData getInformationFlow() {
		return informationFlow;
	}

	public void setInformationFlow(FlowData informationFlow) {
		this.informationFlow = informationFlow;
	}

	public FlowData getMaterialFlow() {
		return materialFlow;
	}

	public void setMaterialFlow(FlowData materialFlow) {
		this.materialFlow = materialFlow;
	}

	public FlowData getEquipmentFlow() {
		return equipmentFlow;
	}

	public void setEquipmentFlow(FlowData equipmentFlow) {
		this.equipmentFlow = equipmentFlow;
	}
	
	public FlowData getFlowDataByType(FlowType type)
	{
		if(type.equals(FlowType.EQUIPMENT))
			return this.equipmentFlow;
		
		if(type.equals(FlowType.INFORMATION))
			return this.informationFlow;
		
		return this.materialFlow;
	}

	public double getReferencedAverageClosenessValue() {
		return referencedAverageClosenessValue;
	}

	public void setReferencedAverageClosenessValue(
			double referencedAverageClosenessValue) {
		this.referencedAverageClosenessValue = referencedAverageClosenessValue;
	}
	


	
	

}
