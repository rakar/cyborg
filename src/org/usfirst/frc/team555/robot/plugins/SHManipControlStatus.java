package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBManipControlStatus;
import org.montclairrobotics.cyborg.utils.CBTriState;

public class SHManipControlStatus extends CBManipControlStatus {

	public SHManipControlStatus() {
	}
	
	public CBTriState ArmDown = new CBTriState();
	public CBTriState HalfUp= new CBTriState();
	public CBTriState ShootOut= new CBTriState();
	public double SpinSpeed;
}
