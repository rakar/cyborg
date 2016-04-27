package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBJoystickIndex;
import org.montclairrobotics.cyborg.CBDriveRequestMapper;

public class CBArcadeDriveRequestMapper extends CBDriveRequestMapper {
	private CBJoystickIndex[] axes = new CBJoystickIndex[3];
	private double[] deadzone = new double[3];
	private CBJoystickIndex gyroLock = new CBJoystickIndex();
	private double[] smoothing = new double[3];
	private double[] lastValue = new double[3];

	public CBArcadeDriveRequestMapper(Cyborg robot, int fwdJoystick, int fwdJoystickAxis, int strJoystick, int strJoystickAxis, int rotJoystick, int rotJoystickAxis) {
		super(robot);
		this.axes[0] = new CBJoystickIndex(fwdJoystick, fwdJoystickAxis);
		this.axes[1] = new CBJoystickIndex(strJoystick, strJoystickAxis);
		this.axes[2] = new CBJoystickIndex(rotJoystick, rotJoystickAxis);
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
	
	
	public CBArcadeDriveRequestMapper setGyroLockButton(int stick, int button) {
		this.gyroLock = new CBJoystickIndex(stick,button);
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
			if(axes[i].isDefined())
			   value[i] = robot.hardwareAdapter.getJoystickAxis(axes[i]);
			else
				value[i] = 0;	
			if(Math.abs(value[i]) <deadzone[i]) value[i] = 0.0;
		}
		
		if(robot.driveRequestStatus instanceof CBGeneralDriveRequestStatus) {
			CBGeneralDriveRequestStatus rs = (CBGeneralDriveRequestStatus)robot.driveRequestStatus;

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
			
			if(gyroLock.isDefined()) {
				rs.gyroLock = robot.hardwareAdapter.getButtonState(gyroLock);
			}			
		} else {
			robot.driveRequestStatus.active = false; // If we don't know what type of request it is shut down drive
		}
	}

}
