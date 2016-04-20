package org.montclairrobotics.cyborg;

import edu.wpi.first.wpilibj.Joystick;

public class DriverStationInterface extends CyborgModule {
		
	public DriverStationInterface(Cyborg robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		for(int i=0; i<robot.driverStationState.joystickCount;i++) {
			Joystick js = robot.driverStationState.joysticks.get(i);
			int jsbc = js.getButtonCount();
			for(int j=0;j<jsbc;j++){
				int state = js.getRawButton(j+1)?1:0;
				robot.driverStationState.buttonTrans[i][j] = state-robot.driverStationState.buttonState[i][j];
				robot.driverStationState.buttonState[i][j] = state;
			}
		}
	}
	
	/*
	 * Getters/Setters
	 */
	public void setJoystickCount(int count) {
		for(int i=robot.driverStationState.joystickCount;i<count;i++) {
			robot.driverStationState.joysticks.add(new Joystick(i+1));
		}
		while(robot.driverStationState.joysticks.size()>count) {
			robot.driverStationState.joysticks.remove(count);
		}
		robot.driverStationState.joystickCount=count;
	}
	
	public int getJoystickCount() {
		return robot.driverStationState.joystickCount;
	}
	
	

}
