package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.core.utils.CBTimingController;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CBDashboardChooser<T> implements CBDevice {
	String name,subsystem;
	SendableChooser<T> chooser;
	CBTimingController timer;
	private T selected;

	public CBDashboardChooser(String name) {
		this.name = name;
		timer = new CBTimingController();
		chooser = new SendableChooser<T>();
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
	
	public T getSelected() {
		return selected;
	}
	

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public String getSubsystem() {
        return subsystem;
    }

    @Override
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public void initSendable(SendableBuilder builder) {

    }

	@Override
	public CBDeviceControl getDeviceControl() {
		return deviceControl;
	}

	CBDeviceControl deviceControl = new CBDeviceControl() {
		@Override
		public void init() {
			SmartDashboard.putData(name, chooser);
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
	};
}
