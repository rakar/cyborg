package org.montclairrobotics.cyborg.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBDifferentialDriveControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.data.CBTankDriveRequestData;

public class CBTankDriveBehavior extends CBBehavior {

	CBTankDriveRequestData drd;
	CBDifferentialDriveControlData dcd;


	public CBTankDriveBehavior(Cyborg robot) {
		super(robot);
		setRequestData((CBTankDriveRequestData)Cyborg.requestData.driveData);
		setControlData((CBDifferentialDriveControlData)Cyborg.controlData.driveData);
	}

	public CBTankDriveBehavior setRequestData(CBTankDriveRequestData data) {
		drd = data;
		return this;
	}

	public CBTankDriveBehavior setControlData(CBDifferentialDriveControlData data) {
		dcd = data;
		return this;
	}

	@Override
	public void update() {
		super.update();
		
		//CBTankDriveRequestData drd = (CBTankDriveRequestData)Cyborg.requestData.driveData;
		//CBDifferentialDriveControlData dcd = (CBDifferentialDriveControlData)Cyborg.controlData.driveData;
		
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
