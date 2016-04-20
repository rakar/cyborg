package org.montclairrobotics.cyborg.utils;

import com.kauailabs.navx.frc.AHRS;

public class NavX { 
	    
    // AHRS reference from wherever NavXGyro is initialized 
    private AHRS ahrs;
    
    public NavX(AHRS ahrs) {
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
}