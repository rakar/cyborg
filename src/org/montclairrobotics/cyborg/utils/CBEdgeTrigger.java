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

	public CBEdgeTrigger setInitialState(boolean value) {
		this.state = value;
		this.toggle = value;
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
}
