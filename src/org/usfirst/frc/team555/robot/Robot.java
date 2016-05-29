package org.usfirst.frc.team555.robot;

import org.montclairrobotics.cyborg.*;
import org.montclairrobotics.cyborg.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.assemblies.CBVictorArrayController;
import org.montclairrobotics.cyborg.behaviors.*;
import org.montclairrobotics.cyborg.controllers.CBDifferentialDriveController;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.data.CBStdDriveRequestData;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDashboardChooser;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.mappers.CBArcadeDriveMapper;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.utils.*;
import org.montclairrobotics.cyborg.utils.CBEnums.CBDriveMode;
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
	
	public SHCustomRequestData crd = (SHCustomRequestData)Cyborg.customRequestData;
	public SHCustomControlData ccd = (SHCustomControlData)Cyborg.customControlData;
	public CBStdDriveControlData dcd = (CBStdDriveControlData)Cyborg.driveControlData;
	public CBStdDriveRequestData drd = (CBStdDriveRequestData) Cyborg.driveRequestData;
	public CBLogicData ld = (CBLogicData)Cyborg.logicData;

	//
	// List Custom Hardware Devices...
	// This should include all of the active devices
	//
	private class SHDevices {
		private CBDeviceID 
			navx, 
			armMainValve, armHalfValve, shooterValve, 
			shooterLeftMotor, shooterRightMotor,
			driveMotorLeft1, driveMotorLeft2,
			driveMotorRight1, driveMotorRight2,		
			driveEncoderLeft, driveEncoderRight,
			forwardAxis, rotationAxis,
			//forward2Axis, 
			shootButton, armUpButton, armMidButton, armDownButton,
			gyroLockButton, autoSteerButton,
			spinPov,
			autoSelect,
			visionPipeline
			;
	}

	private SHDevices devices = new SHDevices();

	@Override
	public void cyborgInit() {

		
		// Configure Hardware Adapter
		
		Cyborg.hardwareAdapter = 
				new CBHardwareAdapter(this)
				.setJoystickCount(2);
		
		CBHardwareAdapter ha = Cyborg.hardwareAdapter;
		
		
		// Robot Hardware 
		
		devices.navx 				= ha.add(new CBNavX(SPI.Port.kMXP));
		
		devices.armMainValve 		= ha.add(new CBSolenoid(0));
		devices.shooterValve 		= ha.add(new CBSolenoid(1));
		devices.armHalfValve 		= ha.add(new CBSolenoid(2));

		devices.shooterLeftMotor	= ha.add(new CBSpeedController(new Talon(5)).setInverted(true));
		devices.shooterRightMotor	= ha.add(new CBSpeedController(new Talon(0)));

		devices.driveMotorLeft1		= ha.add(new CBSpeedController(new Talon(2)));
		devices.driveMotorLeft2		= ha.add(new CBSpeedController(new Talon(4)));
		devices.driveMotorRight1	= ha.add(new CBSpeedController(new Talon(1)));
		devices.driveMotorRight2	= ha.add(new CBSpeedController(new Talon(3)));

		devices.driveEncoderLeft 	= ha.add(new CBEncoder(1,2,EncodingType.k4X,10.0/1600));
		devices.driveEncoderRight 	= ha.add(new CBEncoder(3,4,EncodingType.k4X,10.0/1600));

		
		// Driver's Station Controls	
		devices.forwardAxis 	= ha.add(new CBAxis(0, 1));
		devices.rotationAxis 	= ha.add(new CBAxis(0, 0));
		//devices.forward2Axis 	= ha.add(new CBAxis(1, 1)); // for Tank drive

		devices.gyroLockButton 	= ha.add(new CBButton(0, 1));
		devices.autoSteerButton	= ha.add(new CBButton(0, 3));

		devices.shootButton 	= ha.add(new CBButton(1, 1));
		devices.armUpButton 	= ha.add(new CBButton(1, 5));
		devices.armMidButton 	= ha.add(new CBButton(1, 3));
		devices.armDownButton 	= ha.add(new CBButton(1, 4));

		devices.spinPov 		= ha.add(new CBPov(1, 0));

		devices.autoSelect		= ha.add(
				new CBDashboardChooser<Integer>("Auto:")
				.setTiming(CBGameMode.preGame, 50)
				.addDefault("zero",new Integer(0))
				.addChoice("one", new Integer(1))
				);

		devices.visionPipeline	 = ha.add(
				new CBContourReport("GRIP/mynewreport")
				.setTiming(CBGameMode.anyPeriodic, 5)
				);

		//
		// Data Initialization
		//
		// Initialize data stores
		// The drive and general data stores are separated 
		// since the drive stores will most likely be 
		// pre-built and the general ones will handle 
		// custom data requirements. 
		// 
		driveRequestData 	= new CBStdDriveRequestData();
		driveControlData	= new CBStdDriveControlData();
		customRequestData	= new SHCustomRequestData();
		customControlData	= new SHCustomControlData();
		logicData 			= new CBLogicData();

		
		//
		// Input Mapper Initialization
		//
		
		// Tank Drive Stick Input Example...
		//this.teleOpMappers.add(
		//		new CBTankDriveMapper(this, devices.forwardAxis,
		//				devices.forward2Axis)
		//		.setDeadZone(0.1)
		//		.setGyroLockButton(devices.gyroLockButton)
		//		);

		// Arcade Drive...
		// Use pre-built Cyborg plug-in to map arcade drive control  
		this.teleOpMappers.add(
				new CBArcadeDriveMapper(this)
				.setAxes(devices.forwardAxis, null, devices.rotationAxis) // No strafe axis
				.setDeadZone(0.2)
				.setGyroLockButton(devices.gyroLockButton)
				);
		// Use custom mappers for operator mappings and sensor mappings
		this.teleOpMappers.add(
				new SHOperatorMapper(this)
				.setArmDownButton(devices.armDownButton)
				.setArmUpButton(devices.armUpButton)
				.setArmHalfUpButton(devices.armMidButton)
				.setAutoSteerButton(devices.autoSteerButton)
				.setShootButton(devices.shootButton)
				.setSpinPOV(devices.spinPov)
				);
		
		this.customMappers.add(
				new SHSensorMapper(this)
				.setAutoChooser(devices.autoSelect)
				.setContourRpt(devices.visionPipeline)
				.setGyroLockSource(devices.navx)
				);

		
		
		//
		// Output Controller Initialization
		//
		this.robotControllers.add(
				new CBDifferentialDriveController(this)
				.addDriveModule(
						new CBDriveModule(new CB2DVector(-15,0), 0)
						.addSpeedControllerArray(
								new CBVictorArrayController()
								.setDriveMode(CBDriveMode.Power)
								.addSpeedController(devices.driveMotorLeft1)
								.addSpeedController(devices.driveMotorLeft2)
								.setEncoder(devices.driveEncoderLeft)
								.setErrorCorrection(
										new CBPIDErrorCorrection()
										.setConstants(new double[]{.9,0,.9})
										)
								)
						)
				.addDriveModule(
						new CBDriveModule(new CB2DVector( 15,0), 180)
						.addSpeedControllerArray(
								new CBVictorArrayController()
								.setDriveMode(CBDriveMode.Power)
								.addSpeedController(devices.driveMotorRight1)
								.addSpeedController(devices.driveMotorRight2)
								.setEncoder(devices.driveEncoderRight)
								.setErrorCorrection(
										new CBPIDErrorCorrection()
										.setConstants(new double[]{.9,0,.9})
										)
								)
						)
				);
				
		this.robotControllers.add(
				new SHCustomController(this)
				.setSpinArray(
						new CBVictorArrayController()
						.addSpeedController(devices.shooterLeftMotor)
						.addSpeedController(devices.shooterRightMotor)
						.setDriveMode(CBDriveMode.Power)
						)
				.setArmValve(devices.armMainValve)
				.setHalfValve(devices.armHalfValve)
				.setShootValve(devices.shooterValve)
				);

		
		//
		// Behavior Processors
		//
		this.behaviors.add(
				new CBStdDriveBehavior(this)
				.setGyroLockTracker(
						new CBPIDErrorCorrection()
						.setConstants(new double[]{0.2, 0.0, 2.0})
						.setInputLimits(-180, 180) // assumes navx source in degrees
						)
				);
		// this.behaviors.add(
		//		new CBTankDriveBehaviorProcessor(this)
		//		);
		this.behaviors.add(
				new SHCustomBehavior(this)
				.setXTracker(
						new CBPIDErrorCorrection()
						.setConstants(new double[]{-0.025,0.0,0.0})
						.setOutputLimits(-.3, .3)
						.setTarget(120.0)
						)
				.setYTracker(
						new CBPIDErrorCorrection()
						.setConstants(new double[]{ 0.025,0.0,0.0})
						.setOutputLimits(-.3, .3)
						.setTarget(180.0)
						)
				);
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
