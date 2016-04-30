package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBBehavior;
import org.montclairrobotics.cyborg.Cyborg;

public class SHManipulatorBehavior extends CBBehavior {

	public SHManipulatorBehavior(Cyborg robot) {
		super(robot);
	}
	
	public void update() {
		SHManipulatorRequestData rs = (SHManipulatorRequestData)Cyborg.manipulatorRequestData;
		SHManipulatorControlData cs = (SHManipulatorControlData)Cyborg.manipulatorControlData;
		
		cs.ShootOut.set(rs.ShootOut.get());  
		cs.ArmDown.set(rs.ArmDown.get());	
		cs.HalfUp.set(rs.HalfUp.get());
		
		cs.SpinSpeed = (double)rs.SpinIn.select(-0.4, 0.6, 0.0); 
		
	}

}
