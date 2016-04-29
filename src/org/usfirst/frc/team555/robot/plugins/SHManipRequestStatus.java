package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBManipRequestStatus;
import org.montclairrobotics.cyborg.utils.*;

public class SHManipRequestStatus extends CBManipRequestStatus {
	public CBTriState ArmDown = new CBTriState();
	public CBTriState HalfUp = new CBTriState();
	public CBTriState ShootOut = new CBTriState();
	public CBTriState SpinIn = new CBTriState();
}
