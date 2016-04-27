/**
 * 
 */
package org.montclairrobotics.cyborg;

import java.util.ArrayList;

import org.usfirst.frc.team555.robot.Robot.Device;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * @author rich
 *
 */
public abstract class Cyborg extends IterativeRobot {
	// Hardware Interface Classes
	//public DriverStationAdapter driverStationInterface;
	//public RobotSensorInterface robotSensorInterface;
	public CBFeedbackControlInterface feedbackControlInterface;
	//public HardwareControlInterface hardwareControlInterface;
	//@SuppressWarnings("rawtypes")
	public CBHardwareAdapter hardwareAdapter;
	
	// State Classes
	// States represent low-level "raw" messages
	//public DriverStationState driverStationState;
	//public RobotSensorState robotSensorState;
	public CBFeedbackControlState driverFeedbackState;
	//public HardwareControlState hardwareControlState;

	// Mapper/Controller Queues
	// Mapper Queues hold lists of mappers that convert raw input state information into meaningful status info
	public ArrayList<CBDriveRequestMapper> driveRequestMappers = new ArrayList<CBDriveRequestMapper>();
	public ArrayList<CBManipRequestMapper> manipRequestMappers = new ArrayList<CBManipRequestMapper>();
	public ArrayList<CBRobotSensorMapper> robotSensorMappers = new ArrayList<CBRobotSensorMapper>();
	// Controller Queues hold lists of controllers that convert high-level reqeusts into low-level raw control output data
	public ArrayList<CBFeedbackController> feedbackControllers = new ArrayList<CBFeedbackController>();
	public ArrayList<CBDriveController> driveControllers = new ArrayList<CBDriveController>();
	public ArrayList<CBManipController> manipControllers = new ArrayList<CBManipController>();
	
	
	
	// Status Classes
	// Statuses represent high-level meaningful messages
	public CBDriveRequestStatus driveRequestStatus;
	public CBManipRequestStatus manipRequestStatus;
	public CBRobotSensorStatus robotSensorStatus;
	public CBFeedbackControlStatus feedbackControlStatus;
	public CBDriveControlStatus driveControlStatus;
	public CBManipControlStatus manipControlStatus;
	public CBProcessorStatus processorStatus;
	
	// Logic Layer
	public ArrayList<CBRuleProcessor> ruleProcessors = new ArrayList<>();
	public ArrayList<CBBehaviorProcessor> behaviorProcessors = new ArrayList<>();
	
	public CBAutonomousAI autonomousAI;
	
	// shortcut to customHardwareInterface
	@SuppressWarnings("unchecked")
	public CBHardwareAdapter<Device> getHA() {
		return (CBHardwareAdapter<Device>) this.hardwareAdapter;
	}
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	@Override
    public final void robotInit() {        	
		feedbackControlInterface = new CBFeedbackControlInterface(this);

		cyborgInit();
		
		// Set default StateObjects
		if(driverFeedbackState == null) driverFeedbackState  = new CBFeedbackControlState();
		
	}
    
    public abstract void cyborgInit();
    
	/**
	 */
	@Override
    public final void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
	@Override
    public final void autonomousPeriodic() {
		// Update input interfaces
		//driverStationInterface.Update();
		//robotSensorInterface.update();
		
		// Update Input Mappers
		for(CBRobotSensorMapper m:this.robotSensorMappers) m.update(); 

		// Autonomous Control
		autonomousAI.update();

		// Let the robot do it's thing...
		robotControl();
    }

    /**
     * This function is called periodically during operator control
     */
	@Override
    public final void teleopPeriodic() {
		
		// Update input interfaces
		//driverStationInterface.update();
		//robotSensorInterface.update();
		hardwareAdapter.senseUpdate();
		
		// Update Input Mappers
		for(CBDriveRequestMapper m:this.driveRequestMappers) m.update(); 
		for(CBManipRequestMapper m:this.manipRequestMappers) m.update(); 
		for(CBRobotSensorMapper m:this.robotSensorMappers) m.update(); 
		
		// Let the robot do it's thing...
		robotControl();
    }
    
    /**
     * This function is called periodically during test mode
     */
	@Override
    public final void testPeriodic() {
    
    }


	private void robotControl() {
		// Update Rule and Behavior Processors 
		for(CBRuleProcessor m:this.ruleProcessors) m.update(); 
		for(CBBehaviorProcessor m:this.behaviorProcessors) m.update(); 
		
		
		// Update Output Controllers
		for(CBFeedbackController m:this.feedbackControllers) m.update(); 
		for(CBDriveController m:this.driveControllers) m.update(); 
		for(CBManipController m:this.manipControllers) m.update(); 
		
		
		// Update output interfaces
		//this.feedbackControlInterface.update();
		//this.hardwareControlInterface.update();
		hardwareAdapter.controlUpdate();

	}
	
	
	/*
	 * Setters/Getters 
	 */
	//public void setJoystickCount(int count) {
	//	driverStationInterface.setJoystickCount(count);
	//}
	
	//public void addHumanInterfaceMapper(DriveRequestMapper mapper) {
	//	DrivetrainControlMappers.add(mapper);
	//}
	
}
