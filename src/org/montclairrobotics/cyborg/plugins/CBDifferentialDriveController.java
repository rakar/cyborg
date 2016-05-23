package org.montclairrobotics.cyborg.plugins;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.utils.CBPIDController;
//import org.usfirst.frc.team555.robot.Robot.Device;
import org.montclairrobotics.cyborg.CBRobotController;

import edu.wpi.first.wpilibj.SpeedController;

public class CBDifferentialDriveController extends CBRobotController {

	public enum DriveMode { Power, Speed };

	private ArrayList<CBSpeedController> leftControllers = new ArrayList<>();
	private ArrayList<CBSpeedController> rightControllers = new ArrayList<>();
	private int leftDir = 1;
	private int rightDir = 1;
	private DriveMode mode;
	private double leftPower = 0.0;
	private double rightPower = 0.0;
	private CBEncoder leftEncoder = null;
	private CBEncoder rightEncoder = null;
	private CBPIDController leftPID = null;
	private CBPIDController rightPID = null;
	
	
	
	
	public CBDifferentialDriveController(Cyborg robot) {
		super(robot);
		mode = DriveMode.Power;
	}
	
	@Override
	public void update() {
		if(Cyborg.driveControlData.active) {
			if(Cyborg.driveControlData instanceof CBDifferentialDriveControlData) {

				CBDifferentialDriveControlData status = (CBDifferentialDriveControlData)Cyborg.driveControlData;
				switch(mode) {
				case Power:
					leftPower = status.leftPower*leftDir;
					rightPower = status.rightPower*rightDir;
					break;
				case Speed:
					if(leftPID==null || rightPID==null || leftEncoder==null || rightEncoder==null){
						System.out.println("Error: Drive mode=Speed, but pid or encoder not set.");
						leftPower = 0;
						rightPower = 0;
					} else {
						leftPower  += leftPID.setTarget(status.leftPower).update(leftEncoder.getRate());
						rightPower += rightPID.setTarget(status.rightPower).update(rightEncoder.getRate());
					}
					break;
				}
				
				for(SpeedController l:leftControllers) l.set(status.leftPower);
				for(SpeedController r:rightControllers) r.set(status.rightPower);
	
			} else if(Cyborg.driveControlData instanceof CBGeneralDriveControlData) {

				CBGeneralDriveControlData dcd = (CBGeneralDriveControlData)Cyborg.driveControlData;
				double left = dcd.direction.getY()-dcd.rotation;
				double right= dcd.direction.getY()+dcd.rotation;
				switch(mode) {
				case Power:
					leftPower = left*leftDir;
					rightPower = right*rightDir;
					break;
				case Speed:
					if(leftPID==null || rightPID==null || leftEncoder==null || rightEncoder==null){
						System.out.println("Error: Drive mode=Speed, but pid or encoder not set.");
						leftPower = 0;
						rightPower = 0;
					} else {
						leftPower  += leftPID.setTarget(left).update(leftEncoder.getRate());
						rightPower += rightPID.setTarget(right).update(rightEncoder.getRate());
					}
					break;
				}
				for(SpeedController l:leftControllers) l.set(leftPower);
				for(SpeedController r:rightControllers) r.set(rightPower);
	
			} else {
				System.out.println("Error: Invalid DriveControlStatus for DifferentialDriveController");
			}
		}
	}
	
	public CBDifferentialDriveController addLeftMotorController(CBDeviceID controller) {
		leftControllers.add(Cyborg.hardwareAdapter.getMotorController(controller));
		return this;
	}
	
	public CBDifferentialDriveController addRightMotorController(CBDeviceID controller) {
		rightControllers.add(Cyborg.hardwareAdapter.getMotorController(controller));
		return this;
	}
	
	public CBDifferentialDriveController setLeftDirection(int dir) {
		leftDir = dir;
		return this;
	}

	public CBDifferentialDriveController setRightDirection(int dir) {
		rightDir = dir;
		return this;
	}
	
	public CBDifferentialDriveController setDriveMode(DriveMode mode) {
		this.mode = mode;
		return this;
	}
	
	public CBDifferentialDriveController setEncoders(CBDeviceID leftEncoder, CBDeviceID rightEncoder) {
		this.leftEncoder = Cyborg.hardwareAdapter.getEncoder(leftEncoder);
		this.rightEncoder= Cyborg.hardwareAdapter.getEncoder(rightEncoder);
		return this;
	}
	
	public CBDifferentialDriveController setPIDControllers(CBPIDController leftPID, CBPIDController rightPID) {
		this.leftPID = leftPID;
		this.rightPID = rightPID;
		return this;
	}

	/* 
	 * The purpose of this function would be to do some setup process that calls
	 * configHardware() to build whatever required WPI infrastructure is required.
	 * This concept is not fleshed out yet.  
	 */
	@Override
	public void configHardware() {
		
	}
}
