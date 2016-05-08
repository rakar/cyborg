package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBBehavior;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.plugins.CBGeneralDriveControlData;
import org.montclairrobotics.cyborg.plugins.CBGeneralDriveRequestData;
import org.montclairrobotics.cyborg.utils.CBPIDController;
import org.montclairrobotics.cyborg.utils.CBSource;
import org.montclairrobotics.cyborg.utils.CBTracker;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SHGeneralBehavior extends CBBehavior {

	SHGeneralRequestData grd;
	SHGeneralControlData gcd;
	CBGeneralDriveControlData drd;
	CBTracker xTracker;
	
	public class xTrackerSource implements CBSource {

		@Override
		public double get() {
			return grd.targetX;
		}
		
	}

	public SHGeneralBehavior(Cyborg robot) {
		super(robot);

		grd = (SHGeneralRequestData)Cyborg.generalRequestData;
		gcd = (SHGeneralControlData)Cyborg.generalControlData;
		drd = (CBGeneralDriveControlData)Cyborg.driveControlData;
		xTracker = new CBTracker(
				new xTrackerSource(),
				new CBPIDController(-0.025,0.0,0.0)
				.setOutputLimits(-.3, .3)
				).setTarget(200.0);
	}
	
	public void update() {
		
		
		gcd.ShootOut.set(grd.ShootOut.get());  
		gcd.ArmDown.set(grd.ArmDown.get());	
		gcd.HalfUp.set(grd.HalfUp.get());
		
		gcd.SpinSpeed = (double)grd.SpinIn.select(-0.4, 0.6, 0.0); 
		
		if(grd.autoSteer && grd.targetX>-1) {
			drd.rotation = xTracker.update();
			SmartDashboard.putNumber("autoSteerRotation", drd.rotation);
		}
		
	}

}
