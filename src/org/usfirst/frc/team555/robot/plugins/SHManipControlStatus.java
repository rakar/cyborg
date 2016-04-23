package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.ManipControlStatus;
import org.montclairrobotics.cyborg.utils.TriState;

public class SHManipControlStatus extends ManipControlStatus {

	public SHManipControlStatus() {
	}
	
	public TriState ArmDown;
	public TriState HalfUp;
	public TriState ShootOut;
	public double SpinSpeed;
}
