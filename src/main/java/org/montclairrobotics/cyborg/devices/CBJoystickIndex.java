package org.montclairrobotics.cyborg.devices;

public class CBJoystickIndex{
	public int stickID;
	public int index;
	
	public CBJoystickIndex() {
		stickID  = -1;
		index = -1;
	}
	
	public CBJoystickIndex(int stickID, int index) {
		this.stickID = stickID;
		this.index = index;
	}
	
	public boolean isDefined() {
		return stickID>=0;
	}
	
	public static CBJoystickIndex undefined() {
		return new CBJoystickIndex(-1,-1);
	}
}
