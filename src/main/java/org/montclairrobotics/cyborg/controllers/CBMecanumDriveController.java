package org.montclairrobotics.cyborg.controllers;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBDriveModule;
import org.montclairrobotics.cyborg.data.CBStdDriveControlData;
import org.montclairrobotics.cyborg.utils.CB2DVector;

public class CBMecanumDriveController extends CBDriveController {
	protected ArrayList<CBDriveModule> driveModules = new ArrayList<>();
	protected ArrayList<Double> momentArms = new ArrayList<>();
	protected ArrayList<Double> vTrans = new ArrayList<>();
	protected ArrayList<Double> vCorr = new ArrayList<>();
	protected double tErr;
	protected double minMA = Double.MAX_VALUE;
	protected ArrayList<Double> vTheta = new ArrayList<>();
	protected ArrayList<Double> vTotal = new ArrayList<>();
	protected double maxAbsV;
	protected double qtrPi = Math.PI/4.0;
	protected double halfPi = Math.PI/2.0;
	protected int dmCount = 0;
	protected CBStdDriveControlData dcd=null;
	

	public CBMecanumDriveController(Cyborg robot) {
		super(robot);
		if(Cyborg.controlData.driveData instanceof CBStdDriveControlData) {
			dcd = (CBStdDriveControlData)Cyborg.controlData.driveData;
		} else {
			//System.out.println("Error: Invalid DriveControlData type for CBMecanumDriveController");
			throw new RuntimeException("Error: Invalid DriveControlData type for CBMecanumDriveController");
		}
		System.err.println("Warning: CBMecanumDriveController implementation is highly experimental.");

	}

	@Override
	public void update() {
		if(Cyborg.controlData.driveData.active) {
			calculate();
			for(int i=0;i<dmCount;i++) {
				CBDriveModule dm = driveModules.get(i);
				dm.update(vTotal.get(i));
			}				
		}
	}

	protected void calculate() {
		double theta = dcd.direction.getAngleRad()-qtrPi;
		double vd = dcd.direction.getMag();
		tErr = 0;
		for(int i=0;i<dmCount;i++) {
			double vtrans = vd*Math.cos(theta-i*halfPi);
			vTrans.set(i, vtrans);
			tErr+=momentArms.get(i)*vtrans;
			double vtheta = 4.0*dcd.rotation*minMA;
			vTheta.set(i,vtheta);
		}				
		maxAbsV=0;
		for(int i=0;i<dmCount;i++) {
			double vtotal = vTrans.get(i)+(vTheta.get(i)-tErr)/4.0*momentArms.get(i);
			vTotal.set(i, vtotal);
			double absV = Math.abs(vtotal);
			if (absV>maxAbsV) maxAbsV=absV;
		}		
		if(maxAbsV>1.0) {
			for(int i=0;i<dmCount;i++) {
				vTotal.set(i,vTotal.get(i)/maxAbsV);
			}
		}

	}
	
	public CBMecanumDriveController addDriveModule(CBDriveModule driveModule) {
		driveModules.add(driveModule);
		updateDriveMode(driveModule);
		CB2DVector pos = driveModule.getPosition();
		double ma = pos.getMag() * Math.cos(Math.atan2(Math.abs(pos.getX()), Math.abs(pos.getY())) - qtrPi);
		momentArms.add(ma);
		vTrans.add(0.0);
		vCorr.add(0.0);
		vTheta.add(0.0);
		vTotal.add(0.0);
		dmCount++;
		if(ma<minMA) minMA=ma;
		return this;
	}

	@Override
	public CBMecanumDriveController setControlPeriod(double controlPeriod) {
		return (CBMecanumDriveController)super.setControlPeriod(controlPeriod);
	}
}
