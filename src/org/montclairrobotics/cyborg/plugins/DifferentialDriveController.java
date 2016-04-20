package org.montclairrobotics.cyborg.plugins;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.DriveController;

import edu.wpi.first.wpilibj.SpeedController;

public class DifferentialDriveController extends DriveController {
	ArrayList<SpeedController> tLeft = new ArrayList<>();
	ArrayList<SpeedController> tRight = new ArrayList<>();

	public DifferentialDriveController(Cyborg robot) {
		super(robot);
	}
	
	@Override
	public void update() {
		if(robot.driveControlStatus instanceof DifferentialDriveControlStatus) {
			DifferentialDriveControlStatus status = (DifferentialDriveControlStatus)robot.driveControlStatus;
			if(status.active) {
				for(SpeedController l:tLeft) l.set(status.leftPower);
				for(SpeedController r:tRight) r.set(status.rightPower);
			}

		} else if(robot.driveControlStatus instanceof GeneralDriveControlStatus) {
			GeneralDriveControlStatus status = (GeneralDriveControlStatus)robot.driveControlStatus;
			
			double left = status.direction.getY()+status.rotation;
			double right= status.direction.getY()-status.rotation;
			
			if(status.active) {
				for(SpeedController l:tLeft) l.set(left);
				for(SpeedController r:tRight) r.set(right);
			}

		} else {
			System.out.println("Error: Invalid DriveControlStatus for TankDriveTalonController");
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
	public void configHardware() {
		
	}
}
