package com.rajesh.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.rajesh.bean.Metrics;

@Rule(name="Under Weight Rule", description="Current Weight is less than 10% of the base weight")
public class RuleUnderWeight {
private Metrics eMetrics;
	
	public boolean IsEmployeeUnderWeight;
	
	public float baseWeight;
	
	
	public RuleUnderWeight(Metrics metrics, float baseWeight)
	{
		this.eMetrics = metrics;
		this.baseWeight = baseWeight;
	}
	
    @Condition
	public boolean evaluate(){
		float currWeight = eMetrics.getWeight();
		return baseWeight > 0 && currWeight < (baseWeight - (0.1  * baseWeight));
	}

	@Action
	public void execute() throws Exception{
		IsEmployeeUnderWeight = true;
	}

}
