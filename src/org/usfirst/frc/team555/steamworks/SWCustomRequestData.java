package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.data.CBCustomRequestData;

public class SWCustomRequestData extends CBCustomRequestData {
	
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
	public int selectedAuto = -1;
}
