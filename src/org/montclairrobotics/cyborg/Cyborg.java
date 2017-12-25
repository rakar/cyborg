package org.montclairrobotics.cyborg;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.behaviors.CBBehavior;
import org.montclairrobotics.cyborg.controllers.CBRobotController;
import org.montclairrobotics.cyborg.data.CBControlData;
import org.montclairrobotics.cyborg.data.CBRequestData;
import org.montclairrobotics.cyborg.devices.CBAxisList;
import org.montclairrobotics.cyborg.devices.CBBuilder;
import org.montclairrobotics.cyborg.devices.CBButtonList;
import org.montclairrobotics.cyborg.devices.CBCANBus;
import org.montclairrobotics.cyborg.devices.CBDIOBus;
import org.montclairrobotics.cyborg.devices.CBPDBSlots;
import org.montclairrobotics.cyborg.devices.CBStickList;
import org.montclairrobotics.cyborg.data.CBLogicData;
import org.montclairrobotics.cyborg.mappers.CBCustomMapper;
import org.montclairrobotics.cyborg.mappers.CBTeleOpMapper;
import org.montclairrobotics.cyborg.utils.CBRunStatistics;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author rich
 *
 */
public abstract class Cyborg extends IterativeRobot {

	public static CBHardwareAdapter hardwareAdapter;

	// Build control system
    public CBCANBus canBus = new CBCANBus();
    public CBPDBSlots pdbSlots = new CBPDBSlots();
    public CBDIOBus dioBus = new CBDIOBus();
    public CBStickList stickList = new CBStickList();
    public CBAxisList axisList = new CBAxisList(stickList);
    public CBButtonList buttonList = new CBButtonList(stickList);
    
    public CBBuilder builder = new CBBuilder()
    		.setCANBus(canBus)
    		.setPDBSlots(pdbSlots)
    		.setDIOBus(dioBus)
    		.setStickList(stickList)
    		.setAxisList(axisList)
    		.setButtonList(buttonList);
    		
    
    
    
    
    

	// Data Stores
	// Data Stores represent high-level meaningful messages
	//public static CBDriveRequestData driveRequestData;
	//public static CBDriveControlData driveControlData;
	public static CBRequestData requestData;
	public static CBControlData controlData;
	public static CBLogicData logicData;

	
	// Mapper/Controller Queues
	// Mapper Queues hold lists of mappers that convert raw input state information into meaningful status info
	private ArrayList<CBTeleOpMapper> teleOpMappers = new ArrayList<CBTeleOpMapper>();
	private ArrayList<CBCustomMapper> customMappers = new ArrayList<CBCustomMapper>();
	// Controller Queues hold lists of controllers that convert high-level requests into low-level raw control output data
	private ArrayList<CBRobotController> robotControllers = new ArrayList<CBRobotController>();
	
	// Logic Layer
	private ArrayList<CBRule> rules = new ArrayList<>();
	private ArrayList<CBBehavior> behaviors = new ArrayList<>();
	private ArrayList<CBAutonomous> autonomice = new ArrayList<>();
	
	public static int gameMode=0;
	public NetworkTable table;
	
	public CBRunStatistics runStatistics = new CBRunStatistics();
	
	// General Configuration
	/**
	 * Conversion from default angle unit to radians.
	 */
	public static double angleToRadiansConversion = Math.PI/180.0;
	
	public Cyborg addTeleOpMapper(CBTeleOpMapper mapper) {
		teleOpMappers.add(mapper);
		return this;
	}
	
	public Cyborg addCustomMapper(CBCustomMapper mapper) {
		customMappers.add(mapper);
		return this;
	}
	
	public Cyborg addRobotController(CBRobotController controller) {
		robotControllers.add(controller);
		return this;
	}
	
	public Cyborg addRule(CBRule rule) {
		rules.add(rule);
		return this;
	}
	
	public Cyborg addBehavior(CBBehavior behavior) {
		behaviors.add(behavior);
		return this;
	}
	
	public Cyborg addAutonomous(CBAutonomous autonomous) {
		this.autonomice.add(autonomous);
		return this;
	}
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	@Override
    public final void robotInit() {        	
		gameMode = CBGameMode.robotInit;
		table = NetworkTable.getTable("GRIP");

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
		for(CBAutonomous auto:this.autonomice) auto.init();
    }

	@Override
    public final void autonomousPeriodic() {
		gameMode = CBGameMode.autonomousPeriodic;

		// Update input interfaces
		hardwareAdapter.senseUpdate();		

		// Update Input Mappers
		for(CBCustomMapper m:this.customMappers) m.update(); 

		// Autonomous Control
		for(CBAutonomous auto:this.autonomice) auto.update();

		// Let the robot do it's thing...
		robotControl();
    }

    
	
	@Override
    public final void teleopInit() {
		gameMode = CBGameMode.teleopInit;
		runStatistics.teleopInitUpdate();
		
    }

	/**
     * This function is called periodically during operator control
     */
	@Override
    public final void teleopPeriodic() {
		gameMode = CBGameMode.teleopPeriodic;		
		runStatistics.teleopPeriodicUpdate();
		
		SmartDashboard.putNumber("cyclesPERsecond", runStatistics.averageCycles);
		
		// TODO: Kill this diagnostic
		double[] ys = table.getNumberArray("mynewreport/centerX",new double[0]);
		if (ys.length>0) {
			SmartDashboard.putNumber("y0", ys[0]);
		}
		//
		
		// Update input interfaces
		hardwareAdapter.senseUpdate();
		
		// Update Input Mappers
		for(CBTeleOpMapper m:this.teleOpMappers) m.update(); 
		for(CBCustomMapper  m:this.customMappers)  m.update(); 

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
		//hardwareAdapter.senseUpdate();
		
		// Update Input Mappers
		//for(CBGeneralMapper m:this.generalMappers) m.update(); 

    }

    /**
     * This function is called periodically during disabled 
     */
	@Override
    public final void disabledPeriodic() {
		gameMode = CBGameMode.disabledPeriodic;
		
		// Update input interfaces
		//hardwareAdapter.senseUpdate();
		
		// Update Input Mappers
		//for(CBGeneralMapper m:this.generalMappers) m.update(); 

    }

	
}
