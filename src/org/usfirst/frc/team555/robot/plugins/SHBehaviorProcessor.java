package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.BehaviorProcessor;
import org.montclairrobotics.cyborg.Cyborg;

public class SHBehaviorProcessor extends BehaviorProcessor {

	public SHBehaviorProcessor(Cyborg robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		SHManipRequestStatus rs = (SHManipRequestStatus)robot.manipRequestStatus;
		SHManipControlStatus cs = (SHManipControlStatus)robot.manipControlStatus;
		
		cs.ShootOut = rs.ShootOut;  // single button
		cs.ShootIn  = rs.ShootIn; 
		cs.ArmUp    = rs.ArmUp; 	// press events to toggle state
		cs.ArmDown  = rs.ArmDown;	//
		cs.HalfDown = rs.HalfDown;
		cs.HalfUp   = rs.HalfUp;
		
		if(rs.SpinIn) cs.SpinSpeed = -.4;
		else if(rs.SpinOut) cs.SpinSpeed = .6;
		else cs.SpinSpeed = 0;
		
	}

}
