package org.montclairrobotics.cyborg.devices;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalSource;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBSource;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;


public abstract class CBEncoderBase implements CBSource, CBDevice {

    private CBIEncoder encoder;
    protected String name;
    protected String subsystem;

    public CBEncoderBase(int aChannel, int bChannel, CounterBase.EncodingType encodingType, boolean reversed, double distancePerPulse) {
            encoder = new CBWPIEncoder(aChannel, bChannel, false, encodingType);
    }

    public CBEncoderBase(DigitalSource aSource, DigitalSource bSource, CounterBase.EncodingType encodingType, boolean reversed, double distancePerPulse) {
            encoder = new CBWPIEncoder(aSource, bSource, false, encodingType);
    }

    public CBEncoderBase(CBDeviceID talonSrx, FeedbackDevice encoderType, boolean reversed, double distancePerPulse) {
            encoder = new CBSrxEncoder(Cyborg.hardwareAdapter.getTalonSRX(talonSrx), encoderType, false, distancePerPulse);
    }

    @Deprecated
    public CBEncoderBase(String name, boolean reversed, double distancePerPulse) {
        throw new RuntimeException("Encoder Name constructor not implemented in FRC");
    }

    protected void baseReset() {
        encoder.reset();
    }

    protected int baseGetRaw() {
        return encoder.getRaw();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        //TODO: implement CBEncoder initSendable
    }


}
