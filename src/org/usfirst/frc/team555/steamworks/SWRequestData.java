package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.data.CBRequestData;

public class SWRequestData extends CBRequestData {
	
	// Operator
	public boolean gearAutoClose;
	public boolean gearAutoOpen;
	public boolean gearManualLeftClose;
	public boolean gearManualLeftOpen;
	public boolean gearManualRightClose;
	public boolean gearManualRightOpen;
	public boolean climb;
	
	// Sensors 
	public double leftDriveEncoder;
	public double rightDriveEncoder;
	public double leftDriveEncoderSpeed;
	public double rightDriveEncoderSpeed;
	public boolean rightDriveEncoderDir;
	public boolean leftDriveEncoderDir;
	public boolean leftOpenSwitch;
	public boolean leftCloseSwitch;
	public boolean rightOpenSwitch;
	public boolean rightCloseSwitch;
	
	public int selectedAuto = -1;
	public int selectedAlliance = -1;
}
