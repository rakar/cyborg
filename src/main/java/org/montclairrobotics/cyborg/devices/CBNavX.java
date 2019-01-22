package org.montclairrobotics.cyborg.devices;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class CBNavX implements CBDevice {
    String name, subsystem;

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

    public float getCompassHeading() {
        return ahrs.getCompassHeading();
    }

    public boolean isCalibrating() {
        return ahrs.isCalibrating();
    }

    public boolean isConnected() {
        return ahrs.isConnected();
    }

    public double getByteCount() {
        return ahrs.getByteCount();
    }

    public int getActualUpdateRate() {
        return ahrs.getActualUpdateRate();
    }

    public int getRequestedUpdateRate() {
        return ahrs.getRequestedUpdateRate();
    }
    public double getUpdateCount() {
        return ahrs.getUpdateCount();
    }
    public long getLastSensorTimestamp() {
        return ahrs.getLastSensorTimestamp();
    }

    public float getWorldLinearAccelX()
    {
        return ahrs.getWorldLinearAccelX();
    }
    public float getWorldLinearAccelY()
    {
        return ahrs.getWorldLinearAccelY();
    }
    public float getWorldLinearAccelZ()
    {
        return ahrs.getWorldLinearAccelZ();
    }

    public boolean isMoving()
    {
        return ahrs.isMoving();
    }
    public boolean isRotating()
    {
        return ahrs.isRotating();
    }

    public float getBarometricPressure()
    {
        return ahrs.getBarometricPressure();
    }
    public float getAltitude()
    {
        return ahrs.getAltitude();
    }
    public boolean isAltitudeValid() { return ahrs.isAltitudeValid(); }

    public float getFusedHeading()
    {
        return ahrs.getFusedHeading();
    }

    public boolean isMagneticDisturbance()
    {
        return ahrs.isMagneticDisturbance();
    }
    public boolean isMagnetometerCalibrated()
    {
        return ahrs.isMagnetometerCalibrated();
    }

    /* Unit Quaternions */
    public float getQuaternionW() {
        return ahrs.getQuaternionW();
    }
    public float getQuaternionX() {
        return ahrs.getQuaternionX();
    }
    public float getQuaternionY() {
        return ahrs.getQuaternionY();
    }
    public float getQuaternionZ() {
        return ahrs.getQuaternionZ();
    }

    public float getVelocityX() { return ahrs.getVelocityX(); }
    public float getVelocityY() { return ahrs.getVelocityY(); }
    public float getVelocityZ() {
        return ahrs.getVelocityZ();
    }

    public CBNavX resetDisplacement() { ahrs.resetDisplacement(); return this; }
    public float getDisplacementX() { return ahrs.getDisplacementX(); }
    public float getDisplacementY() { return ahrs.getDisplacementY(); }
    public float getDisplacementZ() {
        return ahrs.getDisplacementZ();
    }


    public PIDSourceType getPIDSourceType() {
        return ahrs.getPIDSourceType();
    }
    public void setPIDSourceType(PIDSourceType type) { ahrs.setPIDSourceType(type); }
    public double pidGet() { return ahrs.pidGet(); }


    public double getAngle() {
        return ahrs.getAngle();
    }
    public double getRate() {
        return ahrs.getRate();
    }
    public void setAngleAdjustment(double adjustment) {
        ahrs.setAngleAdjustment(adjustment);
    }
    public double getAngleAdjustment() {
        return ahrs.getAngleAdjustment();
    }
    public void reset() {
        ahrs.reset();
    }

    public float getRawGyroX() {
        return ahrs.getRawGyroX();
    }
    public float getRawGyroY() {
        return ahrs.getRawGyroY();
    }
    public float getRawGyroZ() {
        return ahrs.getRawGyroZ();
    }

    public float getRawAccelX() {
        return ahrs.getRawAccelX();
    }
    public float getRawAccelY() {
        return ahrs.getRawAccelY();
    }
    public float getRawAccelZ() {
        return ahrs.getRawAccelZ();
    }

    public float getRawMagX() {
        return ahrs.getRawMagX();
    }
    public float getRawMagY() {
        return ahrs.getRawMagY();
    }
    public float getRawMagZ() {
        return ahrs.getRawMagZ();
    }

    public float getTempC()
    {
        return ahrs.getTempC();
    }

    public AHRS.BoardYawAxis getBoardYawAxis() { return ahrs.getBoardYawAxis(); }

    public String getFirmwareVersion() { return ahrs.getFirmwareVersion(); }
    public CBNavX enableLogging(boolean enable) { ahrs.enableLogging(enable); return this; }

    public short getGyroFullScaleRangeDPS() { return ahrs.getGyroFullScaleRangeDPS(); }
    public short getAccelFullScaleRangeG() { return ahrs.getAccelFullScaleRangeG(); }

    public CBNavX zeroYaw() {
        ahrs.zeroYaw();
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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

    public CBNavX setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBNavX setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }

    @Override
    public CBDeviceControl getDeviceControl() {
        return deviceControl;
    }

    CBDeviceControl deviceControl = new CBDeviceControl() {
        @Override
        public void init() {

        }

        @Override
        public void senseUpdate() {

        }

        @Override
        public void controlUpdate() {

        }
    };
}