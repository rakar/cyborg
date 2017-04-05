package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBNavXYawSource;
import org.montclairrobotics.cyborg.mappers.CBCustomMapper;
import org.usfirst.frc.team555.stronghold.SHSensorMapper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SWSensorMapper extends CBCustomMapper {
	SWRobot robot;
	CBStdDriveRequestData drd = (CBStdDriveRequestData) Cyborg.driveRequestData;
	SWCustomRequestData crd = (SWCustomRequestData) Cyborg.customRequestData;

	CBDashboardChooser<Integer> autoChooser;
	CBNavXYawSource navxYawSource;
	CBEncoder leftEncoder;
	CBEncoder rightEncoder;

	public SWSensorMapper(SWRobot robot) {
		super(robot);
		this.robot = robot;	
	}
	
	@Override
	public void update() {
		if(autoChooser!=null)
			crd.selectedAuto = autoChooser.getSelected();
		drd.gyroLockSource = navxYawSource.get();
		if(leftEncoder!=null) {
			crd.leftDriveEncoder = leftEncoder.get();
			crd.rightDriveEncoder = rightEncoder.get();
			crd.leftDriveEncoderSpeed = leftEncoder.getRate();
			crd.rightDriveEncoderSpeed = rightEncoder.getRate();
			SmartDashboard.putNumber("LeftEncoderGet:", crd.leftDriveEncoder);
			SmartDashboard.putNumber("RightEncoderGet:", crd.rightDriveEncoder);
			SmartDashboard.putNumber("LeftEncoderSpeed:", crd.leftDriveEncoderSpeed);
			SmartDashboard.putNumber("RightEncoderSpeed:", crd.rightDriveEncoderSpeed);
		}	
	}
	
	public SWSensorMapper setGyroLockSource(CBDeviceId navxId) {
		navxYawSource = new CBNavXYawSource(navxId); 
		return this;
	}

	@SuppressWarnings("unchecked")
	public SWSensorMapper setAutoChooser(CBDeviceId chooserId) {
		this.autoChooser = (CBDashboardChooser<Integer>)Cyborg.hardwareAdapter.getDevice(chooserId);
		return this;
	}

	public SWSensorMapper setDriveEncoders(CBDeviceId leftEncoder, CBDeviceId rightEncoder) {
		this.leftEncoder = Cyborg.hardwareAdapter.getEncoder(leftEncoder);
		this.rightEncoder = Cyborg.hardwareAdapter.getEncoder(rightEncoder);
		return this;
	}

}
