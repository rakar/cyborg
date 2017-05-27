package org.montclairrobotics.cyborg.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.utils.CB2DVector;

public class CBMecanumDriveController extends CBDriveController {

	public CBMecanumDriveController(Cyborg robot) {
		super(robot);
	}

	@Override
	public void update() {
		if(Cyborg.controlData.driveData.active) {
			if(Cyborg.controlData.driveData instanceof CBStdDriveControlData) {
				// TODO: Implement, like, mecanum calculation, 
				// asymmetric configurations require an overall "group" calculation 
				// instead of a "single" module calculation
				
				CBStdDriveControlData dcd = (CBStdDriveControlData)Cyborg.controlData.driveData;
				for(CBDriveModule dm:driveModules) {
					double power = calculate(dm, dcd.direction, dcd.rotation);
					dm.update(power);
				}				

			} else {
				
				System.out.println("Error: Invalid DriveControlStatus for DifferentialDriveController");
				
			}
		}
	}

	// TODO: Implement, like, Mecanum calculation, the below is differential man!!!
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
	
	public CBMecanumDriveController addDriveModule(CBDriveModule driveModule) {
		return (CBMecanumDriveController)super.addDriveModule(driveModule);
	}

	@Override
	public CBMecanumDriveController setControlPeriod(double controlPeriod) {
		return (CBMecanumDriveController)super.setControlPeriod(controlPeriod);
	}
}
