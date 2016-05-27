package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBGeneralMapper;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBNavXYawSource;
import org.montclairrobotics.cyborg.plugins.CBStdDriveRequestData;
import org.usfirst.frc.team555.robot.Robot;

public class SHSensorMapper extends CBGeneralMapper {
	Robot robot;
	CBStdDriveRequestData drd;
	SHCustomRequestData grd;
	
	CBDashboardChooser<Integer> autoChooser;
	CBContourReport contourRpt;
	CBNavXYawSource navxYawSource;

	public SHSensorMapper(Robot robot) {
		super(robot);
		this.robot = robot;	
		this.grd = (SHCustomRequestData) Cyborg.customRequestData;
	}

	@Override
	public void update() {

		if(autoChooser!=null)
			grd.selectedAuto = autoChooser.getSelected();
		drd.gyroLockSource = navxYawSource.get();
		grd.targetX = contourRpt.centerX;
		grd.targetY = contourRpt.centerY;
	}
	
	public SHSensorMapper setGyroLockSource(CBDeviceID navxId) {
		navxYawSource = new CBNavXYawSource(navxId); 
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public SHSensorMapper setAutoChooser(CBDeviceID chooserId) {
		this.autoChooser = (CBDashboardChooser<Integer>)Cyborg.hardwareAdapter.getDevice(chooserId);
		return this;
	}
	
	public SHSensorMapper setContourRpt(CBDeviceID contourRpt) {
		this.contourRpt = Cyborg.hardwareAdapter.getContourReport(contourRpt);
		return this;
	}
	
}
 