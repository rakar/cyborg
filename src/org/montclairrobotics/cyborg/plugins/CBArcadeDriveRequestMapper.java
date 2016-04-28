package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.usfirst.frc.team555.robot.Robot.Device;
import org.montclairrobotics.cyborg.CBDriveRequestMapper;

public class CBArcadeDriveRequestMapper extends CBDriveRequestMapper {
	private CBAxis[] axes = new CBAxis[3];
	private double[] deadzone = new double[3];
	private CBButton gyroLock = null; //new CBJoystickIndex();
	private double[] smoothing = new double[3];
	private double[] lastValue = new double[3];

	public CBArcadeDriveRequestMapper(Cyborg robot, Device fwd, Device str, Device rot) {
		super(robot);
		this.axes[0] = Cyborg.getHA().getAxis(fwd);
		this.axes[1] = Cyborg.getHA().getAxis(str);
		this.axes[2] = Cyborg.getHA().getAxis(rot);
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
	
	public CBArcadeDriveRequestMapper setGyroLockButton(Device buttonID) {
		this.gyroLock = Cyborg.getHA().getButton(buttonID);
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
		
		if(Cyborg.driveRequestStatus instanceof CBGeneralDriveRequestStatus) {
			CBGeneralDriveRequestStatus rs = (CBGeneralDriveRequestStatus)Cyborg.driveRequestStatus;

			// if smoothing is defined for a given axis use it to follow the control 
			for(int i=0;i<3;i++) {
				if(smoothing[i]!=0) {
					value[i] = lastValue[i] + (value[i] - lastValue[i])*smoothing[i];
					lastValue[i] = value[i];
				}
			}

			rs.active = true;
			rs.direction.setXY(value[1], value[0]); 
			rs.rotation = value[2]; 
			
			if(gyroLock!=null && gyroLock.isDefined()) {
				rs.gyroLock = gyroLock.getButtonState();
			}			
		} else {
			Cyborg.driveRequestStatus.active = false; // If we don't know what type of request it is shut down drive
		}
	}
}
