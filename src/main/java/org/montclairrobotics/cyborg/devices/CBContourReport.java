package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.montclairrobotics.cyborg.utils.CBTimingController;

public class CBContourReport implements CBDevice{
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
	}
	
	public CBContourReport setTiming(int mode, int delay) {
		timer.setTiming(mode, delay);
		return this;
	}

	@Override
	public void senseUpdate() {
		if(timer.update()) {
			area = table.getEntry("area").getDoubleArray(new double[0]);
			if (area.length>0) {
				widthArray = table.getEntry("width").getDoubleArray(new double[0]);
				heightArray = table.getEntry("height").getDoubleArray(new double[0]);
				centerXArray = table.getEntry("centerX").getDoubleArray(new double[0]);
				centerYArray = table.getEntry("centerY").getDoubleArray(new double[0]);
			} else {
				if (widthArray.length>0) {
					widthArray =  new double[0];
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
	public void configure() {
		// TODO Auto-generated method stub
	}
}
