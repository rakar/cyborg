package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBManipulatorController;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBMotorController;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.usfirst.frc.team555.robot.Robot;
import org.usfirst.frc.team555.robot.Robot.Device;

public class SHManipulatorController extends CBManipulatorController {
	Robot robot;
	CBSolenoid armValve;
	CBSolenoid halfValve;
	CBSolenoid shootValve;
	CBMotorController spinLeft;
	CBMotorController spinRight;

	public SHManipulatorController(Robot robot) {
		super(robot);
		this.robot = robot;
		
		armValve   = Cyborg.getHA().getSolenoidValve(Device.ARM_VALVE);
		halfValve  = Cyborg.getHA().getSolenoidValve(Device.HALF_VALVE);
		shootValve = Cyborg.getHA().getSolenoidValve(Device.SHOOT_VALVE);
		
		spinLeft   = Cyborg.getHA().getMotorController(Device.SPIN_LEFT);
		spinRight  = Cyborg.getHA().getMotorController(Device.SPINT_RIGHT);
	}

	
	@Override public void update() {
		
		if(Cyborg.manipulatorControlData instanceof SHManipulatorControlData) {

			SHManipulatorControlData cs = (SHManipulatorControlData)Cyborg.manipulatorControlData;
			armValve.set(cs.ArmDown.get());
			halfValve.set(cs.HalfUp.get());
			shootValve.set(cs.ShootOut.get());
			
			spinLeft.set(-cs.SpinSpeed);
			spinRight.set(cs.SpinSpeed);
			
		}
	}
	
}
