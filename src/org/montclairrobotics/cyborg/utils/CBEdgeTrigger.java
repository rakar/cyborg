package org.montclairrobotics.cyborg.utils;

public class CBEdgeTrigger {
	private boolean state = false;
	private boolean risingEdge = false;
	private boolean fallingEdge = false;
	
	public CBEdgeTrigger setState(boolean value) {
		risingEdge  = !state &&  value;
		fallingEdge =  state && !value;
		state = value;
		return this;
	}
	
	public boolean getState() {
		return state;
	}
	
	public boolean getRisingEdge() {
		return risingEdge;
	}

	public boolean getFallingEdge() {
		return fallingEdge;
	}
}
