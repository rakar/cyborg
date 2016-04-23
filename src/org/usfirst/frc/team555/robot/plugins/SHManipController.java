package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.Cyborg;
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
	EdgeTrigger halfControl = new EdgeTrigger();
	EdgeTrigger shootControl = new EdgeTrigger();
	
	Talon leftSpin;
	Talon rightSpin;
	
	public SHManipController(Robot robot) {
		super(robot);

		armValve = new Solenoid(0);
		halfValve = new Solenoid(1);
		shootValve = new Solenoid(2);
		
		armControl = new EdgeTrigger();
		halfControl = new EdgeTrigger();
		shootControl = new EdgeTrigger();
		
		leftSpin = new Talon(5);
		rightSpin = new Talon(6);
	}

	
	@Override public void update() {
		
		if(robot.manipControlStatus instanceof SHManipControlStatus) {

			SHManipControlStatus cs = (SHManipControlStatus)robot.manipControlStatus;
			
			armControl.setState(cs.ArmDown);
			if(armControl.getRisingEdge()) armValve.set(true);
			if(armControl.getFallingEdge()) armValve.set(false);
			
			halfControl.setState(cs.HalfDown);
			if(halfControl.getRisingEdge()) halfValve.set(true);
			if(halfControl.getFallingEdge()) halfValve.set(false);
			
			shootControl.setState(cs.ShootOut);
			if(shootControl.getRisingEdge()) shootValve.set(true);
			if(shootControl.getFallingEdge()) shootValve.set(false);
			
			leftSpin.set(-cs.SpinSpeed);
			rightSpin.set(cs.SpinSpeed);
			
		}
	}
	
}
