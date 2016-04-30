/**
 * 
 */
package org.montclairrobotics.cyborg;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * @author rich
 *
 */
public abstract class Cyborg extends IterativeRobot {

	public static CBHardwareAdapter hardwareAdapter;
	
	// Status Classes
	// Statuses represent high-level meaningful messages
	public static CBDriveRequestData driveRequestData;
	public static CBManipulatorRequestData manipulatorRequestData;
	public static CBRobotSensorData robotSensorData;
	//public CBFeedbackControlStatus feedbackControlStatus;
	public static CBDriveControlData driveControlData;
	public static CBManipulatorControlData manipulatorControlData;
	public static CBProcessorData processorData;

	
	// Mapper/Controller Queues
	// Mapper Queues hold lists of mappers that convert raw input state information into meaningful status info
	public ArrayList<CBDriveRequestMapper> driveRequestMappers = new ArrayList<CBDriveRequestMapper>();
	public ArrayList<CBManipulatorRequestMapper> manipulatorRequestMappers = new ArrayList<CBManipulatorRequestMapper>();
	public ArrayList<CBRobotSensorMapper> robotSensorMappers = new ArrayList<CBRobotSensorMapper>();
	// Controller Queues hold lists of controllers that convert high-level requests into low-level raw control output data
	//private ArrayList<CBFeedbackController> feedbackControllers = new ArrayList<CBFeedbackController>();
	public ArrayList<CBDriveController> driveControllers = new ArrayList<CBDriveController>();
	public ArrayList<CBManipulatorController> manipulatorControllers = new ArrayList<CBManipulatorController>();
	
	// Logic Layer
	public ArrayList<CBRuleProcessor> ruleProcessors = new ArrayList<>();
	public ArrayList<CBBehaviorProcessor> behaviorProcessors = new ArrayList<>();
	
	public CBAutonomousAI autonomousAI;
	
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	@Override
    public final void robotInit() {        	
		//feedbackControlInterface = new CBFeedbackControlInterface(this);

		cyborgInit();
		
		// Set default StateObjects
		//if(driverFeedbackState == null) driverFeedbackState  = new CBFeedbackControlState();
		
	}
    
    public abstract void cyborgInit();
    public abstract void cyborgAutonomousInit();
    public abstract void cyborgTeleopInit();
    
	/**
	 */
	@Override
    public final void autonomousInit() {
		cyborgAutonomousInit();
    }

    /**
     * This function is called periodically during autonomous
     */
	@Override
    public final void autonomousPeriodic() {
		// Update input interfaces
		// driverStationInterface.Update();
		// robotSensorInterface.update();
		hardwareAdapter.senseUpdate();
		
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
		for(CBManipulatorRequestMapper m:this.manipulatorRequestMappers) m.update(); 
		for(CBRobotSensorMapper  m:this.robotSensorMappers)  m.update(); 

		
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
		for(CBDriveController m:this.driveControllers) m.update(); 
		for(CBManipulatorController m:this.manipulatorControllers) m.update(); 
		
		
		// Update output interfaces
		//this.feedbackControlInterface.update();
		//this.hardwareControlInterface.update();
		hardwareAdapter.controlUpdate();

	}
	
}
