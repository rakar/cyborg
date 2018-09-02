package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.utils.CBTimingController;

public class CBContourReport implements CBDevice {
	String name,subsystem;
	NetworkTable table;
	String baseKey;
	CBTimingController timer;
	public double[] widthArray = new double[0];
	public double[] centerXArray = new double[0];
	public double[] centerYArray = new double[0];
	public double[] heightArray = new double[0];
	public double[] area = new double[0];
	public int largest = -1;
	public double centerX = -1;
	public double centerY = -1;

	public CBContourReport(String key) {
		baseKey = key;
		timer = new CBTimingController();
		table = NetworkTableInstance.getDefault().getTable(baseKey);
		//setName(subsystem, name);
	}
	
	public CBContourReport setTiming(int mode, int delay) {
		timer.setTiming(mode, delay);
		return this;
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

	public CBContourReport setDeviceName(String name) {
		setName(name);
		return this;
	}

	public CBContourReport setDeviceName(String subsystem, String name) {
		setName(subsystem, name);
		return this;
	}

	@Override
	public CBDeviceControl getDeviceControl() {
		return deviceControl;
	}

	CBDeviceControl deviceControl = new CBDeviceControl() {
		@Override
		public void senseUpdate() {
			if(timer.update().getState()) {
				area = table.getEntry("area").getDoubleArray(new double[0]);
				if (area.length>0) {
					widthArray = table.getEntry("width").getDoubleArray(new double[0]);
					heightArray = table.getEntry("height").getDoubleArray(new double[0]);
					centerXArray = table.getEntry("centerX").getDoubleArray(new double[0]);
					centerYArray = table.getEntry("centerY").getDoubleArray(new double[0]);
				} else {
					if (widthArray.length>0) {
						widthArray = new double[0];
						heightArray = new double[0];
						centerXArray = new double[0];
						centerYArray = new double[0];
					}
				}
				double maxArea = -1.0;
				largest = -1;
				centerX = -1;
				for(int i=0; i<area.length;i++) {
					if(area[i]>maxArea) {
						maxArea=area[i];
						largest = i;
						if (i<centerXArray.length) {
							centerX = centerXArray[i];
							centerY = centerYArray[i];
						}
					}
				}
			}
		}

		@Override
		public void controlUpdate() {
			// TODO Auto-generated method stub

		}

		@Override
		public void init() {
			// TODO Auto-generated method stub
		}

	};
}
