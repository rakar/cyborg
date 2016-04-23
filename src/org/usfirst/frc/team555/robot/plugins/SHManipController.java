package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.ManipController;
import org.montclairrobotics.cyborg.utils.*;
import org.usfirst.frc.team555.robot.Robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class SHManipController extends ManipController {

	Solenoid armValve;
	Solenoid shootValve;
	Solenoid halfValve;

	EdgeTrigger armControl;
	EdgeTrigger halfControl;
	EdgeTrigger shootControl;
	
	Talon leftSpin;
	Talon rightSpin;
	
	public SHManipController(Robot robot) {
		super(robot);

		armValve = new Solenoid(0);
		halfValve = new Solenoid(2);
		shootValve = new Solenoid(1);
		
		armControl = new EdgeTrigger();
		halfControl = new EdgeTrigger();
		shootControl = new EdgeTrigger();
		
		leftSpin = new Talon(5);
		rightSpin = new Talon(0);
	}

	
	@Override public void update() {
		
		if(robot.manipControlStatus instanceof SHManipControlStatus) {

			SHManipControlStatus cs = (SHManipControlStatus)robot.manipControlStatus;
			
			if(cs.ArmDown) armValve.set(true);
			if(cs.ArmUp)   armValve.set(false);
			
			if(cs.HalfDown) halfValve.set(true);
			if(cs.HalfUp)   halfValve.set(false);
			
			if(cs.ShootOut) shootValve.set(true);
			if(cs.ShootIn)  shootValve.set(false);
			
			leftSpin.set(-cs.SpinSpeed);
			rightSpin.set(cs.SpinSpeed);
			
		}
	}
	
}
