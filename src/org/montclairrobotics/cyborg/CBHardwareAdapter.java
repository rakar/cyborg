package org.montclairrobotics.cyborg;


import java.util.ArrayList;
import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBContourReport;
import org.montclairrobotics.cyborg.devices.CBDevice;
import org.montclairrobotics.cyborg.devices.CBDeviceId;
import org.montclairrobotics.cyborg.devices.CBDigitalInput;
import org.montclairrobotics.cyborg.devices.CBDigitalOutput;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.utils.CBModule;

import edu.wpi.first.wpilibj.Joystick;


public class CBHardwareAdapter extends CBModule {

	private int joystickCount;
	private ArrayList<Joystick> joysticks = new ArrayList<>(); 

	private ArrayList<CBDevice> devices = new ArrayList<>();
	
	public CBHardwareAdapter(Cyborg robot) {
		super(robot);
	}
	
	public void configure() {
		for(CBDevice d: devices) {
			d.configure();
		}
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


	public CBDeviceId add(CBDevice device) {
		CBDeviceId id = new CBDeviceId();
		id.ordinal = devices.size();
		devices.add(device);
		device.configure();
		return id;
	}
	
	public CBDevice getDevice(CBDeviceId id) {
		if(id==null) return null;
		return devices.get(id.ordinal);
	}

	public CBAxis getAxis(CBDeviceId id) {
		return (CBAxis)getDevice(id);
	}
		
	public CBButton getButton(CBDeviceId id) {
		return (CBButton)getDevice(id);
	}
		
	public CBContourReport getContourReport(CBDeviceId id) {
		return (CBContourReport)getDevice(id);
	}
		
	public CBDigitalInput getDigitalInput(CBDeviceId id) {
		return (CBDigitalInput)getDevice(id);
	}
	
	public CBDigitalOutput getDigitalOutput(CBDeviceId id) {
		return (CBDigitalOutput)getDevice(id);
	}
	
	public CBEncoder getEncoder(CBDeviceId id) {
		return (CBEncoder)getDevice(id);
	}

	public CBSpeedController getSpeedController(CBDeviceId id) {
		return (CBSpeedController)getDevice(id);
	}
	
	public CBNavX getNavX(CBDeviceId id){
		return (CBNavX)getDevice(id);
	}

	public CBPov getPOV(CBDeviceId id) {
		return (CBPov)getDevice(id);
	}

	public CBSolenoid getSolenoidValve(CBDeviceId id) {
		return (CBSolenoid)getDevice(id);
	}
	
}
