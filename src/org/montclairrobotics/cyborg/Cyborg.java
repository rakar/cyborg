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
	// Hardware Interface Classes
	public DriverStationInterface driverStationInterface;
	public RobotSensorInterface robotSensorInterface;
	public FeedbackControlInterface feedbackControlInterface;
	public HardwareControlInterface hardwareControlInterface;
	
	// State Classes
	// States represent low-level "raw" messages
	public DriverStationState driverStationState;
	public RobotSensorState robotSensorState;
	public FeedbackControlState driverFeedbackState;
	public HardwareControlState hardwareControlState;

	// Mapper/Controller Queues
	// Mapper Queues hold lists of mappers that convert raw input state information into meaningful status info
	public ArrayList<DriveRequestMapper> driveRequestMappers = new ArrayList<DriveRequestMapper>();
	public ArrayList<ManipRequestMapper> manipRequestMappers = new ArrayList<ManipRequestMapper>();
	public ArrayList<RobotSensorMapper> robotSensorMappers = new ArrayList<RobotSensorMapper>();
	// Controller Queues hold lists of controllers that convert high-level reqeusts into low-level raw control output data
	public ArrayList<FeedbackController> feedbackControllers = new ArrayList<FeedbackController>();
	public ArrayList<DriveController> driveControllers = new ArrayList<DriveController>();
	public ArrayList<ManipController> manipControllers = new ArrayList<ManipController>();
	
	
	
	// Status Classes
	// Statuses represent high-level meaningful messages
	public DriveRequestStatus driveRequestStatus;
	public ManipRequestStatus manipRequestStatus;
	public RobotSensorStatus robotSensorStatus;
	public FeedbackControlStatus feedbackControlStatus;
	public DriveControlStatus driveControlStatus;
	public ManipControlStatus manipControlStatus;
	
	// Logic Layer
	public ArrayList<RuleProcessor> ruleProcessors = new ArrayList<>();
	public ArrayList<BehaviorProcessor> behaviorProcessors = new ArrayList<>();
	
	public AutonomousAI autonomousAI;
	
	
	 /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	@Override
    public final void robotInit() {        	
		driverStationInterface = new DriverStationInterface(this);
		robotSensorInterface = new RobotSensorInterface(this);
		feedbackControlInterface = new FeedbackControlInterface(this);
		hardwareControlInterface = new HardwareControlInterface(this);

		cyborgInit();
		
		if(driverStationState==null) driverStationState=new DriverStationState();
		if(driverFeedbackState==null) driverFeedbackState=new FeedbackControlState();
		if(robotSensorState==null) robotSensorState = new RobotSensorState();
		if(hardwareControlState==null) hardwareControlState = new HardwareControlState();
		
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
		robotSensorInterface.Update();
		
		// Update Input Mappers
		for(RobotSensorMapper m:this.robotSensorMappers) m.Update(); 

		// Autonomous Control
		autonomousAI.Update();

		// Let the robot do it's thing...
		robotControl();
    }

    /**
     * This function is called periodically during operator control
     */
	@Override
    public final void teleopPeriodic() {
		
		// Update input interfaces
		driverStationInterface.Update();
		robotSensorInterface.Update();
		
		// Update Input Mappers
		for(DriveRequestMapper m:this.driveRequestMappers) m.Update(); 
		for(ManipRequestMapper m:this.manipRequestMappers) m.Update(); 
		for(RobotSensorMapper m:this.robotSensorMappers) m.Update(); 
		
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
		for(RuleProcessor m:this.ruleProcessors) m.Update(); 
		for(BehaviorProcessor m:this.behaviorProcessors) m.Update(); 
		
		
		// Update Output Controllers
		for(FeedbackController m:this.feedbackControllers) m.Update(); 
		for(DriveController m:this.driveControllers) m.Update(); 
		for(ManipController m:this.manipControllers) m.Update(); 
		
		
		// Update output interfaces
		//this.feedbackControlInterface.Update();
		//this.hardwareControlInterface.Update();

	}
	
	
	/*
	 * Setters/Getters 
	 */
	public void setJoystickCount(int count) {
		driverStationInterface.setJoystickCount(count);
	}
	
	//public void addHumanInterfaceMapper(DriveRequestMapper mapper) {
	//	DrivetrainControlMappers.add(mapper);
	//}
	
}
