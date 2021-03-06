package org.montclairrobotics.cyborg.devices;


import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBModule;

import java.util.ArrayList;

/**
 * Main device catalog
 * Created devices are stored here and then accessed via
 * CBDeviceID objects.
 *
 * @author Rich Kopelow
 */
public class CBHardwareAdapter extends CBModule {

    public static final int joystickLimit = 6;
    private int joystickCount;
    private ArrayList<CBJoystick> joysticks = new ArrayList<>();

    private ArrayList<CBDevice> devices = new ArrayList<>();

    public CBHardwareAdapter(Cyborg robot) {
        super(robot);
        // create all the joysticks we can
        // whether they're real or not
        for (int i = joystickCount; i < joystickLimit; i++) {
            joysticks.add(new CBJoystick(i));
        }
        joystickCount = joysticks.size();
    }

    public void init() {
        for (CBDevice d : devices) {
            d.getDeviceControl().deviceInit();
        }
    }

    public void senseUpdate() {
        for (CBDevice d : devices) {
            d.getDeviceControl().senseUpdate();
        }
    }

    public void controlUpdate() {
        for (CBDevice d : devices) {
            d.getDeviceControl().controlUpdate();
        }
    }

    /*
     * Getters/Setters
     */
    @Deprecated
    public CBHardwareAdapter setJoystickCount(int count) {
        System.err.println("CBHardwareAdapter.setJoystickCount is not currently implemented and need not be used.");
        return this;
    }

    public int getJoystickCount() {
        return joystickCount;
    }

    public CBJoystick getJoystick(int index) {
        return joysticks.get(index);
    }

    public CBDeviceID add(CBDevice device) {
        CBDeviceID id = new CBDeviceID();
        id.ordinal = devices.size();
        //id.device = device;
        devices.add(device);
        //device.getDeviceControl().deviceInit(); // I don't think this should happen with the other init calls.
        initialized = false;
        return id;
    }

    public CBDevice getDevice(CBDeviceID id) {
        if (id == null) return null;
        return devices.get(id.ordinal);
    }

    public CBAxis getAxis(CBDeviceID id) {
        return (CBAxis) getDevice(id);
    }

    public CBAxis getDefaultedAxis(CBDeviceID id) {
        return CBAxis.getDefaulted((CBAxis) getDevice(id));
    }

    public CBButton getButton(CBDeviceID id) {
        return (CBButton) getDevice(id);
    }

    public CBButton getDefaultedButton(CBDeviceID id) {
        return CBButton.getDefaulted((CBButton) getDevice(id));
    }

    public CBCANSparkMax getCBCANSparkMax(CBDeviceID id) {
        return (CBCANSparkMax) getDevice(id);
    }

    public CBContourReport getContourReport(CBDeviceID id) {
        return (CBContourReport) getDevice(id);
    }

    public CBDigitalInput getDigitalInput(CBDeviceID id) {
        return (CBDigitalInput) getDevice(id);
    }

    public CBDigitalOutput getDigitalOutput(CBDeviceID id) {
        return (CBDigitalOutput) getDevice(id);
    }

    public CBEncoder getEncoder(CBDeviceID id) {
        return (CBEncoder) getDevice(id);
    }

    public CBNavX getNavX(CBDeviceID id) {
        return (CBNavX) getDevice(id);
    }

    public CBNavXYawSource getNavXYawSource(CBDeviceID id) {
        return (CBNavXYawSource) getDevice(id);
    }

    public CBNetworkTable getNetworkTable(CBDeviceID id) {
        return (CBNetworkTable) getDevice(id);
    }

    public CBPDB getPDB(CBDeviceID id) {
        return (CBPDB) getDevice(id);
    }

    public CBPov getPOV(CBDeviceID id) {
        return (CBPov) getDevice(id);
    }

    public CBServo getServo(CBDeviceID id) {
        return (CBServo) getDevice(id);
    }

    public CBSolenoid getSolenoidValve(CBDeviceID id) {
        return (CBSolenoid) getDevice(id);
    }

    public CBSpeedController getSpeedController(CBDeviceID id) {
        return (CBSpeedController) getDevice(id);
    }

    public CBTalon getTalon(CBDeviceID id) {
        return (CBTalon) getDevice(id);
    }

    public CBTalonSRX getTalonSRX(CBDeviceID id) {
        return (CBTalonSRX) getDevice(id);
    }

}
