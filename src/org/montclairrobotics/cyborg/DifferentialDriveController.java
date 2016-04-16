package org.montclairrobotics.cyborg;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.SpeedController;

public class DifferentialDriveController extends DriveController {
	ArrayList<SpeedController> tLeft = new ArrayList<>();
	ArrayList<SpeedController> tRight = new ArrayList<>();

	public DifferentialDriveController(Cyborg robot) {
		super(robot);
	}
	
	@Override
	public void Update() {
		if(!(robot.driveControlStatus instanceof TankDriveControlStatus)) {
			System.out.println("Error: TankDriveTalonController requires TankDriveControlStatus");
		} else {
			TankDriveControlStatus status = (TankDriveControlStatus)robot.driveControlStatus;
			if(status.active) {
				for(SpeedController l:tLeft) l.set(status.leftPower);
				for(SpeedController r:tRight) r.set(status.rightPower);
			}
		}
	}
	
	public DifferentialDriveController addLeftSpeedController(SpeedController controller) {
		tLeft.add(controller);
		return this;
	}
	
	public DifferentialDriveController addRightSpeedController(SpeedController controller) {
		tRight.add(controller);
		return this;
	}

	/* The idea of this function would be to do some setup process that calls
	 * ConfigHardware() to build whatever required WPI infrastructure is required.
	 * This concept is not fleshed out yet.  
	 */
	@Override
	public void ConfigHardware() {
		
	}
}
