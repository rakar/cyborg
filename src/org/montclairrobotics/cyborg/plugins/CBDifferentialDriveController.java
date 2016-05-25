package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBSpeedControllerArrayController;
import org.montclairrobotics.cyborg.CBRobotController;

public class CBDifferentialDriveController extends CBRobotController {

	CBSpeedControllerArrayController leftController;
	CBSpeedControllerArrayController rightController;
	
	
	public CBDifferentialDriveController(Cyborg robot) {
		super(robot);
		//mode = CBDriveMode.Power;
	}
	
	@Override
	public void update() {
		if(Cyborg.driveControlData.active) {
			if(Cyborg.driveControlData instanceof CBDifferentialDriveControlData) {

				CBDifferentialDriveControlData status = (CBDifferentialDriveControlData)Cyborg.driveControlData;
				leftController.update(status.leftPower);
				rightController.update(status.rightPower);
	
			} else if(Cyborg.driveControlData instanceof CBGeneralDriveControlData) {

				CBGeneralDriveControlData dcd = (CBGeneralDriveControlData)Cyborg.driveControlData;
				double left = dcd.direction.getY()-dcd.rotation;
				double right= dcd.direction.getY()+dcd.rotation;
				leftController.update(left);
				rightController.update(right);
	
			} else {
				System.out.println("Error: Invalid DriveControlStatus for DifferentialDriveController");
			}
		}
	}
	
	public CBDifferentialDriveController setLeftSpeedControllerArray(CBSpeedControllerArrayController controller) {
		leftController = controller;
		return this;
	}
	
	public CBDifferentialDriveController setRightSpeedControllerArray(CBSpeedControllerArrayController controller) {
		rightController = controller;
		return this;
	}
	

	/* 
	 * The purpose of this function would be to do some setup process that calls
	 * configHardware() to build whatever required WPI infrastructure is required.
	 * This concept is not fleshed out yet.  
	 */
	@Override
	public void configHardware() {
		
	}
}
