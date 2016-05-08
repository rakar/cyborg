package org.usfirst.frc.team555.robot.plugins;

import org.montclairrobotics.cyborg.CBGeneralMapper;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.usfirst.frc.team555.robot.Robot;

public class SHSensorMapper extends CBGeneralMapper {
	Robot robot;
	CBDashboardChooser<Integer> autoChooser;
	SHGeneralRequestData mrd;

	@SuppressWarnings("unchecked")
	public SHSensorMapper(Robot robot) {
		super(robot);
		
		this.robot = robot;	
		this.mrd = (SHGeneralRequestData) Cyborg.generalRequestData;
		//this.autoChooser = (CBDashboardChooser<Integer>)Cyborg.hardwareAdapter.getDevice(robot.devices.autoSelect);
	}

	@Override
	public void update() {

		//mrd.selectedAuto = autoChooser.getSelected();
		
	}
	
	

}
