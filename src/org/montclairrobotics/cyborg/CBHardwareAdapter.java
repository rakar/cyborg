package org.montclairrobotics.cyborg;

import java.util.ArrayList;
import java.util.HashMap;

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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;


public class CBHardwareAdapter<T extends Enum<T>> extends CBModule {

	private int joystickCount;
	private ArrayList<Joystick> joysticks = new ArrayList<>(); 

	private ArrayList<CBDevice> devices = new ArrayList<>();
	//private HashMap<Object, Integer> devkey = new HashMap<Object, Integer>();
	
	public CBHardwareAdapter(Cyborg robot, int deviceCount) {
		super(robot);
		devices.ensureCapacity(deviceCount);
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
		/*
		int ptr = this.devices.size();
		this.devices.add(device);
		devkey.put(id, ptr);
		 */
		this.devices.set(id.ordinal(), device);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public CBDevice getDevice(Object id) {
		/*
		Integer ptr = new Integer(0);
		ptr = devkey.get(((Enum<T>) id));
		if(ptr==null) return null;
		CBDevice dev =devices.get(ptr);
		 */
		if(id==null) return null;
		return devices.get(((Enum<T>) id).ordinal());
	}

	public CBAxis getAxis(Object id) {
		return (CBAxis)getDevice(id);
	}
		
	public CBButton getButton(Object id) {
		return (CBButton)getDevice(id);
	}
		
	public CBDigitalInput getDigitalInput(Object id) {
		return (CBDigitalInput)getDevice(id);
	}
	
	public CBDigitalOutput getDigitalOutput(Object id) {
		return (CBDigitalOutput)getDevice(id);
	}
	
	public CBEncoder getEncoder(Object id) {
		return (CBEncoder)getDevice(id);
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

	public CBSolenoid getSolenoidValve(Object id) {
		return (CBSolenoid)getDevice(id);
	}
	
}
