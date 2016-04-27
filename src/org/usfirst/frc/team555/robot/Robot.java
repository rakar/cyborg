
package org.usfirst.frc.team555.robot;

import org.montclairrobotics.cyborg.*;
import org.montclairrobotics.cyborg.devices.CBMotorController;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBNavXYawSource;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.plugins.*;
import org.montclairrobotics.cyborg.utils.*;
import org.usfirst.frc.team555.robot.plugins.*;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SPI;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends Cyborg {
	
	//
	// List Custom Hardware Devices...
	// This should include all of the active devices other than the Driver's Station
	//
	public enum Device {
		NAVX, 
		ARM_VALVE, HALF_VALVE, SHOOT_VALVE, 
		SPIN_LEFT, SPINT_RIGHT,
		DRIVE_LEFT_1, DRIVE_LEFT_2, 
		DRIVE_RIGHT_1, DRIVE_RIGHT_2}; 

	
	
	@Override
	public void cyborgInit() {
		
		
		// Configure Custom Hardware
		CBHardwareAdapter<Device> ha = new CBHardwareAdapter<>(this);
		this.hardwareAdapter = ha;
		
		// Configure Driver Station data collection
		ha.setJoystickCount(2);		
		
		ha.add(Device.NAVX,          new CBNavX(SPI.Port.kMXP));		

		ha.add(Device.ARM_VALVE,     new CBSolenoid(0));
		ha.add(Device.HALF_VALVE,    new CBSolenoid(2));
		ha.add(Device.SHOOT_VALVE,   new CBSolenoid(1));
		
		ha.add(Device.SPIN_LEFT,     new CBMotorController(new Talon(5)));
		ha.add(Device.SPINT_RIGHT,   new CBMotorController(new Talon(0)));
		
		ha.add(Device.DRIVE_LEFT_1,  new CBMotorController(new Talon(1)));
		ha.add(Device.DRIVE_LEFT_2,  new CBMotorController(new Talon(3)));
		ha.add(Device.DRIVE_RIGHT_1, new CBMotorController(new Talon(2)));
		ha.add(Device.DRIVE_RIGHT_2, new CBMotorController(new Talon(4)));

		
		
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
				new CBArcadeDriveRequestMapper(
						this, 
						new CBJoystickIndex(0, 1),
						CBJoystickIndex.undefined(),
						new CBJoystickIndex(0, 0)
						) // Add the basic mapper
				.setDeadZone(0.2)			// Activate Deadzone
				.setGyroLockButton(new CBJoystickIndex(0, 1))	// Set GyroLock button
				);

		this.manipRequestMappers.add(new SHManipRequestMapper(this));
		//this.robotSensorMappers.add(new RobotSensorMapper(this));
	
		
		
		//
		// Status Initialization
		//
		this.driveRequestStatus    = new CBGeneralDriveRequestStatus();
		this.driveControlStatus    = new CBGeneralDriveControlStatus();

		this.manipRequestStatus    = new SHManipRequestStatus();
		this.manipControlStatus    = new SHManipControlStatus();	
		
		this.robotSensorStatus     = new CBRobotSensorStatus();	
		this.feedbackControlStatus = new CBFeedbackControlStatus();
		this.processorStatus       = new CBProcessorStatus();
	
		
		
		//
		// Processors
		//
		
		//this.ruleProcessors.add(new RuleProcessor(this));
		this.behaviorProcessors.add(
				new CBGeneralDriveBehaviorProcessor(this)
				.setGyroLockTracker(
						new CBNavXYawSource(getHA().getNavX(Device.NAVX)), 
						new CBPID(0.2, 0.0, 2.0)
						.setInputLimits(-180, 180) // assumes navx source in degrees
					)
				);
		//this.behaviorProcessors.add(
		//		new TankDriveBehaviorProcessor(this)
		//		);
		this.behaviorProcessors.add(
				new SHManipBehaviorProcessor(this)
				);
		
		
		//
		// Output Controller Initialization
		//
		
		//this.feedbackControllers.add(new FeedbackController(this));		
		this.driveControllers.add(
				new CBDifferentialDriveController(this)
				.addLeftSpeedController (ha.getMotorController(Device.DRIVE_LEFT_1))
				.addLeftSpeedController (ha.getMotorController(Device.DRIVE_LEFT_2))
				.addRightSpeedController(ha.getMotorController(Device.DRIVE_RIGHT_1))
				.addRightSpeedController(ha.getMotorController(Device.DRIVE_RIGHT_1))
				.setRightDirection(-1)
				);
		this.manipControllers.add(new SHManipController(this));
			
	}
	
}
