package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.usfirst.frc.team555.robot.Robot;

public class SHAutoAISM extends CBStateMachine<AutoAIState> {
	private Robot robot;

	public SHAutoAISM(Robot robot) {
		super(AutoAIState.start);
		this.robot = robot;
	}
	
	@Override
	public void calcNextState() {
		switch(currState) {
		case start:
			nextState = AutoAIState.armdown;
			break;
		case armdown:
			if(cycles>175) nextState = AutoAIState.drive;
			break;
		case drive:
			if(cycles>175) nextState = AutoAIState.done;
			break;
		case done:
			break;			
		}
	}
	
	@Override
	public void doTransition() {
		/*
		SHManipRequestStatus scr = (SHManipRequestStatus) robot.manipRequestStatus;
		SHDriveRequestStatus dcr =(SHDriveRequestStatus) robot.driveRequestStatus;
		if(currState==AutoAIState.start && nextState==AutoAIState.armdown) {
			scr.ArmDown=true;
			scr.HalfDown=true;			
		}
		if(currState==AutoAIState.armdown && nextState==AutoAIState.drive) {
			dcr.DrivePower = 0.5;
			dcr.TurnPower  = 0.0;
			dcr.DriveAngle = 0.0;
			dcr.DriveAngleReset = true;
			dcr.GyroLock = true;
			dcr.DriveCmd = true;
		}
		if(currState==AutoAIState.drive && nextState==AutoAIState.done) {
			dcr.DrivePower = 0.0;
			dcr.TurnPower  = 0.0;
			dcr.DriveAngle = 0.0;
			dcr.DriveAngleReset = true;
			dcr.GyroLock = true;
			dcr.DriveCmd = true;
		}
		*/
	}
}
