package org.montclairrobotics.cyborg.utils;

public class CBEdgeTrigger {
	private boolean state = false;
	private boolean toggle = false;
	private boolean risingEdge = false;
	private boolean fallingEdge = false;
	private int pulseDuration = 1;
	private int pulseCount;
	private boolean risingEdgePulse = false;
	private boolean fallingEdgePulse = false;
	private int countTrue;
	private int countFalse;

	public CBEdgeTrigger setInitialState(boolean value) {
		this.state = value;
		this.toggle = value;
		countTrue = 0;
		countFalse = 0;
		return this;
	}

	public CBEdgeTrigger setPulseDuration(int pulseDuration) {
		this.pulseDuration = pulseDuration;
		return this;
	}

	public CBEdgeTrigger update(boolean value) {

		risingEdge=false;
		fallingEdge=false;
		
		if(pulseCount>0) pulseCount--;
		if(pulseCount==0) {
			risingEdgePulse=false;
			fallingEdgePulse=false;
		}
		
		if(!state && value){
			risingEdge  = true;
			risingEdgePulse = true;
			fallingEdge = false;
			fallingEdgePulse = false;
			pulseCount = pulseDuration;
		}
		if(state && !value) {
			risingEdge  = false;
			risingEdgePulse = false;
			fallingEdge = true;
			fallingEdgePulse = true;
			pulseCount = pulseDuration;
		}
		if(risingEdge) {
			toggle = !toggle;
		}
		
		state = value;
		
		if(state) {
			countTrue++;
			countFalse=0;
		} else {
			countTrue=0;
			countFalse++;
		}
		
		return this;
	}

	public boolean getState() {
		return state;
	}

	public boolean getToggle() {
		return toggle;
	}

	public boolean getRisingEdge() {
		return risingEdge;
	}

	public boolean getFallingEdge() {
		return fallingEdge;
	}

	public boolean getRisingEdgePulse() {
		return risingEdgePulse;
	}

	public boolean getFallingEdgePulse() {
		return fallingEdgePulse;
	}
	public int getTrueCount() {
		return countTrue;
	}
	public int getFalseCount() {
		return countFalse;
	}
}
