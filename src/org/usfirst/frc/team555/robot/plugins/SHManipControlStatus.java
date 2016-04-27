package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBManipControlStatus;
import org.montclairrobotics.cyborg.utils.CBTriState;

public class SHManipControlStatus extends CBManipControlStatus {

	public SHManipControlStatus() {
	}
	
	public CBTriState ArmDown;
	public CBTriState HalfUp;
	public CBTriState ShootOut;
	public double SpinSpeed;
}
