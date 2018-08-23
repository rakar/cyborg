package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.simulation.CBIDigitalInput;
import org.montclairrobotics.cyborg.simulation.CBSimDigitalInput;
import org.montclairrobotics.cyborg.simulation.CBWPIDigitalInput;

public class CBDigitalInput extends CBDeviceInit implements CBIDigitalInput{
    CBIDigitalInput digitalInput;

	public CBDigitalInput(int channel) {
		if(Cyborg.simulationActive) {
            digitalInput = new CBSimDigitalInput(channel);
        } else {
		    digitalInput = new CBWPIDigitalInput(channel);
        }
	}

    public boolean get(){
	    return digitalInput.get();
    }

    public int getChannel(){
	    return digitalInput.getChannel();
    }

    public String getName(){
	    return digitalInput.getName();
    }

    public void setName(String name) {
	    digitalInput.setName(name);
    }

    public void setName(String subsystem, String name) {
	    setName(name);
	    setSubsystem(subsystem);
    }

    public String getSubsystem() {
	    return digitalInput.getSubsystem();
    }

    public void setSubsystem(String subsystem) {
	    digitalInput.setSubsystem(subsystem);
    }

    public void initSendable(SendableBuilder builder) {
	    digitalInput.initSendable(builder);
    }

    public CBDigitalInput setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBDigitalInput setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }



    public void senseUpdate() {
	}

	public void controlUpdate() {
	}

	public void init() {
	}

}
