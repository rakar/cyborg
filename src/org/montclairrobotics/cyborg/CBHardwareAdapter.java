package org.montclairrobotics.cyborg;


import java.util.ArrayList;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDevice;
import org.montclairrobotics.cyborg.devices.CBDigitalInput;
import org.montclairrobotics.cyborg.devices.CBDigitalOutput;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBMotorController;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.utils.CBModule;
import org.montclairrobotics.cyborg.utils.CBDeviceID;

import edu.wpi.first.wpilibj.Joystick;


public class CBHardwareAdapter extends CBModule {

	private int joystickCount;
	private ArrayList<Joystick> joysticks = new ArrayList<>(); 

	private ArrayList<CBDevice> devices = new ArrayList<>();
	
	public CBHardwareAdapter(Cyborg robot) {
		super(robot);
		//devices.ensureCapacity(deviceCount);
	}
	
	
	public void senseUpdate() {
		for(CBDevice d: devices) {
			d.senseUpdate();
		}
	}
	
	public void controlUpdate() {
		for(CBDevice d: devices) {
			d.controlUpdate();
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
		return id;
	}
	
	public CBDevice getDevice(CBDeviceID id) {
		if(id==null) return null;
		return devices.get(id.ordinal);
	}

	public CBAxis getAxis(CBDeviceID id) {
		return (CBAxis)getDevice(id);
	}
		
	public CBButton getButton(CBDeviceID id) {
		return (CBButton)getDevice(id);
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

	public CBMotorController getMotorController(CBDeviceID id) {
		return (CBMotorController)getDevice(id);
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
	
}
