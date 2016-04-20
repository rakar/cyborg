package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.DriveRequestMapper;

public class TankDriveRequestMapper extends DriveRequestMapper {
	private int leftJoystick;
	private int leftJoystickAxis;
	private int rightJoystick;
	private int rightJoystickAxis;
	private double deadzone = 0.0;

	public TankDriveRequestMapper(Cyborg robot, int leftJoystick, int leftJoystickAxis, int rightJoystick, int rightJoystickAxis) {
		super(robot);
		// TODO Auto-generated constructor stub
	}
	
	public void setDeadZone(double deadzone) {
		this.deadzone = deadzone;
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
			rs.direction.setLocation(0, velocity); 
			rs.rotation = rotation; 
			rs.active = true;
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
