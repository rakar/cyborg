package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.CBTeleOpMapper;

public class CBArcadeDriveRequestMapper extends CBTeleOpMapper {
	private CBAxis[] axes = new CBAxis[3];
	private CBButton gyroLock = null; 
	private double[] deadzone = new double[3];
	private double[] smoothing = new double[3];
	private double[] lastValue = new double[3];

	public CBArcadeDriveRequestMapper(Cyborg robot, CBDeviceID fwdDeviceID, CBDeviceID strDeviceID, CBDeviceID rotDeviceID) {
		super(robot);
		this.axes[0] = Cyborg.hardwareAdapter.getAxis(fwdDeviceID);
		this.axes[1] = Cyborg.hardwareAdapter.getAxis(strDeviceID);
		this.axes[2] = Cyborg.hardwareAdapter.getAxis(rotDeviceID);
	}

	public CBArcadeDriveRequestMapper setDeadZone(double deadzone) {
		return setDeadZone(deadzone,deadzone,deadzone);
	}
	
	public CBArcadeDriveRequestMapper setDeadZone(double fwdDeadzone, double strDeadzone, double rotDeadzone) {
		this.deadzone[0] = fwdDeadzone;
		this.deadzone[1] = strDeadzone;
		this.deadzone[2] = rotDeadzone;
		return this;
	}
	
	public CBArcadeDriveRequestMapper setGyroLockButton(CBDeviceID buttonDeviceID) {
		this.gyroLock = Cyborg.hardwareAdapter.getButton(buttonDeviceID);
		return this;
	}

	public CBArcadeDriveRequestMapper setSmoothing(double fwdSmoothing, double strSmoothing, double rotSmoothing) {
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
			CBGeneralDriveRequestData rs = (CBGeneralDriveRequestData)Cyborg.driveRequestData;

			// if smoothing is defined for a given axis use it to follow the control 
			for(int i=0;i<3;i++) {
				if(smoothing[i]!=0) {
					value[i] = lastValue[i] + (value[i] - lastValue[i])*smoothing[i];
					lastValue[i] = value[i];
				}
			}

			rs.active = true;
			rs.direction.setXY(value[1], -value[0]); 
			rs.rotation = -value[2]; 
			
			if(gyroLock!=null && gyroLock.isDefined()) {
				rs.gyroLock = gyroLock.getButtonState();
			}			
		} else {
			Cyborg.driveRequestData.active = false; // If we don't know what type of request it is shut down drive
		}
	}
}
