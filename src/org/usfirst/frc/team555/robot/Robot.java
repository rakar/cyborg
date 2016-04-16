
package org.usfirst.frc.team555.robot;

import org.montclairrobotics.cyborg.*;
import org.montclairrobotics.cyborg.plugins.DifferentialDriveController;
import org.montclairrobotics.cyborg.plugins.TankDriveBehaviorProcessor;
import org.montclairrobotics.cyborg.plugins.TankDriveControlStatus;
import org.montclairrobotics.cyborg.plugins.TankDriveRequestMapper;
import org.montclairrobotics.cyborg.plugins.TankDriveRequestStatus;

import edu.wpi.first.wpilibj.Talon;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends Cyborg {
	//SHDriveRequestMapper driveRequestMapper = new SHDriveRequestMapper(this);;
	//SHManipRequestMapper manipRequestMapper = new SHManipRequestMapper(this);

	@Override
	public void cyborgInit() {

		// Configure "built-in" DriverStationInterface
		this.setJoystickCount(2);
		
		//
		// Input Mapper Initialization
		//
		this.driveRequestMappers.add(new TankDriveRequestMapper(this));
		this.manipRequestMappers.add(new ManipRequestMapper(this));
		this.robotSensorMappers.add(new RobotSensorMapper(this));
		
		//
		// Status Initialization
		//
		this.driveRequestStatus = new TankDriveRequestStatus();
		this.manipRequestStatus = new ManipRequestStatus();
		this.robotSensorStatus = new RobotSensorStatus();	
		this.feedbackControlStatus = new FeedbackControlStatus();
		this.driveControlStatus = new TankDriveControlStatus();
		this.manipControlStatus = new ManipControlStatus();	
	
		//
		// Processors
		//
		this.ruleProcessors.add(new RuleProcessor(this));
		this.behaviorProcessors.add(new TankDriveBehaviorProcessor(this));
		
		//
		// Output Controller Initialization
		//
		this.feedbackControllers.add(new FeedbackController(this));
		this.driveControllers.add(
				new DifferentialDriveController(this)
				.addLeftSpeedController(new Talon(1))  // Left  1 - 1
				.addLeftSpeedController(new Talon(2))  // Left  2 - 2
				.addRightSpeedController(new Talon(3)) // Right 1 - 3
				.addRightSpeedController(new Talon(4)) // Right 2 - 4
				);
		this.manipControllers.add(new ManipController(this));
		
	}
	

}
