package org.montclairrobotics.cyborg.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.CBTeleOpMapper;

public class CBTankDriveRequestMapper extends CBTeleOpMapper {
	private CBAxis left;
	private CBAxis right;
	private double deadzone = 0.0;
	private CBButton gyroLock=null;
	

	public CBTankDriveRequestMapper(Cyborg robot, CBDeviceID leftDeviceID, CBDeviceID rightDeviceID) {
		super(robot);
		this.left = Cyborg.hardwareAdapter.getAxis(leftDeviceID);
		this.right = Cyborg.hardwareAdapter.getAxis(rightDeviceID);
	}
	
	public CBTankDriveRequestMapper setDeadZone(double deadzone) {
		this.deadzone = deadzone;
		return this;
	}
	
	public CBTankDriveRequestMapper setGyroLockButton(CBDeviceID buttonID) {
		this.gyroLock = Cyborg.hardwareAdapter.getButton(buttonID);
		return this;
	}

	@Override
	public void update() {
		double leftStick  = 0; // y-axis of first stick
		double rightStick = 0; // y-axis of second stick;
		
		if(left!=null && left.isDefined()) leftStick = -left.get();
		if(right!=null && right.isDefined()) rightStick = right.get();

		
		// Implement dead zone
		if(Math.abs( leftStick)<deadzone)  leftStick=0.0;
		if(Math.abs(rightStick)<deadzone) rightStick=0.0;
		
		if(Cyborg.driveRequestData instanceof CBGeneralDriveRequestData) {
			CBGeneralDriveRequestData rs = (CBGeneralDriveRequestData)Cyborg.driveRequestData;
			double velocity = (leftStick+rightStick)/2.0;// Average stick value "forward"
			double rotation = leftStick - velocity;

			rs.active = true;
			
			rs.direction.setXY(0, velocity); 
			rs.rotation = rotation; 
			
			if(gyroLock!=null && gyroLock.isDefined()) {
				rs.gyroLock = gyroLock.getButtonState();
			}
			
		} else if (Cyborg.driveRequestData instanceof CBTankDriveRequestData) {
			CBTankDriveRequestData rs = (CBTankDriveRequestData)Cyborg.driveRequestData;
			rs.leftPower = leftStick; 
			rs.rightPower = rightStick; 
			rs.active = true;
		} else {
			Cyborg.driveRequestData.active = false; // If we don't know what type of request it is shut down drive
		}
	}
}
