package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBBehaviorProcessor;
import org.montclairrobotics.cyborg.Cyborg;

public class SHManipBehaviorProcessor extends CBBehaviorProcessor {

	public SHManipBehaviorProcessor(Cyborg robot) {
		super(robot);
	}
	
	public void update() {
		SHManipRequestStatus rs = (SHManipRequestStatus)Cyborg.manipRequestStatus;
		SHManipControlStatus cs = (SHManipControlStatus)Cyborg.manipControlStatus;
		
		cs.ShootOut.set(rs.ShootOut.get());  
		cs.ArmDown.set(rs.ArmDown.get());	
		cs.HalfUp.set(rs.HalfUp.get());
		
		cs.SpinSpeed = (double)rs.SpinIn.select(-0.4, 0.6, 0.0); 
		
	}

}
