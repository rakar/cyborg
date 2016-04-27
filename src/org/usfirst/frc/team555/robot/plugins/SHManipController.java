package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBManipController;
import org.usfirst.frc.team555.robot.Robot;
import org.usfirst.frc.team555.robot.Robot.Device;

public class SHManipController extends CBManipController {
	Robot robot;

	public SHManipController(Robot robot) {
		super(robot);
		this.robot = robot;
	}

	
	@Override public void update() {
		
		if(robot.manipControlStatus instanceof SHManipControlStatus) {

			SHManipControlStatus cs = (SHManipControlStatus)robot.manipControlStatus;
			robot.getHA().getSolenoidValve(Device.ARM_VALVE).set(cs.ArmDown.get());
			robot.getHA().getSolenoidValve(Device.HALF_VALVE).set(cs.HalfUp.get());
			robot.getHA().getSolenoidValve(Device.SHOOT_VALVE).set(cs.ShootOut.get());
			
			robot.getHA().getMotorController(Device.SPIN_LEFT).set(-cs.SpinSpeed);
			robot.getHA().getMotorController(Device.SPINT_RIGHT).set(cs.SpinSpeed);
			
		}
	}
	
}
