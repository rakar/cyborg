package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.CBDriveRequestMapper;

public class CBTankDriveRequestMapper extends CBDriveRequestMapper {
	private CBAxis left;
	private CBAxis right;
	private double deadzone = 0.0;
	private CBButton gyroLock=null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CBTankDriveRequestMapper(Cyborg robot, Enum leftID, Enum rightID) {
		super(robot);
		this.left = Cyborg.getHA().getAxis(leftID);
		this.right = Cyborg.getHA().getAxis(rightID);
	}
	
	public CBTankDriveRequestMapper setDeadZone(double deadzone) {
		this.deadzone = deadzone;
		return this;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CBTankDriveRequestMapper setGyroLockButton(Enum buttonID) {
		this.gyroLock = Cyborg.getHA().getButton(buttonID);
		return this;
	}

	@Override
	public void update() {
		double leftStick  = 0; //-left.get(); //-Cyborg.getHA().getJoystickAxis(leftJoystick,  leftJoystickAxis );  // y-axis of first stick
		double rightStick = 0; // right.get(); //Cyborg.getHA().getJoystickAxis(rightJoystick, rightJoystickAxis);  // y-axis of second stick;
		
		if(left!=null && left.isDefined()) leftStick = -left.get();
		if(right!=null && right.isDefined()) rightStick = right.get();

		
		// Implement dead zone
		if(Math.abs( leftStick)<deadzone)  leftStick=0.0;
		if(Math.abs(rightStick)<deadzone) rightStick=0.0;
		
		if(Cyborg.driveRequestStatus instanceof CBGeneralDriveRequestStatus) {
			CBGeneralDriveRequestStatus rs = (CBGeneralDriveRequestStatus)Cyborg.driveRequestStatus;
			double velocity = (leftStick+rightStick)/2.0;// Average stick value "forward"
			double rotation = leftStick - velocity;

			rs.active = true;
			
			rs.direction.setXY(0, velocity); 
			rs.rotation = rotation; 
			
			if(gyroLock!=null && gyroLock.isDefined()) {
				rs.gyroLock = gyroLock.getButtonState();
			}
			
		} else if (Cyborg.driveRequestStatus instanceof CBTankDriveRequestStatus) {
			CBTankDriveRequestStatus rs = (CBTankDriveRequestStatus)Cyborg.driveRequestStatus;
			rs.leftPower = leftStick; 
			rs.rightPower = rightStick; 
			rs.active = true;
		} else {
			Cyborg.driveRequestStatus.active = false; // If we don't know what type of request it is shut down drive
		}
	}
}
