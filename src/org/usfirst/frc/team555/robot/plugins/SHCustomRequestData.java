package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBCustomRequestData;
import org.montclairrobotics.cyborg.utils.*;

public class SHCustomRequestData extends CBCustomRequestData {

	// Operator Mapper Output
	public boolean ArmUp;
	public boolean ArmHalfUp;
	public boolean ArmDown;

	public CBTriState fireShooter = new CBTriState();
	public boolean autoSteer = false;

	public boolean spinUpShooter = false;
	public boolean intake = false;

	// Sensor Mapper Output
	public Integer selectedAuto = new Integer(-1);
	public double targetX = -1;
	public double targetY = -1;
}
