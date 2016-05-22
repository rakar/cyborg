package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.utils.CBTimingController;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

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
		table = NetworkTable.getTable(baseKey);
	}
	
	public CBContourReport setTiming(int mode, int delay) {
		timer.setTiming(mode, delay);
		return this;
	}

	@Override
	public void senseUpdate() {
		if(timer.update()) {
			area = table.getNumberArray("area", new double[0]);
			if (area.length>0) {
				widthArray = table.getNumberArray("width", new double[0]);
				heightArray = table.getNumberArray("height", new double[0]);
				centerXArray = table.getNumberArray("centerX", new double[0]);
				centerYArray = table.getNumberArray("centerY", new double[0]);
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
	
	
	

}
