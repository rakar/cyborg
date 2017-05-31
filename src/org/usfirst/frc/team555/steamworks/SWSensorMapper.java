package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.controllers.CBDriveController;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBDigitalInput;
import org.montclairrobotics.cyborg.devices.CBNavXYawSource;
import org.montclairrobotics.cyborg.mappers.CBCustomMapper;

public class SWSensorMapper extends CBCustomMapper {
	SWRobot robot;
	CBStdDriveRequestData drd = (CBStdDriveRequestData) Cyborg.requestData.driveData;
	SWRequestData rd = (SWRequestData) Cyborg.requestData;

	CBDashboardChooser<Integer> autoChooser;
	CBDashboardChooser<Integer> allianceChooser;
	CBNavXYawSource navxYawSource;
	//CBEncoder leftEncoder;
	//CBEncoder rightEncoder;
	CBDriveController drivetrainController; 
	CBDigitalInput leftOpenSwitch;
	CBDigitalInput leftCloseSwitch;
	CBDigitalInput rightOpenSwitch;
	CBDigitalInput rightCloseSwitch;
	

	public SWSensorMapper(SWRobot robot) {
		super(robot);
		this.robot = robot;
	}
	
	@Override
	public void update() {
		if(autoChooser!=null)
			rd.selectedAuto = autoChooser.getSelected();
		if(allianceChooser!=null)
			rd.selectedAlliance = allianceChooser.getSelected();
		
		drd.gyroLockValue = navxYawSource.get();
		
		//if(leftEncoder!=null) {
		//	rd.leftDriveEncoder = leftEncoder.get();
		//	rd.rightDriveEncoder = rightEncoder.get();
		//	rd.leftDriveEncoderSpeed = leftEncoder.getRate();
		//	rd.rightDriveEncoderSpeed = rightEncoder.getRate();
		//	SmartDashboard.putNumber("LeftEncoderGet:", rd.leftDriveEncoder);
		//	SmartDashboard.putNumber("RightEncoderGet:", rd.rightDriveEncoder);
		//	SmartDashboard.putNumber("LeftEncoderSpeed:", rd.leftDriveEncoderSpeed);
		//	SmartDashboard.putNumber("RightEncoderSpeed:", rd.rightDriveEncoderSpeed);
		//}	
		
		
		
		if(leftOpenSwitch!=null) {
			rd.leftOpenSwitch 	= leftOpenSwitch.get();
			rd.leftCloseSwitch	= leftCloseSwitch.get();
			rd.rightOpenSwitch	= rightOpenSwitch.get();
			rd.rightCloseSwitch	= rightCloseSwitch.get();
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

	@SuppressWarnings("unchecked")
	public SWSensorMapper setAllianceChooser(CBDeviceId chooserId) {
		this.allianceChooser = (CBDashboardChooser<Integer>)Cyborg.hardwareAdapter.getDevice(chooserId);
		return this;
	}

	//public SWSensorMapper setDriveEncoders(CBDeviceId leftEncoder, CBDeviceId rightEncoder) {
	//	this.leftEncoder = Cyborg.hardwareAdapter.getEncoder(leftEncoder);
	//	this.rightEncoder = Cyborg.hardwareAdapter.getEncoder(rightEncoder);
	//	return this;
	//}
	
	public SWSensorMapper setDrivetrain(CBDriveController driveTrainController) {
		this.drivetrainController = driveTrainController;
		return this;
	}
	
	public SWSensorMapper setLimitSwitches(CBDeviceId leftOpenSwitch, CBDeviceId leftCloseSwitch, 
			CBDeviceId rightOpenSwitch, CBDeviceId rightCloseSwitch) {
		this.leftOpenSwitch = Cyborg.hardwareAdapter.getDigitalInput(leftOpenSwitch);
		this.leftCloseSwitch = Cyborg.hardwareAdapter.getDigitalInput(leftCloseSwitch);
		this.rightOpenSwitch = Cyborg.hardwareAdapter.getDigitalInput(rightOpenSwitch);
		this.rightCloseSwitch = Cyborg.hardwareAdapter.getDigitalInput(rightCloseSwitch);
		return this;
	}

}
