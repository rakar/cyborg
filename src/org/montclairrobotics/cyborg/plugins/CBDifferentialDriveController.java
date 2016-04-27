package org.montclairrobotics.cyborg.plugins;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.CBDriveController;

import edu.wpi.first.wpilibj.SpeedController;

public class CBDifferentialDriveController extends CBDriveController {
	private ArrayList<SpeedController> tLeft = new ArrayList<>();
	private ArrayList<SpeedController> tRight = new ArrayList<>();
	int leftDir = 1;
	int rightDir = 1;
	
	public CBDifferentialDriveController(Cyborg robot) {
		super(robot);
	}
	
	@Override
	public void update() {
		if(robot.driveControlStatus.active) {
			if(robot.driveControlStatus instanceof CBDifferentialDriveControlStatus) {

				CBDifferentialDriveControlStatus status = (CBDifferentialDriveControlStatus)robot.driveControlStatus;
				for(SpeedController l:tLeft) l.set(status.leftPower);
				for(SpeedController r:tRight) r.set(status.rightPower);
	
			} else if(robot.driveControlStatus instanceof CBGeneralDriveControlStatus) {

				CBGeneralDriveControlStatus status = (CBGeneralDriveControlStatus)robot.driveControlStatus;
				double left = status.direction.getY()+status.rotation;
				double right= status.direction.getY()-status.rotation;
				for(SpeedController l:tLeft) l.set(left*leftDir);
				for(SpeedController r:tRight) r.set(right*rightDir);
	
			} else {
				System.out.println("Error: Invalid DriveControlStatus for DifferentialDriveController");
			}
		}
	}
	
	public CBDifferentialDriveController addLeftSpeedController(SpeedController controller) {
		tLeft.add(controller);
		return this;
	}
	
	public CBDifferentialDriveController addRightSpeedController(SpeedController controller) {
		tRight.add(controller);
		return this;
	}
	
	public CBDifferentialDriveController setLeftDirection(int dir) {
		leftDir = dir;
		return this;
	}

	public CBDifferentialDriveController setRightDirection(int dir) {
		rightDir = dir;
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
