package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBSpeedControllerArrayController;
import org.montclairrobotics.cyborg.controllers.CBRobotController;
import org.montclairrobotics.cyborg.devices.CBSpeedController;

public class SWManipulatorController extends CBRobotController {

	SWControlData cd;
	double climbPower = 1.0;
	double gearPower = 0.5;
	
	CBSpeedControllerArrayController climbMotors;
	CBSpeedController leftMotor;
	CBSpeedController rightMotor;
	
	
	public SWManipulatorController(Cyborg robot) {
		super(robot);
		cd = (SWControlData)Cyborg.controlData;
	}
	
	@Override
	public void update() {
		super.update();
		
		climbMotors.update(cd.climb?climbPower:0);
		leftMotor.set((double)cd.gearLeftOpen.select(gearPower, -gearPower, 0));
		rightMotor.set((double)cd.gearRightOpen.select(gearPower, -gearPower, 0));
	}
	
	public SWManipulatorController setClimbMotors(CBSpeedControllerArrayController motorArray) {
		climbMotors = motorArray;
		return this;
	}
	
	public SWManipulatorController setLeftMotor(CBSpeedController motorArray) {
		leftMotor = motorArray;
		return this;
	}

	public SWManipulatorController setRightMotor(CBSpeedController motorArray) {
		rightMotor = motorArray;
		return this;
	}
}
