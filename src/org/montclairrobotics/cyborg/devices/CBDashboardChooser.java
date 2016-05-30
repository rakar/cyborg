package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.utils.CBTimingController;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CBDashboardChooser<T> implements CBDevice {
	String name;
	SendableChooser chooser;
	CBTimingController timer;
	private Object selected;
	
	public CBDashboardChooser(String name) {
		this.name = name;
		timer = new CBTimingController();
		chooser = new SendableChooser();
		selected = null;
	}
	
	public CBDashboardChooser<T> setTiming(int mode, int delay) {
		timer.setTiming(mode, delay);
		return this;
	}
	
	public CBDashboardChooser<T> addDefault(String name, T object) {
		chooser.addDefault(name, object);
		return this;
	}

	public CBDashboardChooser<T> addChoice(String name, T object) {
		chooser.addDefault(name, object);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public T getSelected() {
		return (T)selected;
	}
	

	@Override
	public void senseUpdate() {
		if(chooser!=null) {
			selected = chooser.getSelected();
		}
	}

	@Override
	public void controlUpdate() {
		
	}

	@Override
	public void configure() {
		SmartDashboard.putData(this.name, chooser);		
	}

}
