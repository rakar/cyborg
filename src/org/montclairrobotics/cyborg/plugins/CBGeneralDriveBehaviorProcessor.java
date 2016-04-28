package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.CBBehaviorProcessor;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.*;

public class CBGeneralDriveBehaviorProcessor extends CBBehaviorProcessor {
	CBEdgeTrigger gyroLockState;
	CBTracker gyroLockTracker=null;
	

	public CBGeneralDriveBehaviorProcessor(Cyborg robot) {
		super(robot);

		gyroLockState = new CBEdgeTrigger();
	}
	
	public CBGeneralDriveBehaviorProcessor setGyroLockTracker(CBSource source, CBPID pid) {
		this.gyroLockTracker = new CBTracker(source, pid);
		return this;
	}
		
	@Override
	public void update() {
		super.update();
		
		CBGeneralDriveRequestStatus rs = (CBGeneralDriveRequestStatus)Cyborg.driveRequestStatus;
		CBGeneralDriveControlStatus cs = (CBGeneralDriveControlStatus)Cyborg.driveControlStatus;
		
		cs.active = rs.active;
		if(cs.active) {
			cs.direction.copy(rs.direction);
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
