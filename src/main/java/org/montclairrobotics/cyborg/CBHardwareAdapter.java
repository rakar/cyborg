package org.montclairrobotics.cyborg;


import java.util.ArrayList;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDevice;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBDigitalInput;
import org.montclairrobotics.cyborg.devices.CBDigitalOutput;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBPDB;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.utils.CBModule;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Main device catalog
 * Created devices are stored here and then accessed via
 * CBDeviceID objects.
 */
public class CBHardwareAdapter extends CBModule {

	private int joystickCount;
	private ArrayList<Joystick> joysticks = new ArrayList<>(); 

	private ArrayList<CBDevice> devices = new ArrayList<>();
	
	public CBHardwareAdapter(Cyborg robot) {
		super(robot);
	}
	
	public void configure() {
		if(!Cyborg.simulationActive) {
			for (CBDevice d : devices) {
				d.configure();
			}
		} else {
			for (CBDevice d : devices) {
				d.configureSim();
			}
		}
	}
		
	public void senseUpdate() {
		if(!Cyborg.simulationActive) {
			for (CBDevice d : devices) {
				d.senseUpdate();
			}
		} else {
			for (CBDevice d : devices) {
				d.senseUpdateSim();
			}
		}
	}
	
	public void controlUpdate() {
		if(!Cyborg.simulationActive) {
			for (CBDevice d : devices) {
				d.controlUpdate();
			}
		} else {
			for (CBDevice d : devices) {
				d.controlUpdateSim();
			}
		}
	}
	
	
	/*
	 * Getters/Setters
	 */
	public CBHardwareAdapter setJoystickCount(int count) {
		for(int i=joystickCount;i<count;i++) {
			joysticks.add(new Joystick(i));
		}
		while(joysticks.size()>count) {
			joysticks.remove(count);
		}
		joystickCount=count;
		return this;
	}
	
	public int getJoystickCount() {
		return joystickCount;
	}
	
	public Joystick getJoystick(int index) {
		return joysticks.get(index);
	}	


	public CBDeviceID add(CBDevice device) {
		CBDeviceID id = new CBDeviceID();
		id.ordinal = devices.size();
		devices.add(device);
		device.configure();
		return id;
	}
	
	public CBDevice getDevice(CBDeviceID id) {
		if(id==null) return null;
		return devices.get(id.ordinal);
	}

	public CBAxis getAxis(CBDeviceID id) {
		return (CBAxis)getDevice(id);
	}
	
	public CBAxis getDefaultedAxis(CBDeviceID id) {
		return CBAxis.getDefaulted((CBAxis)getDevice(id));
	}
		
	public CBButton getButton(CBDeviceID id) {
		return (CBButton)getDevice(id);
	}

	public CBButton getDefaultedButton(CBDeviceID id) {
		return CBButton.getDefaulted((CBButton)getDevice(id));
	}
	
	public CBContourReport getContourReport(CBDeviceID id) {
		return (CBContourReport)getDevice(id);
	}
		
	public CBDigitalInput getDigitalInput(CBDeviceID id) {
		return (CBDigitalInput)getDevice(id);
	}
	
	public CBDigitalOutput getDigitalOutput(CBDeviceID id) {
		return (CBDigitalOutput)getDevice(id);
	}
	
	public CBEncoder getEncoder(CBDeviceID id) {
		return (CBEncoder)getDevice(id);
	}

	public CBSpeedController getSpeedController(CBDeviceID id) {
		return (CBSpeedController)getDevice(id);
	}
	
	public CBNavX getNavX(CBDeviceID id){
		return (CBNavX)getDevice(id);
	}

	public CBPov getPOV(CBDeviceID id) {
		return (CBPov)getDevice(id);
	}

	public CBSolenoid getSolenoidValve(CBDeviceID id) {
		return (CBSolenoid)getDevice(id);
	}
	
	public CBPDB getPDB(CBDeviceID id) {
		return (CBPDB)getDevice(id);
	}
	
}
