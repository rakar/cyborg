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
	public static CBGeneralRequestData generalRequestData;
	public static CBDriveControlData driveControlData;
	public static CBGeneralControlData generalControlData;
	public static CBProcessorData processorData;

	
	// Mapper/Controller Queues
	// Mapper Queues hold lists of mappers that convert raw input state information into meaningful status info
	public ArrayList<CBTeleOpMapper> teleOpMappers = new ArrayList<CBTeleOpMapper>();
	public ArrayList<CBGeneralMapper> generalMappers = new ArrayList<CBGeneralMapper>();
	// Controller Queues hold lists of controllers that convert high-level requests into low-level raw control output data
	public ArrayList<CBRobotController> robotControllers = new ArrayList<CBRobotController>();
	
	// Logic Layer
	public ArrayList<CBRule> rules = new ArrayList<>();
	public ArrayList<CBBehavior> behaviors = new ArrayList<>();
	public CBAutonomous autonomous;
	
	public static int gameMode=0;

	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	@Override
    public final void robotInit() {        	
		gameMode = CBGameMode.robotInit;

		cyborgInit();
		
	}
    
	public abstract void cyborgTestInit();
	public abstract void cyborgTestPeriodic();

    public abstract void cyborgInit();
	//public abstract void cyborgDisabledInit();
	//public abstract void cyborgDisabledPeriodic();
    public abstract void cyborgTeleopInit();
    
    
    
	@Override
    public final void autonomousInit() {
		gameMode = CBGameMode.autonomousInit;
		autonomous.init();
    }

	@Override
    public final void autonomousPeriodic() {
		gameMode = CBGameMode.autonomousPeriodic;

		// Update input interfaces
		hardwareAdapter.senseUpdate();		

		// Update Input Mappers
		for(CBGeneralMapper m:this.generalMappers) m.update(); 

		// Autonomous Control
		autonomous.update();

		// Let the robot do it's thing...
		robotControl();
    }

    
	
	@Override
    public final void teleopInit() {
		gameMode = CBGameMode.teleopInit;
    }

	/**
     * This function is called periodically during operator control
     */
	@Override
    public final void teleopPeriodic() {
		gameMode = CBGameMode.teleopPeriodic;
		
		// Update input interfaces
		hardwareAdapter.senseUpdate();
		
		// Update Input Mappers
		for(CBTeleOpMapper m:this.teleOpMappers) m.update(); 
		for(CBGeneralMapper  m:this.generalMappers)  m.update(); 

		
		// Let the robot do it's thing...
		robotControl();
    }
	
	
	
	@Override
	public final void testInit() {
		gameMode = CBGameMode.testInit;
		cyborgTestInit();
	}
    
    /**
     * This function is called periodically during test mode
     */
	@Override
    public final void testPeriodic() {
		gameMode = CBGameMode.testPeriodic;
		cyborgTestPeriodic();
    }


	private void robotControl() {
		// Update Rule and Behavior Processors 
		for(CBRule m:this.rules) m.update(); 
		for(CBBehavior m:this.behaviors) m.update(); 
		
		
		// Update Output Controllers
		for(CBRobotController m:this.robotControllers) m.update(); 
		
		
		// Update output interfaces
		hardwareAdapter.controlUpdate();

	}

    /**
     * This function is called periodically during disabled 
     */
	@Override
    public final void disabledInit() {
		gameMode = CBGameMode.disabledInit;
		
		// Update input interfaces
		hardwareAdapter.senseUpdate();
		
		// Update Input Mappers
		for(CBGeneralMapper m:this.generalMappers) m.update(); 

    }

    /**
     * This function is called periodically during disabled 
     */
	@Override
    public final void disabledPeriodic() {
		gameMode = CBGameMode.disabledPeriodic;
		
		// Update input interfaces
		hardwareAdapter.senseUpdate();
		
		// Update Input Mappers
		for(CBGeneralMapper m:this.generalMappers) m.update(); 

    }

	
}
