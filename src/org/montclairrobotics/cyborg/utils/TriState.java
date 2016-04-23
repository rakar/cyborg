package org.montclairrobotics.cyborg.utils;

public class TriState {

	public enum TriStateValue {low, nil, high};
	
	private TriStateValue value=TriStateValue.nil;
	
	public TriState set(TriStateValue value) {
		this.value = value;
		return this;
	}
	
	public TriState set(boolean high, boolean low) {
		if(high) value=TriStateValue.high;
		else if(low) value=TriStateValue.low;
		else value=TriStateValue.nil;
		return this;
	}
	
	public TriStateValue get() {
		return value;
	};
	
	public Object select(Object high, Object low, Object nil) {
		if(value==TriStateValue.high) return high;
		if(value==TriStateValue.low) return low;
		return nil;
	}
}
