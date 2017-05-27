package org.usfirst.frc.team555.stronghold;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBNavXYawSource;
import org.montclairrobotics.cyborg.mappers.CBCustomMapper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SHSensorMapper extends CBCustomMapper {
	SHRobot robot;
	CBStdDriveRequestData drd = (CBStdDriveRequestData) Cyborg.requestData.driveData;
	SHCustomRequestData crd = (SHCustomRequestData) Cyborg.requestData;

	CBDashboardChooser<Integer> autoChooser;
	CBDashboardChooser<Integer> autoSide;
	CBContourReport contourRpt;
	CBNavXYawSource navxYawSource;
	CBEncoder leftEncoder;
	CBEncoder rightEncoder;

	public SHSensorMapper(SHRobot robot) {
		super(robot);
		this.robot = robot;	
	}

	@Override
	public void update() {

		if(autoChooser!=null)
			crd.selectedAuto = autoChooser.getSelected();
		if(autoSide!=null)
			crd.selectedSide = autoSide.getSelected();
		
		drd.gyroLockValue = navxYawSource.get();
		crd.targetX = contourRpt.centerX;
		crd.targetY = contourRpt.centerY;
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

	public SHSensorMapper setGyroLockSource(CBDeviceId navxId) {
		navxYawSource = new CBNavXYawSource(navxId); 
		return this;
	}

	@SuppressWarnings("unchecked")
	public SHSensorMapper setAutoChooser(CBDeviceId chooserId) {
		this.autoChooser = (CBDashboardChooser<Integer>)Cyborg.hardwareAdapter.getDevice(chooserId);
		return this;
	}

	@SuppressWarnings("unchecked")
	public SHSensorMapper setAutoSide(CBDeviceId chooserId) {
		this.autoSide = (CBDashboardChooser<Integer>)Cyborg.hardwareAdapter.getDevice(chooserId);
		return this;
	}

	public SHSensorMapper setContourRpt(CBDeviceId contourRpt) {
		this.contourRpt = Cyborg.hardwareAdapter.getContourReport(contourRpt);
		return this;
	}

	public SHSensorMapper setDriveEncoders(CBDeviceId leftEncoder, CBDeviceId rightEncoder) {
		this.leftEncoder = Cyborg.hardwareAdapter.getEncoder(leftEncoder);
		this.rightEncoder = Cyborg.hardwareAdapter.getEncoder(rightEncoder);
		return this;
	}

}
 