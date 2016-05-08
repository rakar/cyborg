package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBGeneralMapper;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.usfirst.frc.team555.robot.Robot;

public class SHSensorMapper extends CBGeneralMapper {
	Robot robot;
	CBDashboardChooser<Integer> autoChooser;
	SHGeneralRequestData grd;
	CBContourReport contourRpt;

	@SuppressWarnings("unchecked")
	public SHSensorMapper(Robot robot) {
		super(robot);
		
		this.robot = robot;	
		this.grd = (SHGeneralRequestData) Cyborg.generalRequestData;
		this.autoChooser = (CBDashboardChooser<Integer>)Cyborg.hardwareAdapter.getDevice(robot.devices.autoSelect);
		
	}

	@Override
	public void update() {

		grd.selectedAuto = autoChooser.getSelected();
		grd.targetX = -1;
		if(contourRpt.largest>=0) {
			grd.targetX = contourRpt.centerX[contourRpt.largest];
		}
		
	}
	
	

}
