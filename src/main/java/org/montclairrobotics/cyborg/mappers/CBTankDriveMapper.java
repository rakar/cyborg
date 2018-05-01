package org.montclairrobotics.cyborg.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.data.CBTankDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDeviceID;

public class CBTankDriveMapper extends CBTeleOpMapper {
	private CBAxis left;
	private CBAxis right;
	private double deadzone = 0.0;
	private CBButton gyroLock=null;
	

	public CBTankDriveMapper(Cyborg robot, CBDeviceID leftDeviceID, CBDeviceID rightDeviceID) {
		super(robot);
		this.left = Cyborg.hardwareAdapter.getAxis(leftDeviceID);
		this.right = Cyborg.hardwareAdapter.getAxis(rightDeviceID);
	}
	
	public CBTankDriveMapper setDeadZone(double deadzone) {
		this.deadzone = deadzone;
		return this;
	}
	
	public CBTankDriveMapper setGyroLockButton(CBDeviceID buttonID) {
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
		
		if(Cyborg.requestData.driveData instanceof CBStdDriveRequestData) {
			
			CBStdDriveRequestData drd = (CBStdDriveRequestData)Cyborg.requestData.driveData;
			double velocity = (leftStick+rightStick)/2.0;// Average stick value "forward"
			double rotation = leftStick - velocity;

			drd.active = true;
			drd.direction.setXY(0, velocity); 
			drd.rotation = rotation; 

			if(gyroLock!=null && gyroLock.isDefined()) {
				drd.gyroLockActive = gyroLock.getState();
			}
			
		} else if (Cyborg.requestData.driveData instanceof CBTankDriveRequestData) {
			
			CBTankDriveRequestData drd = (CBTankDriveRequestData)Cyborg.requestData.driveData;
			drd.leftPower = leftStick; 
			drd.rightPower = rightStick; 
			drd.active = true;
		
		} else {
			Cyborg.requestData.driveData.active = false; // If we don't know what type of request it is shut down drive
		}
	}
}
