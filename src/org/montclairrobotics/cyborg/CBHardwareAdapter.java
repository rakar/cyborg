package org.montclairrobotics.cyborg;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.devices.CBMotorController;
import org.montclairrobotics.cyborg.devices.CBNavX;
import org.montclairrobotics.cyborg.devices.CBSolenoid;
import org.montclairrobotics.cyborg.utils.CBJoystickIndex;
import org.usfirst.frc.team555.robot.Robot.Device;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;


public class CBHardwareAdapter<T extends Enum<T>> extends CBModule {

	private int joystickCount;
	private ArrayList<Joystick> joysticks = new ArrayList<>(); 
	private int[][] buttonState = new int[4][16];
	private int[][] buttonTrans = new int[4][16];

	private ArrayList<Object> device = new ArrayList<>();

	
	public CBHardwareAdapter(Cyborg robot) {
		super(robot);
		device.ensureCapacity(Device.values().length);
	}
	
	public void senseUpdate() {
		for(int i=0; i<joystickCount;i++) {
			Joystick js = joysticks.get(i);
			int jsbc = js.getButtonCount();
			for(int j=0;j<jsbc;j++){
				int state = js.getRawButton(j+1)?1:0;
				buttonTrans[i][j] = state-buttonState[i][j];
				buttonState[i][j] = state;
			}
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
	
	public double getJoystickAxis(int stick, int axis) {
		return joysticks.get(stick).getRawAxis(axis);
	}
	
	public double getJoystickAxis(CBJoystickIndex index) {
		return getJoystickAxis(index.stickID,index.index);
	}
		
	public boolean getButtonState(int stick, int button) {
		return buttonState[stick][button-1]==1?true:false;
	}
	public boolean getButtonState(CBJoystickIndex index) {
		return getButtonState(index.stickID, index.index);
	}
	
	public boolean getButtonPress(int stick, int button) {
		return buttonTrans[stick][button-1]==1?true:false;
	}
	public boolean getButtonPress(CBJoystickIndex index) {
		return getButtonPress(index.stickID, index.index);
	}
	
	public boolean getButtonRelease(int stick, int button) {
		return buttonTrans[stick][button-1]==-1?true:false;
	}
	public boolean getButtonRelease(CBJoystickIndex index) {
		return getButtonRelease(index.stickID, index.index);
	}

	
	public int getPOV(int stick, int button) {
		return joysticks.get(stick).getPOV(button);
	}
	public int getPOV(CBJoystickIndex index) {
		return getPOV(index.stickID, index.index);
	}
	

	public CBHardwareAdapter<T> add(Enum<T> id, Object device) {
		this.device.ensureCapacity(id.ordinal()+1);
		this.device.set(id.ordinal(),device);
		return this;
	}
	
	public Object getDevice(T id) {
		return (Object)device.get(id.ordinal());
	}
		
	public CBMotorController getMotorController(Enum<T> id) {
		return (CBMotorController)device.get(id.ordinal());
	}
	
	public DigitalInput getDigitalInput(T id) {
		return (DigitalInput)device.get(id.ordinal());
	}
	
	public DigitalOutput getDigitalOutput(T id) {
		return (DigitalOutput)device.get(id.ordinal());
	}
	
	public CBSolenoid getSolenoidValve(T id) {
		return (CBSolenoid)device.get(id.ordinal());
	}
	
	public Encoder getEncoder(T id) {
		return (Encoder)device.get(id.ordinal());
	}
	
	public CBNavX getNavX(T id){
		return (CBNavX)device.get(id.ordinal());
	}

}
