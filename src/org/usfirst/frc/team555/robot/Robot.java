
package org.usfirst.frc.team555.robot;

import org.montclairrobotics.cyborg.*;
import org.montclairrobotics.cyborg.plugins.DifferentialDriveController;
import org.montclairrobotics.cyborg.plugins.GeneralDriveBehaviorProcessor;
import org.montclairrobotics.cyborg.plugins.GeneralDriveRequestStatus;
import org.montclairrobotics.cyborg.plugins.ArcadeDriveRequestMapper;
import org.montclairrobotics.cyborg.plugins.DifferentialDriveControlStatus;
import org.montclairrobotics.cyborg.utils.NavX;
import org.montclairrobotics.cyborg.utils.NavXYawSource;
import org.montclairrobotics.cyborg.utils.PID;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends Cyborg {
	AHRS ahrs;
	NavX navx;
	
	@Override
	public void cyborgInit() {
		
		// Declare NavX Unit
		ahrs = new AHRS(SPI.Port.kMXP);
		navx = new NavX(ahrs);

		// Configure "built-in" DriverStationInterface
		this.setJoystickCount(2);
		
		//
		// Input Mapper Initialization
		//
		
		// Tank Drive...
		//this.driveRequestMappers.add(
		//		new TankDriveRequestMapper(this, 1, 1, 2, 1) // Add the basic mapper
		//		.setDeadZone(0.1)			// Activate Deadzone
		//		.setGyroLockButton(1, 1)	// Set GyroLock button
		//		);
		
		// Arcade Drive...
		this.driveRequestMappers.add(
				new ArcadeDriveRequestMapper(this, 1, 1, 2) // Add the basic mapper
				.setDeadZone(0.1)			// Activate Deadzone
				.setGyroLockButton(1, 1)	// Set GyroLock button
				);
		this.manipRequestMappers.add(new ManipRequestMapper(this));
		this.robotSensorMappers.add(new RobotSensorMapper(this));
		
		//
		// Status Initialization
		//
		this.driveRequestStatus    = new GeneralDriveRequestStatus();
		this.manipRequestStatus    = new ManipRequestStatus();
		this.robotSensorStatus     = new RobotSensorStatus();	
		this.feedbackControlStatus = new FeedbackControlStatus();
		this.driveControlStatus    = new DifferentialDriveControlStatus();
		this.manipControlStatus    = new ManipControlStatus();	
		this.processorStatus       = new ProcessorStatus();
	
		//
		// Processors
		//
		this.ruleProcessors.add(new RuleProcessor(this));
		this.behaviorProcessors.add(
				new GeneralDriveBehaviorProcessor(this)
				.setGyroLockTracker(
						new NavXYawSource(navx), 
						new PID(0.2, 0.0, 2.0)
						.setInLimits(-180, 180) // assumes navx source in degrees
					)
				);
		
		//
		// Output Controller Initialization
		//
		this.feedbackControllers.add(new FeedbackController(this));
		this.driveControllers.add(
				new DifferentialDriveController(this)
				.addLeftSpeedController (new Talon(1))  // Left:  1 PWM: 1
				.addLeftSpeedController (new Talon(2))  // Left:  2 PWM: 2
				.addRightSpeedController(new Talon(3))  // Right: 1 PWM: 3
				.addRightSpeedController(new Talon(4))  // Right: 2 PWM: 4
				);
		this.manipControllers.add(new ManipController(this));		
	}
}
