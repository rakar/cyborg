package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.BehaviorProcessor;
import org.montclairrobotics.cyborg.Cyborg;

public class SHManipBehaviorProcessor extends BehaviorProcessor {

	public SHManipBehaviorProcessor(Cyborg robot) {
		super(robot);
	}
	
	public void update() {
		SHManipRequestStatus rs = (SHManipRequestStatus)robot.manipRequestStatus;
		SHManipControlStatus cs = (SHManipControlStatus)robot.manipControlStatus;
		
		cs.ShootOut.set(rs.ShootOut.get());  
		cs.ArmDown.set(rs.ArmDown.get());	
		cs.HalfUp.set(rs.HalfUp.get());
		
		cs.SpinSpeed = (double)rs.SpinIn.select(-.4, .6, 0); 
		
	}

}
