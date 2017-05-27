package org.usfirst.frc.team555.stronghold;

import org.montclairrobotics.cyborg.data.CBRequestData;
import org.montclairrobotics.cyborg.utils.*;

public class SHCustomRequestData extends CBRequestData {

	// Operator Mapper Output
	public boolean ArmUp;
	public boolean ArmHalfUp;
	public boolean ArmDown;

	public CBTriState fireShooter = new CBTriState();
	public boolean autoSteer = false;
	public double autoSteerX = -1;
	public double autoSteerY = -1;

	public boolean spinUpShooter = false;
	public boolean intake = false;

	// Sensor Mapper Output
	public int selectedAuto = -1;
	public int selectedSide = -1;
	public double targetX = -1;
	public double targetY = -1;
	
	public boolean pidTuneEnable = false;
	public boolean pidTuneCycle = false;
	
	public double leftDriveEncoder;
	public double rightDriveEncoder;
	public double leftDriveEncoderSpeed;
	public double rightDriveEncoderSpeed;
	public boolean rightDriveEncoderDir;
	public boolean leftDriveEncoderDir;
}
