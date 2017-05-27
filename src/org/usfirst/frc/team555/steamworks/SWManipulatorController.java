package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBSpeedControllerArrayController;
import org.montclairrobotics.cyborg.controllers.CBRobotController;

public class SWManipulatorController extends CBRobotController {

	SWControlData cd;
	double climbPower = 1.0;
	double gearPower = 0.5;
	CBSpeedControllerArrayController climbMotors;
	CBSpeedControllerArrayController leftMotor;
	CBSpeedControllerArrayController rightMotor;
	
	
	public SWManipulatorController(Cyborg robot) {
		super(robot);
		cd = (SWControlData)Cyborg.controlData;
	}
	
	@Override
	public void update() {
		super.update();
		
		if (cd.climb) {
			climbMotors.update(climbPower);
		} else {
			climbMotors.update(0);
		}
		
		if(cd.gearLeftOpen.isHigh()) leftMotor.update(gearPower);
		if(cd.gearLeftOpen.isLow()) leftMotor.update(-gearPower);
		if(cd.gearRightOpen.isHigh()) rightMotor.update(gearPower);
		if(cd.gearRightOpen.isLow()) rightMotor.update(-gearPower);
	}
	
	public SWManipulatorController setClimbMotors(CBSpeedControllerArrayController motorArray) {
		climbMotors = motorArray;
		return this;
	}
	
	public SWManipulatorController setLeftMotor(CBSpeedControllerArrayController motorArray) {
		leftMotor = motorArray;
		return this;
	}

	public SWManipulatorController setRightMotor(CBSpeedControllerArrayController motorArray) {
		rightMotor = motorArray;
		return this;
	}
}
