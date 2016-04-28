package org.montclairrobotics.cyborg;

import java.util.ArrayList;

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
		this.device.ensureCapacity(id.ordinal()+1);
		this.device.set(id.ordinal(),device);
		return this;
	}
	
	public CBDevice getDevice(Enum<T> id) {
		return device.get(id.ordinal());
	}

	public CBAxis getAxis(Enum<T> id) {
		return (CBAxis)device.get(id.ordinal());
	}
		
	public CBButton getButton(Enum<T> id) {
		return (CBButton)device.get(id.ordinal());
	}
		
	public CBMotorController getMotorController(Enum<T> id) {
		return (CBMotorController)device.get(id.ordinal());
	}
	
	public CBNavX getNavX(Enum<T> navx){
		return (CBNavX)device.get(navx.ordinal());
	}

	public CBPov getPOV(Enum<T> id) {
		return (CBPov)device.get(id.ordinal());
	}

	public DigitalInput getDigitalInput(Enum<T> id) {
		return (DigitalInput)device.get(id.ordinal());
	}
	
	public DigitalOutput getDigitalOutput(Enum<T> id) {
		return (DigitalOutput)device.get(id.ordinal());
	}
	
	public CBSolenoid getSolenoidValve(Enum<T> id) {
		return (CBSolenoid)device.get(id.ordinal());
	}
	
	public Encoder getEncoder(Enum<T> id) {
		return (Encoder)device.get(id.ordinal());
	}
	
}
