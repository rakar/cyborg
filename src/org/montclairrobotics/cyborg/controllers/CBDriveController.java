package org.montclairrobotics.cyborg.controllers;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class CBDriveController extends CBRobotController {

	protected ArrayList<CBDriveModule> driveModules = new ArrayList<>();
	protected CBDriveMode driveMode;
	protected double controlPeriod = 1/50.0;
	

	public CBDriveController(Cyborg robot) {
		super(robot);
	}

	public CBDriveController addDriveModule(CBDriveModule driveModule) {
		driveModules.add(driveModule);
		updateDriveMode(driveModule);
		return this;
	}
	
	/**
	 * @return the driveMode
	 */
	public CBDriveMode getDriveMode() {
		return driveMode;
	}

	protected void updateDriveMode(CBDriveModule driveModule) {
		if (driveMode==null) {
			driveMode = driveModule.getDriveMode();
		} else {
			if (driveModule.getDriveMode()!=driveMode) {
				driveMode = CBDriveMode.Conflict;
				SmartDashboard.putString("Drivemode","Conflict");
			}
		}
	}
	
	/* 
	 * The purpose of this function is to do "last minute" hardware configuration 
	 * required after construction. 
	 * configHardware() to build whatever required WPI infrastructure is required.
	 * This concept is not fleshed out yet.  
	 */
	@Override
	public void configHardware() {
		
	}

	/**
	 * @return the controlPeriod
	 */
	public double getControlPeriod() {
		return controlPeriod;
	}

	/**
	 * @param controlPeriod the controlPeriod to set
	 */
	public CBDriveController setControlPeriod(double controlPeriod) {
		this.controlPeriod = controlPeriod;
		return this;
	}

}
