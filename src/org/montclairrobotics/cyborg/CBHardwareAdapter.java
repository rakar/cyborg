package org.montclairrobotics.cyborg;

import java.util.ArrayList;
import java.util.HashMap;

import org.montclairrobotics.cyborg.devices.CBAxis;
import org.montclairrobotics.cyborg.devices.CBButton;
import org.montclairrobotics.cyborg.devices.CBDevice;
import org.montclairrobotics.cyborg.devices.CBMotorController;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBPov;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.utils.CBModule;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;


public class CBHardwareAdapter<T extends Enum<T>> extends CBModule {

	private int joystickCount;
	private ArrayList<Joystick> joysticks = new ArrayList<>(); 

	private ArrayList<CBDevice> device = new ArrayList<>();
	private HashMap<Object, Integer> devkey = new HashMap<Object, Integer>();
	
	public CBHardwareAdapter(Cyborg robot, int deviceCount) {
		super(robot);
		device.ensureCapacity(deviceCount);
	}
	
	
	public void senseUpdate() {
		for(CBDevice d: device) {
			d.senseUpdate();
		}
	}
	
	public void controlUpdate() {
		for(CBDevice d: device) {
			d.controlUpdate();
		}
	}
	
	
	/*
	 * Getters/Setters
	 */
	public void setJoystickCount(int count) {
		for(int i=joystickCount;i<count;i++) {
			joysticks.add(new Joystick(i));
		}
		while(joysticks.size()>count) {
			joysticks.remove(count);
		}
		joystickCount=count;
	}
	
	public int getJoystickCount() {
		return joystickCount;
	}
	
	public Joystick getJoystick(int index) {
		return joysticks.get(index);
	}	


	public CBHardwareAdapter<T> add(Enum<T> id, CBDevice device) {
		int ptr = this.device.size();
		this.device.add(device);
		devkey.put(id, ptr);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public CBDevice getDevice(Object id) {
		Integer ptr = devkey.get(((Enum<T>) id));
		if(ptr==null) return null;
		return device.get(ptr);
	}

	public CBAxis getAxis(Object id) {
		return (CBAxis)getDevice(id);
	}
		
	public CBButton getButton(Object id) {
		return (CBButton)getDevice(id);
	}
		
	public CBMotorController getMotorController(Object id) {
		return (CBMotorController)getDevice(id);
	}
	
	public CBNavX getNavX(Object id){
		return (CBNavX)getDevice(id);
	}

	public CBPov getPOV(Object id) {
		return (CBPov)getDevice(id);
	}

	public DigitalInput getDigitalInput(Object id) {
		return (DigitalInput)getDevice(id);
	}
	
	public DigitalOutput getDigitalOutput(Object id) {
		return (DigitalOutput)getDevice(id);
	}
	
	public CBSolenoid getSolenoidValve(Object id) {
		return (CBSolenoid)getDevice(id);
	}
	
	public Encoder getEncoder(Object id) {
		return (Encoder)getDevice(id);
	}
	
}
