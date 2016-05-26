package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBGeneralRequestData;
import org.montclairrobotics.cyborg.utils.*;

public class SHGeneralRequestData extends CBGeneralRequestData {

	public boolean ArmUp;
	public boolean ArmHalfUp;
	public boolean ArmDown;

	public CBTriState ShootOut = new CBTriState();
	public CBTriState SpinIn = new CBTriState();
	public Integer selectedAuto = new Integer(-1);
	public boolean autoSteer = false;
	public double targetX = -1;
	public double targetY = -1;
}
