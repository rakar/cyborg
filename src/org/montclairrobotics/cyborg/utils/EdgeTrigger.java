package org.montclairrobotics.cyborg.utils;

public class EdgeTrigger {
	private boolean state = false;
	private boolean risingEdge = false;
	private boolean fallingEdge = false;
	
	public void setState(boolean value) {
		risingEdge  = !state &&  value;
		fallingEdge =  state && !value;
		state = value;
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
