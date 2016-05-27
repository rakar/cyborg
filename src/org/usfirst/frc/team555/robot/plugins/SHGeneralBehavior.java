package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBBehavior;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.plugins.CBGeneralDriveControlData;
import org.montclairrobotics.cyborg.utils.CBPIDErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBSource;
import org.montclairrobotics.cyborg.utils.CBTracker;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SHGeneralBehavior extends CBBehavior {

	SHGeneralRequestData grd;
	SHGeneralControlData gcd;
	CBGeneralDriveControlData dcd;
	CBTracker xTracker;
	CBTracker yTracker;
	
	public class xTrackerSource implements CBSource {

		@Override
		public double get() {
			return grd.targetX;
		}
	}

	public class yTrackerSource implements CBSource {

		@Override
		public double get() {
			return grd.targetY;
		}
	}

	public SHGeneralBehavior(Cyborg robot) {
		super(robot);

		grd = (SHGeneralRequestData)Cyborg.generalRequestData;
		gcd = (SHGeneralControlData)Cyborg.generalControlData;
		dcd = (CBGeneralDriveControlData)Cyborg.driveControlData;
		
		xTracker = new CBTracker(
				new xTrackerSource(),
				new CBPIDErrorCorrection()
				.setConstants(new double[]{-0.025,0.0,0.0})
				.setOutputLimits(-.3, .3)
				).setTarget(120.0);
		yTracker = new CBTracker(
				new yTrackerSource(),
				new CBPIDErrorCorrection()
				.setConstants(new double[]{ 0.025,0.0,0.0})
				.setOutputLimits(-.3, .3)
				).setTarget(180.0);
	}
	
	public void update() {

		gcd.ShootOut.set(grd.ShootOut.get());  
		gcd.ArmDown.set(grd.ArmDown || grd.ArmHalfUp, grd.ArmUp);	
		gcd.HalfUp.set(grd.ArmHalfUp, grd.ArmDown);

		gcd.SpinSpeed = (double)grd.SpinIn.select(-0.4, 0.6, 0.0); 

		if(grd.autoSteer) {
			dcd.rotation = 0;
			dcd.direction.setXY(0, 0);
			dcd.active=true;
			if(grd.targetX>-1) {		
				dcd.rotation = xTracker.update();
				SmartDashboard.putNumber("autoSteerRotation", dcd.rotation);
			}
			if(grd.targetY>-1) {		
				dcd.direction.setXY(0, yTracker.update());
				SmartDashboard.putNumber("autoSteerForward", dcd.rotation);
			}
		}
	}
}
