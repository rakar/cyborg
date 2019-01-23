package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.core.utils.CBSource;

public class CBNetworkTable implements CBSource, CBDevice {
    String name, subsystem;
    private String sourceEntry;
    public NetworkTable table;

    public CBNetworkTable(String path) {
        table = NetworkTableInstance.getDefault().getTable(path);
    }

    public CBNetworkTable setSourceEntry(String entry) {
        sourceEntry = entry;
        return this;
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

    public CBNetworkTable setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBNetworkTable setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }

    @Override
    public double get() {
        return table.getEntry(sourceEntry).getDouble(0);
    }

    public CBEntrySource getEntrySource(String sourceEntry) {
        return new CBEntrySource(sourceEntry);
    }

    public class CBEntrySource implements CBSource  {
        String sourceEntry;

        public CBEntrySource(String entry) {
            sourceEntry = entry;
        }

        @Override
        public double get() {
            return table.getEntry(sourceEntry).getDouble(0);
        }
    }

    @Override
    public CBDeviceControl getDeviceControl() {
        return deviceControl;
    }

    CBDeviceControl deviceControl = new CBDeviceControl() {
        @Override
        public void init() {

        }

        @Override
        public void senseUpdate() {

        }

        @Override
        public void controlUpdate() {

        }
    };


}
