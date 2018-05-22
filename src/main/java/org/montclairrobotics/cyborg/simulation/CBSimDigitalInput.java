package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;

public class CBSimDigitalInput implements CBIDigitalInput {

    public class CBSimDigitalInputData {
        String subsystem, name;
        int channel;
        boolean value;

        public CBSimDigitalInputData(int channel) {
            this.channel = channel;
        }
    }

    CBSimDigitalInputData simData;

    public CBSimDigitalInput(int channel) {
        simData = new CBSimDigitalInputData(channel);
        Cyborg.simLink.digitalInputs.add(simData);
    }

    @Override
    public boolean get() {
        return simData.value;
    }

    @Override
    public int getChannel() {
        return simData.channel;
    }

    @Override
    public String getName() {
        return simData.name;
    }

    @Override
    public void setName(String name) {
        simData.name = name;
    }

    @Override
    public void setName(String subsystem, String name) {
        simData.name = name;
        simData.subsystem = subsystem;
    }

    @Override
    public String getSubsystem() {
        return simData.subsystem;
    }

    @Override
    public void setSubsystem(String subsystem) {
        simData.subsystem = subsystem;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Digital Input");
        builder.addBooleanProperty("Value", this::get, null);
    }
}
