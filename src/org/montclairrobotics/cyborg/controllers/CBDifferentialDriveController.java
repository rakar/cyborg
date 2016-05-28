package org.montclairrobotics.cyborg.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.assemblies.CBSpeedControllerArrayController;
import org.montclairrobotics.cyborg.data.CBDifferentialDriveControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.utils.CB2DVector;
import org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.CBRobotController;

public class CBDifferentialDriveController extends CBRobotController {

	//private CBDriveModule leftDriveModule;
	//private CBDriveModule rightDriveModule;
	protected ArrayList<CBDriveModule> driveModules;
	protected CBDriveMode driveMode;
	protected double controlPeriod = 1/50.0;
	
	
	public CBDifferentialDriveController(Cyborg robot) {
		super(robot);
		//mode = CBDriveMode.Power;
	}
	
	@Override
	public void update() {
		if(Cyborg.driveControlData.active) {
			if(Cyborg.driveControlData instanceof CBDifferentialDriveControlData) {

				CBDifferentialDriveControlData status = (CBDifferentialDriveControlData)Cyborg.driveControlData;
				for(CBSpeedControllerArrayController c:driveModules.get(0).getControllerArrays()) c.update(status.leftPower);
				for(CBSpeedControllerArrayController c:driveModules.get(1).getControllerArrays()) c.update(status.rightPower);
	
			} else if(Cyborg.driveControlData instanceof CBStdDriveControlData) {
				
				CBStdDriveControlData dcd = (CBStdDriveControlData)Cyborg.driveControlData;
				for(CBDriveModule dm:driveModules) {
					double power = calculate(dm, dcd.direction, dcd.rotation);
					for(CBSpeedControllerArrayController c:dm.getControllerArrays()) {
						c.update(power);
					}
				}				

			} else {
				
				System.out.println("Error: Invalid DriveControlStatus for DifferentialDriveController");
				
			}
		}
	}
	
	protected double calculate(CBDriveModule module, CB2DVector direction, double rotation) {
		double res = 0;

		switch (driveMode) {
		case Power:
		{
			CB2DVector diff = new CB2DVector(0,direction.getY()+Math.signum(module.getPosition().getX())*rotation);
			res = module.getOrientationVector().dot(diff);
		}
			break;
		case Speed:
		{
			CB2DVector pos = module.getPosition();
			CB2DVector targetPosition = 
					pos.scaledRotate(rotation, controlPeriod)
					.scaledTranslate(direction, controlPeriod);
			CB2DVector diff = pos.sub(targetPosition);
			res = module.getOrientationVector().dot(diff);
		}
			break;
		case Conflict:
		default:
			break;
		}
				
		return res;
	}
	
	public CBDifferentialDriveController addDriveModule(CBDriveModule driveModule) {
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
	public CBDifferentialDriveController setControlPeriod(double controlPeriod) {
		this.controlPeriod = controlPeriod;
		return this;
	}
}
