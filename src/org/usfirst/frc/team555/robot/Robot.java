
package org.usfirst.frc.team555.robot;

import org.montclairrobotics.cyborg.*;
import org.montclairrobotics.cyborg.plugins.*;
import org.montclairrobotics.cyborg.utils.*;
import org.usfirst.frc.team555.robot.plugins.*;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;

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
		//		new TankDriveRequestMapper(this, 0, 1, 1, 1) // Add the basic mapper
		//		.setDeadZone(0.1)			// Activate Deadzone
		//		.setGyroLockButton(1, 1)	// Set GyroLock button
		//		);
		
		// Arcade Drive...
		this.driveRequestMappers.add(
				new ArcadeDriveRequestMapper(this, 0, 1, -1, -1, 0, 0) // Add the basic mapper
				.setDeadZone(0.1)			// Activate Deadzone
				.setGyroLockButton(0, 0)	// Set GyroLock button
				);

		this.manipRequestMappers.add(new SHManipRequestMapper(this));
		//this.robotSensorMappers.add(new RobotSensorMapper(this));
	
		//
		// Status Initialization
		//
		this.driveRequestStatus    = new GeneralDriveRequestStatus();
		this.driveControlStatus    = new GeneralDriveControlStatus();

		this.manipRequestStatus    = new SHManipRequestStatus();
		this.manipControlStatus    = new SHManipControlStatus();	
		
		this.robotSensorStatus     = new RobotSensorStatus();	
		this.feedbackControlStatus = new FeedbackControlStatus();
		this.processorStatus       = new ProcessorStatus();
	
		//
		// Processors
		//
		
		//this.ruleProcessors.add(new RuleProcessor(this));
		
		this.behaviorProcessors.add(
				new GeneralDriveBehaviorProcessor(this)
				.setGyroLockTracker(
						new NavXYawSource(navx), 
						new PID(0.2, 0.0, 2.0)
						.setInputLimits(-180, 180) // assumes navx source in degrees
					)
				);
		//this.behaviorProcessors.add(
		//		new TankDriveBehaviorProcessor(this)
		//		);
		this.behaviorProcessors.add(
				new SHBehaviorProcessor(this)
				);
		
		//
		// Output Controller Initialization
		//
		
		//this.feedbackControllers.add(new FeedbackController(this));
		
		this.driveControllers.add(
				new DifferentialDriveController(this)
				.addLeftSpeedController (new Talon(1))  // Left:  1 PWM: 1
				.addLeftSpeedController (new Talon(2))  // Left:  2 PWM: 2
				.addRightSpeedController(new Talon(3))  // Right: 1 PWM: 3
				.addRightSpeedController(new Talon(4))  // Right: 2 PWM: 4
				);
		
		this.manipControllers.add(new SHManipController(this));
			
	}
	
}
