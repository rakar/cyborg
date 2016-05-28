package org.montclairrobotics.cyborg.behaviors;

import org.montclairrobotics.cyborg.CBBehavior;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBDifferentialDriveControlData;
import org.montclairrobotics.cyborg.data.CBTankDriveRequestData;

public class CBTankDriveBehavior extends CBBehavior {

	public CBTankDriveBehavior(Cyborg robot) {
		super(robot);
	}

	@Override
	public void update() {
		super.update();
		
		CBTankDriveRequestData drd = (CBTankDriveRequestData)Cyborg.driveRequestData;
		CBDifferentialDriveControlData dcd = (CBDifferentialDriveControlData)Cyborg.driveControlData;
		
		// Copy simple Tank drive command info
		dcd.leftPower = drd.leftPower;
		dcd.rightPower = drd.rightPower;
		dcd.active = drd.active;
		
		//
		// Turn off request.active to indicate that command was handled. 
		// This will prevent re-processing a given request. For example
		// Autonomous may only issue drive requests periodically.
		//  
		drd.active = false;
	}
}
