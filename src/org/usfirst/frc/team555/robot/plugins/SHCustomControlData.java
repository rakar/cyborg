package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBCustomControlData;
import org.montclairrobotics.cyborg.utils.CBTriState;

public class SHCustomControlData extends CBCustomControlData {

	public SHCustomControlData() {
	}
	
	public CBTriState ArmDown	= new CBTriState();
	public CBTriState HalfUp	= new CBTriState();
	public CBTriState ShootOut	= new CBTriState();
	public double SpinSpeed;
}
