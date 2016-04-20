package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.DriveRequestMapper;

public class TankDriveRequestMapper extends DriveRequestMapper {
	private int leftJoystick;
	private int leftJoystickAxis;
	private int rightJoystick;
	private int rightJoystickAxis;
	private double deadzone = 0.0;
	private int gyroLockStick = -1;
	private int gyroLockButton = -1;

	public TankDriveRequestMapper(Cyborg robot, int leftJoystick, int leftJoystickAxis, int rightJoystick, int rightJoystickAxis) {
		super(robot);
		this.leftJoystick = leftJoystick;
		this.leftJoystickAxis = leftJoystickAxis;
		this.rightJoystick = rightJoystick;
		this.rightJoystickAxis = rightJoystickAxis;
	}
	
	public TankDriveRequestMapper setDeadZone(double deadzone) {
		this.deadzone = deadzone;
		return this;
	}
	
	public TankDriveRequestMapper setGyroLockButton(int stick, int button) {
		this.gyroLockStick = stick;
		this.gyroLockButton = button;
		return this;
	}

	@Override
	public void update() {
		double leftStick = robot.driverStationState.getJoystickAxis(leftJoystick,leftJoystickAxis);  // y-axis of first stick
		double rightStick = robot.driverStationState.getJoystickAxis(rightJoystick, rightJoystickAxis); // y-axis of second stick;
		
		// Implement dead zone
		if(Math.abs( leftStick)<deadzone)  leftStick=0.0;
		if(Math.abs(rightStick)<deadzone) rightStick=0.0;
		
		if(robot.driveRequestStatus instanceof GeneralDriveRequestStatus) {
			GeneralDriveRequestStatus rs = (GeneralDriveRequestStatus)robot.driveRequestStatus;
			double velocity = (leftStick+rightStick)/2.0;// Average stick value "forward"
			double rotation = leftStick - velocity;

			rs.active = true;
			
			rs.direction.setLocation(0, velocity); 
			rs.rotation = rotation; 
			
			if(gyroLockStick>=0) {
				rs.gyroLock = robot.driverStationState.getButtonState(gyroLockStick, gyroLockButton);
			}
			
		} else if (robot.driveRequestStatus instanceof TankDriveRequestStatus) {
			TankDriveRequestStatus rs = (TankDriveRequestStatus)robot.driveRequestStatus;
			rs.leftPower = leftStick; 
			rs.rightPower = rightStick; 
			rs.active = true;
		} else {
			robot.driveRequestStatus.active = false; // If we don't know what type of request it is shut down drive
		}
	}
}
