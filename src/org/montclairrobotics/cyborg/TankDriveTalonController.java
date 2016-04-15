package org.montclairrobotics.cyborg;

import edu.wpi.first.wpilibj.Talon;

public class TankDriveTalonController extends DriveController {
	Talon tLeft;
	Talon tRight;

	public TankDriveTalonController(Cyborg robot,int leftPWM, int rightPWM) {
		super(robot);
		tLeft = new Talon(leftPWM);
		tRight = new Talon(rightPWM);
	}
	
	@Override
	public void Update() {
		if(!(robot.driveControlStatus instanceof TankDriveControlStatus)) {
			System.out.println("Error: TankDriveTalonController requires TankDriveControlStatus");
		} else {
			TankDriveControlStatus status = (TankDriveControlStatus)robot.driveControlStatus;
			if(status.active) {
				tLeft.set(status.leftPower);
				tRight.set(status.rightPower);
			}
		}
	}

	/* The idea of this function would be to do some setup process that calls
	 * ConfigHardware() to build whatever required WPI infrastructure is required.
	 * This concept is not fleshed out yet.  
	 */
	@Override
	public void ConfigHardware() {
		
	}
}
