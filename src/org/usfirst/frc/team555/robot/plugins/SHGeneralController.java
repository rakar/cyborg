package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBRobotController;
import org.montclairrobotics.assemblies.CBSpeedControllerArrayController;
import org.montclairrobotics.assemblies.CBVictorArrayController;
import org.montclairrobotics.cyborg.CBHardwareAdapter;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.usfirst.frc.team555.robot.Robot;

public class SHGeneralController extends CBRobotController {
	Robot robot;
	SHGeneralControlData gcd;
	CBHardwareAdapter ha;

	CBSolenoid armValve;
	CBSolenoid halfValve;
	CBSolenoid shootValve;
	CBSpeedControllerArrayController spinArray;

	public SHGeneralController(Robot robot) {
		super(robot);
		this.robot = robot;
		gcd = (SHGeneralControlData)Cyborg.generalControlData;
		ha = Cyborg.hardwareAdapter;

		armValve   = ha.getSolenoidValve(robot.devices.armValve);
		halfValve  = ha.getSolenoidValve(robot.devices.halfValve);
		shootValve = ha.getSolenoidValve(robot.devices.shootValve);

		spinArray = new CBVictorArrayController()
				.addSpeedController(robot.devices.spinLeft)
				.addSpeedController(robot.devices.spinRight)
				.setDriveMode(CBDriveMode.Power)
				;
	}
	
	@Override 
	public void update() {

		armValve.set(gcd.ArmDown.get());
		halfValve.set(gcd.HalfUp.get());
		shootValve.set(gcd.ShootOut.get());

		spinArray.update(gcd.SpinSpeed);

	}

}
