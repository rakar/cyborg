package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.ManipController;
import org.usfirst.frc.team555.robot.Robot;

public class SHManipController extends ManipController {
	Robot robot;

	public SHManipController(Robot robot) {
		super(robot);
		this.robot = robot;
	}

	
	@Override public void update() {
		
		if(robot.manipControlStatus instanceof SHManipControlStatus) {

			SHManipControlStatus cs = (SHManipControlStatus)robot.manipControlStatus;
			robot.armValve.set(cs.ArmDown.get());
			robot.halfValve.set(cs.HalfUp.get());
			robot.shootValve.set(cs.ShootOut.get());
			
			robot.leftSpin.set(-cs.SpinSpeed);
			robot.rightSpin.set(cs.SpinSpeed);
			
		}
	}
	
}
