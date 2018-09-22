package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;

import static org.montclairrobotics.cyborg.Cyborg.hardwareAdapter;

/**
 * Represents a generic speed controller.
 */
public abstract class CBSpeedController implements CBDevice {
    String name,subsystem;
    protected CBPDB pdb;
    protected int pdbChannel;
    protected CBSpeedControllerFaultCriteria faultCriteria;

    public abstract CBSpeedController pidWrite(double output);

    public abstract double get();

	//CBSpeedController set(double speed, byte syncGroup);

    public abstract CBSpeedController set(double speed);

    public abstract CBSpeedController setInverted(boolean isInverted);

    public abstract boolean getInverted();

    public abstract CBSpeedController disable();

    public abstract CBSpeedController stopMotor();

    public double getActualCurrent() {
        if(pdb==null) throw new RuntimeException("Power Source not configured for SpeedController "+getName());
        return pdb.getCurrent(pdbChannel);
    }

    public CBSpeedController setPowerSource(CBDeviceID pdb, int channel) {
        this.pdb = hardwareAdapter.getPDB(pdb);
        this.pdbChannel = channel;
        return this;
    }

    public CBSpeedController setSpeedControllerFaultCriteria(CBSpeedControllerFaultCriteria criteria){
        this.faultCriteria = criteria;
        return this;
    }

    public CBSpeedControllerFault getSpeedControllerFault() {
        if(faultCriteria== null || pdb==null) return null;
        return faultCriteria.check(getActualCurrent(),this.get());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSubsystem() {
        return subsystem;
    }

    @Override
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public void initSendable(SendableBuilder builder) {

    }

    public CBSpeedController setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBSpeedController setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }

}