package org.montclairrobotics.cyborg;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;

public class DriverStationState {
	public int joystickCount;
	public ArrayList<Joystick> joysticks = new ArrayList<>(); 
	public int[][] buttonState = new int[4][16];
	public int[][] buttonTrans = new int[4][16];

	
	public double getJoystickAxis(int stick, int axis) {
		return joysticks.get(stick-1).getRawAxis(axis);
	}
	
	public boolean getButtonState(int stick, int button) {
		return buttonState[stick-1][button-1]==1?true:false;
	}
	
	public boolean getButtonPress(int stick, int button) {
		return buttonTrans[stick-1][button-1]==1?true:false;
	}
	
	public boolean getButtonRelease(int stick, int button) {
		return buttonTrans[stick-1][button-1]==-1?true:false;
	}
	

}
