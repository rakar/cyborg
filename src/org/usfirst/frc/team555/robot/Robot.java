
package org.usfirst.frc.team555.robot;

import org.montclairrobotics.cyborg.*;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBMotorController;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBNavXYawSource;
import org.montclairrobotics.cyborg.devices.CBPov;
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
		DRIVE_MOTOR_LEFT_1, DRIVE_MOTOR_LEFT_2, 
		DRIVE_MOTOR_RIGHT_1, DRIVE_MOTOR_RIGHT_2,
		GYROLOCK_BUTTON, 
		FORWARD_AXIS, ROTATION_AXIS, FORWARD2_AXIS,
		SHOOT_BUTTON,ARMDOWN_BUTTON,ARMUP_BUTTON,HALFDOWN_BUTTON,HALFUP_BUTTON,
		SPIN_POV,
		}; 

	
	
	@Override
	public void cyborgInit() {
		
		
		// Configure Custom Hardware
		CBHardwareAdapter<Device> ha = new CBHardwareAdapter<Device>(this, Device.values().length);
		Cyborg.hardwareAdapter = ha;
		
		// Configure Driver Station data collection
		ha.setJoystickCount(2);		
		
		ha.add(Device.NAVX,          		new CBNavX(SPI.Port.kMXP));		

		ha.add(Device.ARM_VALVE,     		new CBSolenoid(0));
		ha.add(Device.SHOOT_VALVE,   		new CBSolenoid(1));
		ha.add(Device.HALF_VALVE,    		new CBSolenoid(2));
		
		ha.add(Device.SPIN_LEFT,     		new CBMotorController(new Talon(5)));
		ha.add(Device.SPINT_RIGHT,			new CBMotorController(new Talon(0)));
		
		ha.add(Device.DRIVE_MOTOR_LEFT_1,	new CBMotorController(new Talon(1)));
		ha.add(Device.DRIVE_MOTOR_LEFT_2,	new CBMotorController(new Talon(3)));
		ha.add(Device.DRIVE_MOTOR_RIGHT_1,	new CBMotorController(new Talon(2)));
		ha.add(Device.DRIVE_MOTOR_RIGHT_2,	new CBMotorController(new Talon(4)));
		ha.add(Device.GYROLOCK_BUTTON,		new CBButton(0, 1));
		ha.add(Device.FORWARD_AXIS, 		new CBAxis(0,1));
		ha.add(Device.ROTATION_AXIS, 		new CBAxis(0,0));
		ha.add(Device.FORWARD2_AXIS, 		new CBAxis(1,1));
		ha.add(Device.SHOOT_BUTTON,			new CBButton(1, 1));
		ha.add(Device.ARMDOWN_BUTTON,		new CBButton(1, 3));
		ha.add(Device.ARMUP_BUTTON,			new CBButton(1, 5));
		ha.add(Device.HALFDOWN_BUTTON,		new CBButton(1, 4));
		ha.add(Device.HALFUP_BUTTON,		new CBButton(1, 6));
		ha.add(Device.SPIN_POV, 			new CBPov(1,0));
		
		//
		// Input Mapper Initialization
		//
		
		// Tank Drive...
		//this.driveRequestMappers.add(
		//		new CBTankDriveRequestMapper(this, Device.FORWARD_AXIS, Device.FORWARD2_AXIS)
		//		.setDeadZone(0.1)
		//		.setGyroLockButton(Device.GYROLOCK_BUTTON)
		//		);
		
		// Arcade Drive...
		this.driveRequestMappers.add(
				new CBArcadeDriveRequestMapper(
						this, 
						Device.FORWARD_AXIS,
						null,
						Device.ROTATION_AXIS
						) 
				.setDeadZone(0.2)			
				.setGyroLockButton(Device.GYROLOCK_BUTTON)	
				);

		this.manipRequestMappers.add(new SHManipRequestMapper(this));
		//this.robotSensorMappers.add(new RobotSensorMapper(this));
	
		
		
		//
		// Status Initialization
		//
		driveRequestStatus    = new CBGeneralDriveRequestStatus();
		driveControlStatus    = new CBGeneralDriveControlStatus();

		manipRequestStatus    = new SHManipRequestStatus();
		manipControlStatus    = new SHManipControlStatus();	
		
		robotSensorStatus     = new CBRobotSensorStatus();	
		//this.feedbackControlStatus = new CBFeedbackControlStatus();
		processorStatus       = new CBProcessorStatus();
	
		
		
		//
		// Processors
		//
		
		//this.ruleProcessors.add(new RuleProcessor(this));
		this.behaviorProcessors.add(
				new CBGeneralDriveBehaviorProcessor(this)
				.setGyroLockTracker(
						new CBNavXYawSource(Device.NAVX), 
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
				.addLeftMotorController (Device.DRIVE_MOTOR_LEFT_1)
				.addLeftMotorController (Device.DRIVE_MOTOR_LEFT_2)
				.addRightMotorController(Device.DRIVE_MOTOR_RIGHT_1)
				.addRightMotorController(Device.DRIVE_MOTOR_RIGHT_1)
				.setRightDirection(-1)
				);
		this.manipControllers.add(new SHManipController(this));
			
	}
	
}
