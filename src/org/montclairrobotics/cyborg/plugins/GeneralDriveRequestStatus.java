package org.montclairrobotics.cyborg.plugins;

import java.awt.geom.Point2D;

import org.montclairrobotics.cyborg.DriveRequestStatus;

public class GeneralDriveRequestStatus extends DriveRequestStatus {

	public GeneralDriveRequestStatus() {
		// TODO Auto-generated constructor stub
	}
	
	public Point2D direction;
	public double rotation;
	
	public boolean gyroLock;
	

}
