package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.CBBehaviorProcessor;
import org.montclairrobotics.cyborg.Cyborg;

public class CBTankDriveBehaviorProcessor extends CBBehaviorProcessor {

	public CBTankDriveBehaviorProcessor(Cyborg robot) {
		super(robot);
	}

	@Override
	public void update() {
		super.update();
		
		CBTankDriveRequestStatus rs = (CBTankDriveRequestStatus)Cyborg.driveRequestStatus;
		CBDifferentialDriveControlStatus cs = (CBDifferentialDriveControlStatus)Cyborg.driveControlStatus;
		
		// Copy simple Tank drive command info
		cs.leftPower = rs.leftPower;
		cs.rightPower = rs.rightPower;
		cs.active = rs.active;
		
		//
		// Turn off request.active to indicate that command was handled. 
		// This will prevent re-processing a given request. For example
		// Autonomous may only issue drive requests periodically.
		//  
		rs.active = false;
	}
}
