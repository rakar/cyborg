package org.montclairrobotics.cyborg.devices;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;

public class CBNavX implements CBDevice { 
	    
    // AHRS reference from wherever NavXGyro is initialized 
    private AHRS ahrs;
    
    public CBNavX(Port spi_port_id) {
    	ahrs = new AHRS(spi_port_id);
    }
    
    public CBNavX(AHRS ahrs) {
        this.ahrs = ahrs;
        ahrs.reset();// Resets gyro readings to 0 (starting orientation)
    }
    
    // Gyroscopic readings along x-axis, y-axis, z-axis
    
    public float getPitch() {
        return ahrs.getPitch();
    }
    
    public float getRoll() {
        return ahrs.getRoll(); // Ignore documentation; there is a typo
    }

    public float getYaw() {
        return ahrs.getYaw();
    }
    
    public void zeroYaw()
    {
        ahrs.zeroYaw();
    }

	@Override
	public void senseUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlUpdate() {
		// TODO Auto-generated method stub
		
	}

    @Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
    @Override
    public void configureSim() {

    }

    @Override
    public void senseUpdateSim() {

    }

    @Override
    public void controlUpdateSim() {

    }

}