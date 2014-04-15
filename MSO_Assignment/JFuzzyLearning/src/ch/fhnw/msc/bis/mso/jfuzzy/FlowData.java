package ch.fhnw.msc.bis.mso.jfuzzy;

public class FlowData {
	
	private double FlowValue;
	private double WeightFactor;
	private double ReferenceClosenessValue;
	private double CalculatedClosenessValue;
	private FlowType FlowType;
	
	public enum FlowType {
	    INFORMATION, MATERIAL, EQUIPMENT 
	} 
	
	

	public FlowData(FlowType flowType) {
		this.FlowType = flowType;
	}

	public double getFlowValue() {
		return FlowValue;
	}

	public void setFlowValue(double _flowValue) {
		this.FlowValue = _flowValue;
	}

	public double getWeightFactor() {
		return WeightFactor;
	}

	public void setWeightFactor(double _weightFactor) {
		this.WeightFactor = _weightFactor;
	}

	public double getReferenceClosenessValue() {
		return ReferenceClosenessValue;
	}

	public void setReferenceClosenessValue(double _referenceClosenessValue) {
		this.ReferenceClosenessValue = _referenceClosenessValue;
	}

	public double getCalculatedClosenessValue() {
		return CalculatedClosenessValue;
	}

	public void setCalculatedClosenessValue(double _calculatedClosenessValue) {
		this.CalculatedClosenessValue = _calculatedClosenessValue;
	}

	public FlowType getFlowType() {
		return FlowType;
	}

	public void setFlowType(FlowType _flowType) {
		this.FlowType = _flowType;
	}
	

}
