package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBSpeedControllerArrayController;
import org.montclairrobotics.cyborg.controllers.CBRobotController;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.usfirst.frc.team555.robot.Robot;

public class SHCustomController extends CBRobotController {
	Robot robot;
	SHCustomControlData ccd = (SHCustomControlData)Cyborg.customControlData;

	CBSolenoid armValve;
	CBSolenoid halfValve;
	CBSolenoid shootValve;
	CBSpeedControllerArrayController spinArray;

	public SHCustomController(Robot robot) {
		super(robot);
		this.robot = robot;
	}
	
	@Override 
	public void update() {
		armValve.set(ccd.ArmDown.get());
		halfValve.set(ccd.HalfUp.get());
		shootValve.set(ccd.ShootOut.get());
		spinArray.update(ccd.SpinSpeed);
	}
	
	public SHCustomController setSpinArray(CBSpeedControllerArrayController spinArray) {
		this.spinArray = spinArray;
		return this;
	}

	public SHCustomController setArmValve(CBDeviceId solenoidId) {
		this.armValve = Cyborg.hardwareAdapter.getSolenoidValve(solenoidId);
		return this;
	}

	public SHCustomController setHalfValve(CBDeviceId solenoidId) {
		this.halfValve = Cyborg.hardwareAdapter.getSolenoidValve(solenoidId);
		return this;
	}

	public SHCustomController setShootValve(CBDeviceId solenoidId) {
		this.shootValve = Cyborg.hardwareAdapter.getSolenoidValve(solenoidId);
		return this;
	}
}
