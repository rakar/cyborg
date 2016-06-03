package org.montclairrobotics.cyborg.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.data.CBDifferentialDriveControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.utils.CB2DVector;

public class CBDifferentialDriveController extends CBDriveController {

	
	public CBDifferentialDriveController(Cyborg robot) {
		super(robot);
	}
	
	@Override
	public void update() {
		if(Cyborg.driveControlData.active) {
			if(Cyborg.driveControlData instanceof CBDifferentialDriveControlData) {

				CBDifferentialDriveControlData status = (CBDifferentialDriveControlData)Cyborg.driveControlData;
				driveModules.get(0).update(status.leftPower);
				driveModules.get(1).update(status.rightPower);
	
			} else if(Cyborg.driveControlData instanceof CBStdDriveControlData) {
				
				CBStdDriveControlData dcd = (CBStdDriveControlData)Cyborg.driveControlData;
				for(CBDriveModule dm:driveModules) {
					double power = calculate(dm, dcd.direction, dcd.rotation);
					dm.update(power);
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
		return (CBDifferentialDriveController)super.addDriveModule(driveModule);
	}

	@Override
	public CBDifferentialDriveController setControlPeriod(double controlPeriod) {
		return (CBDifferentialDriveController)super.setControlPeriod(controlPeriod);
	}
}