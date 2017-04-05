package org.usfirst.frc.team555.steamworks;

import org.montclairrobotics.cyborg.*;
import org.montclairrobotics.cyborg.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.assemblies.CBSrxArrayController;
import org.montclairrobotics.cyborg.assemblies.CBVictorArrayController;
import org.montclairrobotics.cyborg.behaviors.*;
import org.montclairrobotics.cyborg.controllers.CBDifferentialDriveController;
import org.montclairrobotics.cyborg.data.CBLogicData;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBCANTalon;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBTalon;
import org.montclairrobotics.cyborg.mappers.CBArcadeDriveMapper;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.utils.*;
import org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;

/**
 * 
 * This is the main robot definition class.
 * 
 */
public class SWRobot extends Cyborg {
	
	final static int driveStickId = 1;
	final static int operStickId = 0;
	
	//
	// List Custom Hardware Devices...
	// This should include all of the active devices
	//
	private class Devices {
		private CBDeviceId
			// input
			autoSelect,
			navx,
			gearAutoOpenButton,
			gearAutoCloseButton,
			gearManualLeftOpenButton,
			gearManualLeftCloseButton,
			gearManualRightOpenButton,
			gearManualRightCloseButton,
			climbButton,
			forwardAxis, rotationAxis,
			gyroLockButton, spinPov,
			// output
			driveMotorLeft1, driveMotorLeft2,
			driveMotorRight1, driveMotorRight2,		
			driveEncoderLeft, driveEncoderRight,
			climbMotorLeft,climbMotorRight,
			gearMotorLeft, gearMotorRight
			;
	}
	private Devices devices = new Devices();

	@Override
	public void cyborgInit() {

		//
		// Data Initialization
		//
		// Initialize data stores
		// The drive and general data stores are separated 
		// because the drive stores will most likely be 
		// pre-built and the custom ones will handle 
		// robot specific data requirements. 
		// 
		driveRequestData 	= new CBStdDriveRequestData();
		driveControlData	= new CBStdDriveControlData();
		customRequestData	= new SWCustomRequestData();
		customControlData	= new SWCustomControlData();
		logicData 			= new CBLogicData();

		
		// Configure Hardware Adapter
		Cyborg.hardwareAdapter = 
				new CBHardwareAdapter(this)
				.setJoystickCount(2);
		CBHardwareAdapter ha = Cyborg.hardwareAdapter;		
		
		// Robot Hardware 
		// Input devices
		devices.navx 				= ha.add(new CBNavX(SPI.Port.kMXP));

		devices.driveEncoderLeft 	= ha.add(
				new CBEncoder(2, 3, EncodingType.k4X, true, 4*76.25/5865)
				);
		devices.driveEncoderRight 	= ha.add(
				new CBEncoder(4, 5, EncodingType.k4X, true, 4*76.25/5865)
				);

		
		// Driver's Station Controls	
		devices.forwardAxis 	= ha.add(
				new CBAxis(driveStickId, 1)
				.setDeadzone(0.1)
				.setScale(-1.0) // stick forward => robot forward
				);
		devices.rotationAxis 	= ha.add(
				new CBAxis(driveStickId, 0)
				.setDeadzone(0.1)
				.setScale(-1.0) // stick left => robot left/counterClockwise
				);
		//devices.forward2Axis 	= ha.add(
		//		new CBAxis(operStickId, 1)
		//		.setDeadzone(0.1)
		//		.setScale(-1.0) // stick forward => robot forward
		//		); // for Tank drive
		
		devices.gearAutoOpenButton			= ha.add(new CBButton(operStickId, 1));
		devices.gearAutoCloseButton			= ha.add(new CBButton(operStickId, 2));
		devices.gearManualLeftOpenButton	= ha.add(new CBButton(operStickId, 3));
		devices.gearManualLeftCloseButton	= ha.add(new CBButton(operStickId, 4));
		devices.gearManualRightOpenButton	= ha.add(new CBButton(operStickId, 5));
		devices.gearManualRightCloseButton	= ha.add(new CBButton(operStickId, 6));
		devices.climbButton					= ha.add(new CBButton(operStickId, 7));


		//devices.spinPov 		= ha.add(new CBPov(operStickId, 0));

		devices.autoSelect		= ha.add(
				new CBDashboardChooser<Integer>("Auto:")
				.setTiming(CBGameMode.preGame, 0)
				.addDefault("Center", 1)
				.addChoice("Left", 2)
				.addChoice("Right", 3)
				);

		// Output devices
		devices.driveMotorLeft1		= ha.add(new CBCANTalon(3));
		devices.driveMotorLeft2		= ha.add(new CBCANTalon(4));
		devices.driveMotorRight1	= ha.add(new CBCANTalon(1));
		devices.driveMotorRight2	= ha.add(new CBCANTalon(2));
		devices.gearMotorLeft		= ha.add(new CBCANTalon(5));
		devices.gearMotorRight		= ha.add(new CBCANTalon(6));
		devices.climbMotorLeft		= ha.add(new CBCANTalon(7));
		devices.climbMotorRight		= ha.add(new CBCANTalon(8));

		
		// Co-processor Vision System
		//devices.visionPipeline	 = ha.add(
		//		new CBContourReport("GRIP/mynewreport")
		//		.setTiming(CBGameMode.anyPeriodic, 0)
		//		);


		//
		// Input Mapper Initialization
		//
		
		// Tank Drive Stick Input Example...
		//this.addTeleOpMapper(
		//		new CBTankDriveMapper(this, devices.forwardAxis,
		//				devices.forward2Axis)
		//		.setDeadZone(0.1)
		//		.setGyroLockButton(devices.gyroLockButton)
		//		);

		// Arcade Drive...
		// Use pre-built Cyborg plug-in to map arcade drive control  
		this.addTeleOpMapper(
				new CBArcadeDriveMapper(this)
				.setAxes(devices.forwardAxis, null, devices.rotationAxis) // No strafe axis
				.setGyroLockButton(devices.gyroLockButton)
				.setAxisScales(0, 40, 90) // no strafe, 40 inches/second, 90 degrees/second
				);

		// Use teleOp mappers for operator mapping
		this.addTeleOpMapper(
				new SWOperatorMapper(this)
				.setClimbButton(devices.climbButton)
				.setGearAutoCloseButton(devices.gearAutoCloseButton)
				.setGearAutoOpenButton(devices.gearAutoOpenButton)
				.setGearManualLeftCloseButton(devices.gearManualLeftCloseButton)
				.setGearManualLeftOpenButton(devices.gearManualLeftOpenButton)
				.setGearManualRightCloseButton(devices.gearManualRightCloseButton)
				.setGearManualRightOpenButton(devices.gearManualRightOpenButton)
				);
		
		// Use custom mappers for sensor/full-time mapping
		this.addCustomMapper(
				new SWSensorMapper(this)
				.setAutoChooser(devices.autoSelect)
				.setGyroLockSource(devices.navx)
				.setDriveEncoders(devices.driveEncoderLeft, devices.driveEncoderRight)
				);

		
		//
		// Output Controller Initialization
		//
		this.addRobotController(
				new CBDifferentialDriveController(this)
				.addDriveModule(
						new CBDriveModule(new CB2DVector(-13.75,0), 0)
						.addSpeedControllerArray(
								new CBSrxArrayController()
								.setDriveMode(CBDriveMode.Speed)
								.addSpeedController(devices.driveMotorLeft1)
								.addSpeedController(devices.driveMotorLeft2)
								.setEncoder(devices.driveEncoderLeft)
								.setErrorCorrection(
										new CBPIDErrorCorrection()
										.setConstants(new double[]{0.08,0,0})
										)
								)
						)
				.addDriveModule(
						new CBDriveModule(new CB2DVector( 13.75,0), 180)
						.addSpeedControllerArray(
								new CBSrxArrayController()
								.setDriveMode(CBDriveMode.Speed)
								.addSpeedController(devices.driveMotorRight1)
								.addSpeedController(devices.driveMotorRight2)
								.setEncoder(devices.driveEncoderRight)
								.setErrorCorrection(
										new CBPIDErrorCorrection()
										.setConstants(new double[]{0.08,0,0})
										)
								)
						)
				);
				
		//this.addRobotController(
		//		new SHCustomController(this)
		//		.setSpinArray(
		//				new CBVictorArrayController()
		//				.addSpeedController(devices.shooterLeftMotor)
		//				.addSpeedController(devices.shooterRightMotor)
		//				.setDriveMode(CBDriveMode.Power)
		//				)
		//		.setArmValve(devices.armMainValve)
		//		.setHalfValve(devices.armHalfValve)
		//		.setShootValve(devices.shooterValve)
		//		);

		
		//
		// Behavior Processors
		//
		this.addBehavior(
				new CBStdDriveBehavior(this)
				.setGyroLockTracker(
						new CBPIDErrorCorrection()
						.setConstants(new double[]{0.2, 0.0, 2.0})
						.setInputLimits(-180, 180) // assumes navx source in degrees
						)
				);
		// this.addBehavior(
		//		new CBTankDriveBehaviorProcessor(this)
		//		);
		//this.addBehavior(
		//		new SHCustomBehavior(this)
		//		.setXTracker(
		//				new CBPIDErrorCorrection()
		//				.setConstants(new double[]{5.0,0.0,0.0})
		//				.setOutputLimits(-45, 45)
		//				// Targets shouldn't be set here unless
		//				// they are inherently fixed.
		//				// Instead these were moved to CustomRequestData.
		//				//.setTarget(160.0)   
		//				)
		//		.setYTracker(
		//				new CBPIDErrorCorrection()
		//				.setConstants(new double[]{0.0,0.0,0.0})
		//				.setOutputLimits(-20, 20)
		//				// Targets shouldn't be set here unless
		//				// they are inherently fixed.
		//				// Instead these were moved to CustomRequestData.
		//				//.setTarget(200.0)
		//				)
		//		);
		//this.addBehavior(
		//		new SHPIDTuner(this)
		//		);
		

		//this.addAutonomous(
		//		new SHAutonomous(this)
		//		.setFireTarget(160, 200, 10, 20)
		//		);

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
