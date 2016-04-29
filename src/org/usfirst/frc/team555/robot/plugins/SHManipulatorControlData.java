package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBManipulatorControlData;
import org.montclairrobotics.cyborg.utils.CBTriState;

public class SHManipulatorControlData extends CBManipulatorControlData {

	public SHManipulatorControlData() {
	}
	
	public CBTriState ArmDown = new CBTriState();
	public CBTriState HalfUp= new CBTriState();
	public CBTriState ShootOut= new CBTriState();
	public double SpinSpeed;
}
