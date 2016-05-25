package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.assemblies.CBDriveModule;
import org.montclairrobotics.assemblies.CBSpeedControllerArrayController;
import org.montclairrobotics.cyborg.CBRobotController;

public class CBDifferentialDriveController extends CBRobotController {

	CBDriveModule leftDriveModule;
	CBDriveModule rightDriveModule;
	
	
	public CBDifferentialDriveController(Cyborg robot) {
		super(robot);
		//mode = CBDriveMode.Power;
	}
	
	@Override
	public void update() {
		if(Cyborg.driveControlData.active) {
			if(Cyborg.driveControlData instanceof CBDifferentialDriveControlData) {

				CBDifferentialDriveControlData status = (CBDifferentialDriveControlData)Cyborg.driveControlData;
				for(CBSpeedControllerArrayController c:leftDriveModule.getControllerArrays()) c.update(status.leftPower);
				for(CBSpeedControllerArrayController c:rightDriveModule.getControllerArrays()) c.update(status.rightPower);
	
			} else if(Cyborg.driveControlData instanceof CBGeneralDriveControlData) {
				
				// TODO: Now that we have position/orientation, I=implement the "go there from here" model. 
				// can now do full "trig/vector analysis" of movement to work out control values 
				// This might actually be more accurate if we scaled the rotation by the distance from center
				// and then add the radian amount to the linear travel adjusting both for orientation, then scale for time.
				
				CBGeneralDriveControlData dcd = (CBGeneralDriveControlData)Cyborg.driveControlData;
				double left = dcd.direction.getY()-dcd.rotation;
				double right= dcd.direction.getY()+dcd.rotation;
				for(CBSpeedControllerArrayController c:leftDriveModule.getControllerArrays()) c.update(left);
				for(CBSpeedControllerArrayController c:rightDriveModule.getControllerArrays()) c.update(right);
			} else {
				System.out.println("Error: Invalid DriveControlStatus for DifferentialDriveController");
			}
		}
	}
	
	public CBDifferentialDriveController setLeftDriveModule(CBDriveModule driveModule) {
		leftDriveModule = driveModule;
		return this;
	}
	
	public CBDifferentialDriveController setRightDriveModule(CBDriveModule driveModule) {
		rightDriveModule = driveModule;
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
