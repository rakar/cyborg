package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.BehaviorProcessor;
import org.montclairrobotics.cyborg.Cyborg;

public class GeneralDriveBehaviorProcessor extends BehaviorProcessor {

	public GeneralDriveBehaviorProcessor(Cyborg robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update() {
		super.update();
		
		GeneralDriveRequestStatus rs = (GeneralDriveRequestStatus)robot.driveRequestStatus;
		GeneralDriveControlStatus cs = (GeneralDriveControlStatus)robot.driveControlStatus;
		
		// Copy simple Tank drive command info
		cs.direction.setLocation(rs.direction);
		cs.rotation = rs.rotation;
		cs.active = rs.active;
		
		//
		// Turn off request.active to indicate that command was handled. 
		// This will prevent re-processing a given request. For example
		// Autonomous may only issue drive requests periodically.
		//  
		rs.active = false;
	}


}
