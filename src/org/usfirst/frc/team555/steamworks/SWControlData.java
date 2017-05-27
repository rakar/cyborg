package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.data.CBControlData;
import org.montclairrobotics.cyborg.utils.CBTriState;

public class SWControlData extends CBControlData {

	public CBTriState gearLeftOpen;
	public CBTriState gearRightOpen;
	
	public boolean climb;
}
