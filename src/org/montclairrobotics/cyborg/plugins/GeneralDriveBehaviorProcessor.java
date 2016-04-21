package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.BehaviorProcessor;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.EdgeTrigger;
import org.montclairrobotics.cyborg.utils.ISource;
import org.montclairrobotics.cyborg.utils.PID;
import org.montclairrobotics.cyborg.utils.Tracker;

public class GeneralDriveBehaviorProcessor extends BehaviorProcessor {
	EdgeTrigger gyroLockState;
	Tracker gyroLockTracker=null;
	

	public GeneralDriveBehaviorProcessor(Cyborg robot) {
		super(robot);

		gyroLockState = new EdgeTrigger();
	}
	
	public GeneralDriveBehaviorProcessor setGyroLockTracker(ISource source, PID pid) {
		this.gyroLockTracker = new Tracker(source, pid);
		return this;
	}
		
	@Override
	public void update() {
		super.update();
		
		GeneralDriveRequestStatus rs = (GeneralDriveRequestStatus)robot.driveRequestStatus;
		GeneralDriveControlStatus cs = (GeneralDriveControlStatus)robot.driveControlStatus;
		
		cs.active = rs.active;
		if(cs.active) {
			cs.direction.setLocation(rs.direction);
			cs.rotation = rs.rotation;
			
			gyroLockState.setState(rs.gyroLock);
			if(gyroLockTracker!=null) {
				if(gyroLockState.getRisingEdge()) gyroLockTracker.lock();
				if(gyroLockState.getState()) cs.rotation = gyroLockTracker.update();
			}
		}
		//
		// Turn off request.active to indicate that command was handled. 
		// This will prevent re-processing a given request. For example
		// Autonomous may only issue drive requests periodically.
		//  
		rs.active = false;
	}


}
