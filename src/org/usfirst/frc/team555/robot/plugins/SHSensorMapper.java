package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBNavXYawSource;
import org.montclairrobotics.cyborg.mappers.CBCustomMapper;
import org.usfirst.frc.team555.robot.Robot;

public class SHSensorMapper extends CBCustomMapper {
	Robot robot;
	CBStdDriveRequestData drd = (CBStdDriveRequestData) Cyborg.driveRequestData;
	SHCustomRequestData crd = (SHCustomRequestData) Cyborg.customRequestData;

	CBDashboardChooser<Integer> autoChooser;
	CBDashboardChooser<Integer> autoSide;
	CBContourReport contourRpt;
	CBNavXYawSource navxYawSource;

	public SHSensorMapper(Robot robot) {
		super(robot);
		this.robot = robot;	
		//this.grd = (SHCustomRequestData) Cyborg.customRequestData;
	}

	@Override
	public void update() {

		if(autoChooser!=null)
			crd.selectedAuto = autoChooser.getSelected();
		if(autoSide!=null)
			crd.selectedSide = autoSide.getSelected();
		
		drd.gyroLockSource = navxYawSource.get();
		crd.targetX = contourRpt.centerX;
		crd.targetY = contourRpt.centerY;
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

}
 