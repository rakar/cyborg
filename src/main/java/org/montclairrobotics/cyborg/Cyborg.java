package org.montclairrobotics.cyborg;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.cyborg.core.behaviors.CBAutonomous;
import org.montclairrobotics.cyborg.core.behaviors.CBBehavior;
import org.montclairrobotics.cyborg.core.behaviors.CBRule;
import org.montclairrobotics.cyborg.core.controllers.CBRobotController;
import org.montclairrobotics.cyborg.core.mappers.CBSensorMapper;
import org.montclairrobotics.cyborg.core.mappers.CBTeleOpMapper;
import org.montclairrobotics.cyborg.core.utils.CBGameMode;
import org.montclairrobotics.cyborg.core.utils.CBRunStatistics;
import org.montclairrobotics.cyborg.devices.CBHardwareAdapter;

import java.util.ArrayList;

/**
 * Framework specific replacement for TimedRobot. This class
 * is overridden to produce the main robot control class. This class
 * "hijacks" the standard TimedRobot interface and performs the
 * appropriate Cyborg operations instead.
 *
 * @author rich
 */
public abstract class Cyborg extends TimedRobot {

    public static CBHardwareAdapter hardwareAdapter;
    public static boolean simulationActive;
    //public static CBSimLink simLink;

    // Mapper/Controller Queues
    // Mapper Queues hold lists of mappers that convert raw input state information into meaningful status info
    private ArrayList<CBTeleOpMapper> teleOpMappers; // = new ArrayList<>();
    private ArrayList<CBSensorMapper> sensorMappers; // = new ArrayList<>();
    // Controller Queues hold lists of controllers that convert high-level requests into low-level raw control output data
    private ArrayList<CBRobotController> robotControllers; // = new ArrayList<>();

    // Logic Layer
    private ArrayList<CBRule> rules; // = new ArrayList<>();
    private ArrayList<CBBehavior> behaviors; // = new ArrayList<>();
    private ArrayList<CBAutonomous> autonomice; // = new ArrayList<>();

    public static int gameMode; //=0;
    //public NetworkTable table;

    public CBRunStatistics runStatistics; // = new CBRunStatistics();

    // General Configuration
    /**
     * Conversion from default angle unit to radians.
     */
    public static double angleToRadiansConversion = Math.PI / 180.0;

    @Deprecated
    public Cyborg addTeleOpMapper(CBTeleOpMapper... mappers) {
        addTeleOpMappers(mappers);
        return this;
    }

    public Cyborg addTeleOpMappers(CBTeleOpMapper... mappers) {
        for(CBTeleOpMapper mapper:mappers) {
            teleOpMappers.add(mapper);
        }
        return this;
    }

    @Deprecated
    public Cyborg addSensorMapper(CBSensorMapper... mappers) {
        addSensorMappers(mappers);
        return this;
    }

    public Cyborg addSensorMappers(CBSensorMapper... mappers) {
        for(CBSensorMapper mapper:mappers) {
            sensorMappers.add(mapper);
        }
        return this;
    }

    @Deprecated
    public Cyborg addRobotController(CBRobotController... controllers) {
        addRobotControllers(controllers);
        return this;
    }

    public Cyborg addRobotControllers(CBRobotController... controllers) {
        for(CBRobotController controller:controllers) {
            robotControllers.add(controller);
        }
        return this;
    }

    @Deprecated
    public Cyborg addRule(CBRule... rules) {
        addRules(rules);
        return this;
    }

    public Cyborg addRules(CBRule... rules) {
        for(CBRule rule:rules) {
            this.rules.add(rule);
        }
        return this;
    }

    @Deprecated
    public Cyborg addBehavior(CBBehavior... behaviors) {
        addBehaviors(behaviors);
        return this;
    }

    public Cyborg addBehaviors(CBBehavior... behaviors) {
        for(CBBehavior behavior:behaviors) {
            this.behaviors.add(behavior);
        }
        return this;
    }

    @Deprecated
    public Cyborg addAutonomous(CBAutonomous... autonomice) {
        addAutonomousBehaviors(autonomice);
        return this;
    }

    public Cyborg addAutonomousBehaviors(CBAutonomous... autonomice) {
        for(CBAutonomous autonomous:autonomice) {
            this.autonomice.add(autonomous);
        }
        return this;
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public final void robotInit() {
        gameMode = CBGameMode.robotInit;

        teleOpMappers = new ArrayList<>();
        sensorMappers = new ArrayList<>();
        robotControllers = new ArrayList<>();

        rules = new ArrayList<>();
        behaviors = new ArrayList<>();
        autonomice = new ArrayList<>();

        runStatistics = new CBRunStatistics();

        //table = NetworkTableInstance.getDefault().getTable("GRIP");

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

        for (CBSensorMapper m : this.sensorMappers) m.update();

        // Update Input Mappers
        if (gameMode == CBGameMode.teleopPeriodic) {
            for (CBTeleOpMapper m : this.teleOpMappers) m.update();
        }
        if (gameMode == CBGameMode.autonomousPeriodic) {
            for (CBAutonomous auto : this.autonomice) auto.update();
        }

        // Update Rule and Behavior Processors
        for (CBRule m : this.rules) m.update();
        for (CBBehavior m : this.behaviors) m.update();

        // Update Output Controllers
        for (CBRobotController m : this.robotControllers) m.update();

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
        return (Cyborg.gameMode & gameMode) != 0;
    }

    // utility functions
    public void logMessage(String msg) {
        logMessage(msg, false);
    }

    public void logMessage(String msg, boolean immediate) {
        System.out.println(msg);
        //if(immediate) telemetry.update();
    }

    public void putStringArray(String title, String[] msgs) {
        SmartDashboard.putStringArray(title, msgs);
    }
}
