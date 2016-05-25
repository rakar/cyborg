package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.CBTeleOpMapper;

public class CBArcadeDriveMapper extends CBTeleOpMapper {
	private CBAxis[] axes = new CBAxis[3];
	private CBButton gyroLock = null; 
	private double[] deadzone = new double[3];
	private double[] smoothing = new double[3];
	private double[] lastValue = new double[3];

	public CBArcadeDriveMapper(Cyborg robot) {
		super(robot);
	}
	public CBArcadeDriveMapper setAxes(CBDeviceID fwdDeviceID, CBDeviceID strDeviceID, CBDeviceID rotDeviceID) {
		this.axes[0] = Cyborg.hardwareAdapter.getAxis(fwdDeviceID);
		this.axes[1] = Cyborg.hardwareAdapter.getAxis(strDeviceID);
		this.axes[2] = Cyborg.hardwareAdapter.getAxis(rotDeviceID);
		return this;
	}

	public CBArcadeDriveMapper setDeadZone(double deadzone) {
		return setDeadZone(deadzone,deadzone,deadzone);
	}
	
	public CBArcadeDriveMapper setDeadZone(double fwdDeadzone, double strDeadzone, double rotDeadzone) {
		this.deadzone[0] = fwdDeadzone;
		this.deadzone[1] = strDeadzone;
		this.deadzone[2] = rotDeadzone;
		return this;
	}
	
	public CBArcadeDriveMapper setGyroLockButton(CBDeviceID buttonDeviceID) {
		this.gyroLock = Cyborg.hardwareAdapter.getButton(buttonDeviceID);
		return this;
	}

	public CBArcadeDriveMapper setSmoothing(double fwdSmoothing, double strSmoothing, double rotSmoothing) {
		this.smoothing[0] = fwdSmoothing;
		this.smoothing[1] = strSmoothing;
		this.smoothing[2] = rotSmoothing;
		return this;
	}
		
	@Override
	public void update() {
		double value[] = new double[3];
		
		for(int i=0;i<3;i++) {
			if(axes[i]!=null && axes[i].isDefined())
			   value[i] = axes[i].get();
			else
				value[i] = 0;	
			if(Math.abs(value[i]) <deadzone[i]) value[i] = 0.0;
		}
		
		if(Cyborg.driveRequestData instanceof CBGeneralDriveRequestData) {
			CBGeneralDriveRequestData drd = (CBGeneralDriveRequestData)Cyborg.driveRequestData;

			// if smoothing is defined for a given axis use it to follow the control 
			for(int i=0;i<3;i++) {
				if(smoothing[i]!=0) {
					value[i] = lastValue[i] + (value[i] - lastValue[i])*smoothing[i];
					lastValue[i] = value[i];
				}
			}

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
