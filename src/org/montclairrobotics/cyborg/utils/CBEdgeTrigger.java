package org.montclairrobotics.cyborg.utils;

public class CBEdgeTrigger {
	private boolean state = false;
	private boolean risingEdge = false;
	private boolean fallingEdge = false;
	private int edgeDuration = 1;
	private int edgeCount;
	
	public CBEdgeTrigger setInitialState(boolean value) {
		this.state = value;
		return this;
	}
	
	public CBEdgeTrigger setEdgeDuration(int edgeDuration) {
		this.edgeDuration = edgeDuration;
		return this;
	}
	
	
	public CBEdgeTrigger update(boolean value) {
		if(edgeCount>0) edgeCount--;
		if(edgeCount==0) {
			risingEdge=false;
			fallingEdge=false;
		}
		if(!state && value){
			risingEdge  = true;
			edgeCount = edgeDuration;
		}
		if(state && !value) {
			fallingEdge = true;
			edgeCount = edgeDuration;
		}
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
