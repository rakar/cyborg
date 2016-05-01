package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.CBGameMode;
import org.montclairrobotics.cyborg.Cyborg;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class CBDashboardChooser<T> implements CBDevice {
	String name;
	SendableChooser chooser;
	private int timingMode;
	private int timingDelay; 
	private int timingCount=0;
	private Object selected;
	
	public CBDashboardChooser(String name) {
		this.name = name;
		chooser = new SendableChooser();
	}
	
	public CBDashboardChooser<T> setTiming(int mode, int delay) {
		timingMode = mode;
		timingDelay = delay;
		timingCount = delay;
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
		if((Cyborg.gameMode & timingMode)!=0 ) {
			timingCount--;
			if(timingCount<=0 || (Cyborg.gameMode & CBGameMode.anyInit)!=0) {
				selected = chooser.getSelected();
				timingCount = timingDelay;
			}
		}
	}

	@Override
	public void controlUpdate() {
		
	}

}
