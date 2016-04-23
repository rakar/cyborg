package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.DriveRequestMapper;

public class ArcadeDriveRequestMapper extends DriveRequestMapper {
	private int[] joystick = new int[3];
	private int[] joystickAxis = new int[3];
	private double[] deadzone = new double[3];
	private int gyroLockStick = -1;
	private int gyroLockButton = -1;
	private double[] smoothing = new double[3];
	private double[] lastValue = new double[3];

	public ArcadeDriveRequestMapper(Cyborg robot, int fwdJoystick, int fwdJoystickAxis, int strJoystick, int strJoystickAxis, int rotJoystick, int rotJoystickAxis) {
		super(robot);
		this.joystick[0] = fwdJoystick;
		this.joystickAxis[0] = fwdJoystickAxis;
		this.joystick[1] = strJoystick;
		this.joystickAxis[1] = strJoystickAxis;
		this.joystick[2] = rotJoystick;
		this.joystickAxis[2] = rotJoystickAxis;
	}

	public ArcadeDriveRequestMapper setDeadZone(double deadzone) {
		return setDeadZone(deadzone,deadzone,deadzone);
	}
	
	public ArcadeDriveRequestMapper setDeadZone(double fwdDeadzone, double strDeadzone, double rotDeadzone) {
		this.deadzone[0] = fwdDeadzone;
		this.deadzone[1] = strDeadzone;
		this.deadzone[2] = rotDeadzone;
		return this;
	}
	
	
	public ArcadeDriveRequestMapper setGyroLockButton(int stick, int button) {
		this.gyroLockStick = stick;
		this.gyroLockButton = button;
		return this;
	}

	public ArcadeDriveRequestMapper setSmoothing(double fwdSmoothing, double strSmoothing, double rotSmoothing) {
		this.smoothing[0] = fwdSmoothing;
		this.smoothing[1] = strSmoothing;
		this.smoothing[2] = rotSmoothing;
		return this;
	}
		
	@Override
	public void update() {
		double value[] = new double[3];
		
		for(int i=0;i<3;i++) {
			if(joystick[i]>=0)
			   value[i] = robot.driverStationState.getJoystickAxis(joystick[i], joystickAxis[i]);
			else
				value[i] = 0;	
			if(Math.abs(value[i]) <deadzone[i]) value[i] = 0.0;
		}
		
		if(robot.driveRequestStatus instanceof GeneralDriveRequestStatus) {
			GeneralDriveRequestStatus rs = (GeneralDriveRequestStatus)robot.driveRequestStatus;

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
			
			if(gyroLockStick>=0) {
				rs.gyroLock = robot.driverStationState.getButtonState(gyroLockStick, gyroLockButton);
			}			
		} else {
			robot.driveRequestStatus.active = false; // If we don't know what type of request it is shut down drive
		}
	}

}
