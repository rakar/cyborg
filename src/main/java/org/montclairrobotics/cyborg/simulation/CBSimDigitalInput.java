package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;

public class CBSimDigitalInput implements Sendable {

    public class CBSimDigitalInputData {
        String subsystem,name;
        int channel;
        boolean value;

        public CBSimDigitalInputData(int channel) {
            this.channel = channel;
        }
    }

    CBSimDigitalInputData simData;
    DigitalInput digitalInput;

    public CBSimDigitalInput (int channel){
        if(Cyborg.simulationActive) {
            simData = new CBSimDigitalInputData(channel);
            Cyborg.simLink.DIs[channel] = simData;
        } else {
            digitalInput = new DigitalInput(channel);
        }
    }

    public boolean get() {
        if(Cyborg.simulationActive) {
            return simData.value;
        } else {
            return digitalInput.get();
        }
    }

    public int getChannel(){
        if(Cyborg.simulationActive) {
            return simData.channel;
        } else {
            return digitalInput.getChannel();
        }
    }

    @Override
    public String getName() {
        if(Cyborg.simulationActive) {
            return simData.name;
        } else {
            return digitalInput.getName();
        }
    }

    @Override
    public void setName(String name) {
        if(Cyborg.simulationActive) {
            simData.name=name;
        } else {
            digitalInput.setName(name);
        }
    }

    @Override
    public void setName(String subsystem, String name) {
        if(Cyborg.simulationActive) {
            simData.name=name;
            simData.subsystem = subsystem;
        } else {
            digitalInput.setName(subsystem, name);
        }
    }

    @Override
    public String getSubsystem() {
        if(Cyborg.simulationActive) {
            return simData.subsystem;
        } else {
            return digitalInput.getSubsystem();
        }
    }

    @Override
    public void setSubsystem(String subsystem) {
        if(Cyborg.simulationActive) {
            simData.subsystem=subsystem;
        } else {
            digitalInput.setSubsystem(subsystem);
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        if(Cyborg.simulationActive) {
            //
        } else {
            digitalInput.initSendable(builder);
        }
    };
}
