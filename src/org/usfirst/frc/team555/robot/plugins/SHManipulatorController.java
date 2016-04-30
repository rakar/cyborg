package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBRobotController;
import org.montclairrobotics.cyborg.CBHardwareAdapter;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBMotorController;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.usfirst.frc.team555.robot.Robot;

public class SHManipulatorController extends CBRobotController {
	Robot robot;
	CBSolenoid armValve;
	CBSolenoid halfValve;
	CBSolenoid shootValve;
	CBMotorController spinLeft;
	CBMotorController spinRight;

	public SHManipulatorController(Robot robot) {
		super(robot);
		this.robot = robot;
		CBHardwareAdapter ha = Cyborg.hardwareAdapter;
		
		armValve   = ha.getSolenoidValve(robot.devices.armValve);
		halfValve  = ha.getSolenoidValve(robot.devices.halfValve);
		shootValve = ha.getSolenoidValve(robot.devices.shootValve);
		
		spinLeft   = ha.getMotorController(robot.devices.spinLeft);
		spinRight  = ha.getMotorController(robot.devices.spinRight);
	}
	
	@Override 
	public void update() {
		
		SHManipulatorControlData cs = (SHManipulatorControlData)Cyborg.manipulatorControlData;
		armValve.set(cs.ArmDown.get());
		halfValve.set(cs.HalfUp.get());
		shootValve.set(cs.ShootOut.get());
		
		spinLeft.set(-cs.SpinSpeed);
		spinRight.set(cs.SpinSpeed);

	}
	
}
