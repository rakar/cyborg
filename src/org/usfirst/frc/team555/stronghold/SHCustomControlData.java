package org.usfirst.frc.team555.stronghold;

import org.montclairrobotics.cyborg.data.CBControlData;
import org.montclairrobotics.cyborg.utils.CBTriState;

public class SHCustomControlData extends CBControlData {

	public CBTriState ArmDown	= new CBTriState();
	public CBTriState HalfUp	= new CBTriState();
	public CBTriState ShootOut	= new CBTriState();
	public double SpinSpeed;

}
