package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.BehaviorProcessor;
import org.montclairrobotics.cyborg.Cyborg;

public class TankDriveBehaviorProcessor extends BehaviorProcessor {

	public TankDriveBehaviorProcessor(Cyborg robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		super.update();
		
		TankDriveRequestStatus rs = (TankDriveRequestStatus)robot.driveRequestStatus;
		DifferentialDriveControlStatus cs = (DifferentialDriveControlStatus)robot.driveControlStatus;
		
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
