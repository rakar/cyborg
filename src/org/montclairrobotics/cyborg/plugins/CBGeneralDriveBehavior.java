package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.CBBehavior;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.*;

public class CBGeneralDriveBehavior extends CBBehavior {
	CBEdgeTrigger gyroLockState;
	CBTracker gyroLockTracker=null;
	

	public CBGeneralDriveBehavior(Cyborg robot) {
		super(robot);

		gyroLockState = new CBEdgeTrigger();
	}
	
	public CBGeneralDriveBehavior setGyroLockTracker(CBSource source, CBPIDController pid) {
		this.gyroLockTracker = new CBTracker(source, pid);
		return this;
	}
		
	@Override
	public void update() {
		super.update();
		
		CBGeneralDriveRequestData rs = (CBGeneralDriveRequestData)Cyborg.driveRequestData;
		CBGeneralDriveControlData cs = (CBGeneralDriveControlData)Cyborg.driveControlData;
		
		cs.active = rs.active;
		if(cs.active) {
			cs.direction.copy(rs.direction);
			cs.rotation = rs.rotation;
			
			gyroLockState.setState(rs.gyroLock);
			if(gyroLockTracker!=null) {
				if(gyroLockState.getRisingEdge()) gyroLockTracker.lock();
				if(gyroLockState.getState()) cs.rotation = gyroLockTracker.update();
			}
			
			//
			// Turn off request.active to indicate that command was handled. 
			// This will prevent re-processing a given request. For example
			// Autonomous may only issue drive requests periodically.
			//  
			rs.active = false;
		}
	}


}
