package org.usfirst.frc.team555.robot;

import org.montclairrobotics.cyborg.*;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBMotorController;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBNavXYawSource;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.plugins.*;
import org.montclairrobotics.cyborg.plugins.CBDifferentialDriveController.DriveMode;
import org.montclairrobotics.cyborg.utils.*;
import org.usfirst.frc.team555.robot.plugins.*;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SPI;

/**
 * 
 * This is the main robot definition class.
 * 
 */
public class Robot extends Cyborg {

	//
	// List Custom Hardware Devices...
	// This should include all of the active devices
	//
	public class SHDevices {
		public CBDeviceID navx, 
			armValve, halfValve,
			shootValve, spinLeft, spinRight,
			driveMotorLeft1, driveMotorLeft2,
			driveMotorRight1, driveMotorRight2,
			driveEncoderLeft, driveEncoderRight,
			gyroLockButton, forwardAxis, rotationAxis,
			forward2Axis, shootButton, armDownButton,
			armUpButton, halfDownButton, halfUpButton,
			autoSteerButton,
			spinPov,
			autoSelect,
			xTarget
			;
	}

	public SHDevices devices = new SHDevices();

	@Override
	public void cyborgInit() {

		
		// Configure Hardware Adapter
		
		CBHardwareAdapter ha = new CBHardwareAdapter(this);
		Cyborg.hardwareAdapter = ha;
		ha.setJoystickCount(2);
		
		
		// Robot Hardware 
		
		devices.navx 			= ha.add(new CBNavX(SPI.Port.kMXP));
		
		devices.armValve 		= ha.add(new CBSolenoid(0));
		devices.shootValve 		= ha.add(new CBSolenoid(1));
		devices.halfValve 		= ha.add(new CBSolenoid(2));

		devices.spinLeft 		= ha.add(new CBMotorController(new Talon(5)));
		devices.spinRight		= ha.add(new CBMotorController(new Talon(0)));

		devices.driveMotorLeft1 = ha.add(new CBMotorController(new Talon(1)));
		devices.driveMotorLeft2 = ha.add(new CBMotorController(new Talon(3)));
		devices.driveMotorRight1 = ha.add(new CBMotorController(new Talon(2)));
		devices.driveMotorRight2 = ha.add(new CBMotorController(new Talon(4)));

		devices.driveEncoderLeft = ha.add(
				new CBEncoder(1,2,EncodingType.k4X)
				.setDistancePerPulse(10.0/1600) // distance/encoder ticks
				.setReverseDirection(true)
				);
		devices.driveEncoderRight = ha.add(
				new CBEncoder(3,4,EncodingType.k4X)
				.setDistancePerPulse(10.0/1600) // distance/encoder ticks
				.setReverseDirection(false)
				);

		
		// Driver's Station Controls
		
		devices.forwardAxis 	= ha.add(new CBAxis(0, 1));
		devices.rotationAxis 	= ha.add(new CBAxis(0, 0));
		// devices.forward2Axis = ha.add(new CBAxis(1,1)) // for Tank drive

		devices.gyroLockButton 	= ha.add(new CBButton(0, 1));
		devices.autoSteerButton	= ha.add(new CBButton(0, 3));

		devices.shootButton 	= ha.add(new CBButton(1, 1));
		devices.armDownButton 	= ha.add(new CBButton(1, 3));
		devices.armUpButton 	= ha.add(new CBButton(1, 5));
		devices.halfDownButton 	= ha.add(new CBButton(1, 4));
		devices.halfUpButton 	= ha.add(new CBButton(1, 6));

		devices.spinPov 		= ha.add(new CBPov(1, 0));

		devices.autoSelect		= ha.add(
				new CBDashboardChooser<Integer>("Auto:")
				.setTiming(CBGameMode.preGame, 50)
				.addDefault("zero",new Integer(0))
				.addChoice("one", new Integer(1))
				);

		devices.xTarget			 = ha.add(
				new CBContourReport("GRIP/mynewreport")
				.setTiming(CBGameMode.anyPeriodic, 5)
				);

		//
		// Data Initialization
		//
		driveRequestData 	= new CBGeneralDriveRequestData();
		driveControlData	= new CBGeneralDriveControlData();

		generalRequestData	= new SHGeneralRequestData();
		generalControlData	= new SHGeneralControlData();

		processorData 		= new CBProcessorData();

		
		//
		// Input Mapper Initialization
		//
		
		// Tank Drive Stick Input Example...
		// this.driveRequestMappers.add(
		// new CBTankDriveRequestMapper(this, Device.FORWARD_AXIS,
		// Device.FORWARD2_AXIS)
		// .setDeadZone(0.1)
		// .setGyroLockButton(Device.GYROLOCK_BUTTON)
		// );

		// Arcade Drive...
		this.teleOpMappers.add(
				new CBArcadeDriveMapper(this, devices.forwardAxis, null, devices.rotationAxis)
				.setDeadZone(0.2)
				.setGyroLockButton(devices.gyroLockButton)
				);
		this.teleOpMappers.add(new SHOperatorMapper(this));
		this.generalMappers.add(new SHSensorMapper(this));

		
		
		//
		// Output Controller Initialization
		//
		
		this.robotControllers.add(
				new CBDifferentialDriveController(this)
				.addLeftMotorController(devices.driveMotorLeft1).addLeftMotorController(devices.driveMotorLeft2)
				.addRightMotorController(devices.driveMotorRight1).addRightMotorController(devices.driveMotorRight1)
				.setLeftDirection(-1)
				// Enable this code to switch to Speed Control Mode.
				//.setEncoders(devices.driveEncoderLeft, devices.driveEncoderRight)
				//.setPIDControllers(
				//		new CBPIDController(.9,0,9), 
				//		new CBPIDController(.9,0,9)
				//		)
				//.setDriveMode(DriveMode.Speed)
				);
		this.robotControllers.add(new SHGeneralController(this));

		
		//
		// Behavior Processors
		//

		this.behaviors.add(
				new CBGeneralDriveBehavior(this)
				.setGyroLockTracker(
						new CBNavXYawSource(devices.navx), 
						new CBPIDController(0.2, 0.0, 2.0)
						.setInputLimits(-180, 180) // assumes navx source in degrees
						)
				);
		// this.behaviorProcessors.add(
		//		new CBTankDriveBehaviorProcessor(this)
		//		);
		this.behaviors.add(new SHGeneralBehavior(this));
		this.autonomous = new SHAutonomous(this);

	}

	@Override
	public void cyborgTeleopInit() {

	}

	@Override
	public void cyborgTestInit() {
		
	}

	@Override
	public void cyborgTestPeriodic() {
		
	}
}
