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
		
		cs.ShootOut = rs.Shoot;
		if(rs.ArmUp) cs.ArmDown=false;
		if(rs.ArmDown) cs.ArmDown=true;
		if(rs.HalfUp) cs.HalfDown=false;
		if(rs.HalfDown) cs.HalfDown=true;
		if(rs.SpinIn) cs.SpinSpeed = -.4;
		if(rs.SpinOut) cs.SpinSpeed = .6;
		
	}

}
