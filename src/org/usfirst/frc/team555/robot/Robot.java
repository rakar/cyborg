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
	// This should include all of the active devices 
	//
	public class SHDevices {
		public CBDeviceID 
		navx, 
		armValve, halfValve, shootValve,
		spinLeft, spinRight,
		driveMotorLeft1, driveMotorLeft2,
		driveMotorRight1,driveMotorRight2,
		gyroLockButton,
		forwardAxis, rotationAxis, forward2Axis,
		shootButton, armDownButton, armUpButton, halfDownButton, halfUpButton,
		spinPov
		;
	}
	
	public SHDevices devices = new SHDevices();

	@Override
	public void cyborgInit() {
		
		// Configure Custom Hardware
		Cyborg.hardwareAdapter = new CBHardwareAdapter(this)
				.setJoystickCount(2)		
				.add(devices.navx,              new CBNavX(SPI.Port.kMXP))	
				.add(devices.armValve,     		new CBSolenoid(0))
				.add(devices.shootValve,   		new CBSolenoid(1))
				.add(devices.halfValve,    		new CBSolenoid(2))
				
				.add(devices.spinLeft,     		new CBMotorController(new Talon(5)))
				.add(devices.spinRight,			new CBMotorController(new Talon(0)))
				
				.add(devices.driveMotorLeft1,	new CBMotorController(new Talon(1)))
				.add(devices.driveMotorLeft2,	new CBMotorController(new Talon(3)))
				.add(devices.driveMotorRight1,	new CBMotorController(new Talon(2)))
				.add(devices.driveMotorRight2,	new CBMotorController(new Talon(4)))
				
				.add(devices.forwardAxis, 		new CBAxis(0,1))
				.add(devices.rotationAxis, 		new CBAxis(0,0))
				//.add(devices.forward2Axis, 		new CBAxis(1,1)) // for Tank drive 
		
				.add(devices.gyroLockButton,	new CBButton(0, 1))
				.add(devices.shootButton,		new CBButton(1, 1))
				.add(devices.armDownButton,		new CBButton(1, 3))
				.add(devices.armUpButton,		new CBButton(1, 5))
				.add(devices.halfDownButton,	new CBButton(1, 4))
				.add(devices.halfUpButton,		new CBButton(1, 6))
				
				.add(devices.spinPov, 			new CBPov(1,0))
				;
		
	

		//
		// Status Initialization
		//
		driveRequestData       = new CBGeneralDriveRequestData();
		driveControlData       = new CBGeneralDriveControlData();

		manipulatorRequestData = new SHManipulatorRequestData();
		manipulatorControlData = new SHManipulatorControlData();	
		
		//robotSensorData        = new CBRobotSensorData();	
		//this.feedbackControlStatus = new CBFeedbackControlStatus();
		
		processorData          = new CBProcessorData();
	
		
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
						devices.forwardAxis,
						null,
						devices.rotationAxis
						) 
				.setDeadZone(0.2)			
				.setGyroLockButton(devices.gyroLockButton)	
				);
		this.manipulatorRequestMappers.add(new SHManipulatorRequestMapper(this));
		//this.robotSensorMappers.add(new RobotSensorMapper(this));
		
		
		//
		// Output Controller Initialization
		//		
		//this.feedbackControllers.add(new FeedbackController(this));		
		this.driveControllers.add(
				new CBDifferentialDriveController(this)
				.addLeftMotorController (devices.driveMotorLeft1)
				.addLeftMotorController (devices.driveMotorLeft2)
				.addRightMotorController(devices.driveMotorRight1)
				.addRightMotorController(devices.driveMotorRight1)
				.setRightDirection(-1)
				);
		this.manipulatorControllers.add(
				new SHManipulatorController(this)
				);
			
		//
		// Processors
		//		
		//this.ruleProcessors.add(new RuleProcessor(this));
		this.behaviorProcessors.add(
				new CBGeneralDriveBehaviorProcessor(this)
				.setGyroLockTracker(
						new CBNavXYawSource(devices.navx), 
						new CBPID(0.2, 0.0, 2.0)
						.setInputLimits(-180, 180) // assumes navx source in degrees
					)
				);
		//this.behaviorProcessors.add(
		//		new TankDriveBehaviorProcessor(this)
		//		);
		this.behaviorProcessors.add(
				new SHManipulatorBehaviorProcessor(this)
				); 
		this.autonomousAI = new SHAutonomousAI(this);

	}
	
}
