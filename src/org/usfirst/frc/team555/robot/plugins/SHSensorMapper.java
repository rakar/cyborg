package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBGeneralMapper;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.usfirst.frc.team555.robot.Robot;

public class SHSensorMapper extends CBGeneralMapper {
	Robot robot;
	SHGeneralRequestData grd;
	CBDashboardChooser<Integer> autoChooser;
	CBContourReport contourRpt;

	public SHSensorMapper(Robot robot) {
		super(robot);
		
		this.robot = robot;	
		this.grd = (SHGeneralRequestData) Cyborg.generalRequestData;
		
		
		//this.contourRpt = Cyborg.hardwareAdapter.getContourReport(robot.devices.visionPipeline);
	}

	@Override
	public void update() {

		if(autoChooser!=null)
			grd.selectedAuto = autoChooser.getSelected();

		grd.targetX = contourRpt.centerX;
		grd.targetY = contourRpt.centerY;
		//SmartDashboard.putNumber("grd.targetX", grd.targetX);

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
 