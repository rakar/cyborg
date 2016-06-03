package org.montclairrobotics.cyborg.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceId;

public class CBArcadeDriveMapper extends CBTeleOpMapper {
	private CBAxis[] axes = new CBAxis[3];
	private CBButton gyroLock = null; 

	public CBArcadeDriveMapper(Cyborg robot) {
		super(robot);
	}

	public CBArcadeDriveMapper setAxes(CBDeviceId fwdDeviceID, CBDeviceId strDeviceID, CBDeviceId rotDeviceID) {
		this.axes[0] = Cyborg.hardwareAdapter.getAxis(fwdDeviceID);
		this.axes[1] = Cyborg.hardwareAdapter.getAxis(strDeviceID);
		this.axes[2] = Cyborg.hardwareAdapter.getAxis(rotDeviceID);
		return this;
	}

	public CBArcadeDriveMapper setGyroLockButton(CBDeviceId buttonDeviceID) {
		this.gyroLock = Cyborg.hardwareAdapter.getButton(buttonDeviceID);
		return this;
	}

	@Override
	public void update() {
		double value[] = new double[3];
		
		for(int i=0;i<3;i++) {
			value[i] = (axes[i]!=null)?axes[i].get():0;
		}
		
		if(Cyborg.driveRequestData instanceof CBStdDriveRequestData) {
			CBStdDriveRequestData drd = (CBStdDriveRequestData)Cyborg.driveRequestData;

			drd.active = true;
			drd.direction.setXY(value[1], -value[0]); 
			drd.rotation = -value[2]; 
			
			if(gyroLock!=null && gyroLock.isDefined()) {
				drd.gyroLock = gyroLock.getButtonState();
			}			
		} else {
			Cyborg.driveRequestData.active = false; // If we don't know what type of request it is shut down drive
		}
	}
}
