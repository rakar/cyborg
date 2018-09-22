package org.montclairrobotics.cyborg;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.montclairrobotics.cyborg.behaviors.CBAutonomous;
import org.montclairrobotics.cyborg.behaviors.CBBehavior;
import org.montclairrobotics.cyborg.behaviors.CBRule;
import org.montclairrobotics.cyborg.controllers.CBRobotController;
import org.montclairrobotics.cyborg.data.CBControlData;
import org.montclairrobotics.cyborg.data.CBRequestData;
import org.montclairrobotics.cyborg.data.CBLogicData;
import org.montclairrobotics.cyborg.devices.CBHardwareAdapter;
import org.montclairrobotics.cyborg.mappers.CBSensorMapper;
import org.montclairrobotics.cyborg.mappers.CBTeleOpMapper;
import org.montclairrobotics.cyborg.utils.CBGameMode;
import org.montclairrobotics.cyborg.utils.CBRunStatistics;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.cyborg.simulation.CBSimLink;

/**
 * Framework specific replacement for IterativeRobot. This class
 * is overridden to produce the main robot control class. This class
 * "hijacks" the standard IterativeRobot interface and performs the
 * appropriate Cyborg operations instead.
 * @author rich
 */
public abstract class Cyborg extends IterativeRobot {

	public static CBHardwareAdapter hardwareAdapter;
	public static boolean simulationActive;
	public static CBSimLink simLink;

	// Data Stores
	// Data Stores represent high-level meaningful messages
	// In order to improve the CyborgInit code and avoid mucho casting,
    // and to ensure that any reference to them is generalized
    // for example by using setters to connect cyborg objects to
    // the relevant data. Most of this is done already. The real
    // changes will be in the class that extends Cyborg, which
    // will need to create the data stores, and in the custom
    // code that references the data stores. The custom references
    // should be able to be simplified.
	// public static CBRequestData requestData;
	// public static CBControlData controlData;
	// public static CBLogicData logicData;

	// Mapper/Controller Queues
	// Mapper Queues hold lists of mappers that convert raw input state information into meaningful status info
	private ArrayList<CBTeleOpMapper> teleOpMappers = new ArrayList<>();
	private ArrayList<CBSensorMapper> sensorMappers = new ArrayList<>();
	// Controller Queues hold lists of controllers that convert high-level requests into low-level raw control output data
	private ArrayList<CBRobotController> robotControllers = new ArrayList<>();
	
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
	
	public Cyborg addSensorMapper(CBSensorMapper mapper) {
		sensorMappers.add(mapper);
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
		autonomice.add(autonomous);
		return this;
	}
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	@Override
    public final void robotInit() {        	
		gameMode = CBGameMode.robotInit;
		table = NetworkTableInstance.getDefault().getTable("GRIP");
		cyborgInit();
		moduleInit();
	}

	private void moduleInit() {
        // Init input interfaces
        hardwareAdapter.moduleInit();

        // Init Input Mappers
        for (CBTeleOpMapper m : this.teleOpMappers) m.moduleInit();
        for (CBSensorMapper m : this.sensorMappers) m.moduleInit();

        // Init Rule and Behavior Processors
        for (CBRule m : this.rules) m.moduleInit();
        for (CBAutonomous auto : this.autonomice) auto.moduleInit();
        for (CBBehavior m : this.behaviors) m.moduleInit();

        // Init Output Controllers
        for (CBRobotController m : this.robotControllers) m.moduleInit();
    }

    private void robotUpdate() {
        // Update input interfaces
        hardwareAdapter.senseUpdate();

        // Update Input Mappers
        if(gameMode==CBGameMode.teleopPeriodic) {
            for (CBTeleOpMapper m : this.teleOpMappers) m.update();
        }
        for(CBSensorMapper m:this.sensorMappers) m.update();

        // Update Rule and Behavior Processors
        for(CBRule m:this.rules) m.update();
        if(gameMode==CBGameMode.autonomousPeriodic) {
            for (CBAutonomous auto : this.autonomice) auto.update();
        }
        for(CBBehavior m:this.behaviors) m.update();

        // Update Output Controllers
        for(CBRobotController m:this.robotControllers) m.update();

        // Update output interfaces
        hardwareAdapter.controlUpdate();
    }


    public abstract void cyborgInit();
	public abstract void cyborgDisabledInit();
    public abstract void cyborgAutonomousInit();
	public abstract void cyborgTeleopInit();
	public abstract void cyborgTestInit();


    @Override
    public final void disabledInit() {
        gameMode = CBGameMode.disabledInit;
        cyborgDisabledInit();
        moduleInit();
    }
    @Override
    public final void disabledPeriodic() {
        gameMode = CBGameMode.disabledPeriodic;
        robotUpdate();
    }


	@Override
    public final void autonomousInit() {
		gameMode = CBGameMode.autonomousInit;
		cyborgAutonomousInit();
        moduleInit();
    }
    public final void autonomousPeriodic() {
		gameMode = CBGameMode.autonomousPeriodic;
		robotUpdate();
    }

	
	@Override
    public final void teleopInit() {
		gameMode = CBGameMode.teleopInit;
		cyborgTeleopInit();
		runStatistics.teleopInitUpdate();
		moduleInit();
    }
	@Override
    public final void teleopPeriodic() {
		gameMode = CBGameMode.teleopPeriodic;		
		runStatistics.teleopPeriodicUpdate();
		SmartDashboard.putNumber("cyclesPERsecond", runStatistics.averageCycles);
		robotUpdate();
    }

	
	@Override
	public final void testInit() {
		gameMode = CBGameMode.testInit;
		cyborgTestInit();
		moduleInit();
	}
	@Override
    public final void testPeriodic() {
		gameMode = CBGameMode.testPeriodic;
        robotUpdate();
    }

    public static boolean isGameMode(int gameMode) {
    	return (Cyborg.gameMode & gameMode)!=0;
	}
}
