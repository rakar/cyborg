package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBManipulatorRequestData;
import org.montclairrobotics.cyborg.utils.*;

public class SHManipulatorRequestData extends CBManipulatorRequestData {
	public CBTriState ArmDown = new CBTriState();
	public CBTriState HalfUp = new CBTriState();
	public CBTriState ShootOut = new CBTriState();
	public CBTriState SpinIn = new CBTriState();
}
