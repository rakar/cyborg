package org.montclairrobotics.cyborg.devices;

import com.revrobotics.CANEncoder;

public class CBCANSparkMaxEncoder implements CBIEncoder {
    CBCANSparkMax controller;
    CANEncoder ce;
    double resetBase;



    public CBCANSparkMaxEncoder(CBCANSparkMax controller) {
        this.controller = controller;
        ce = controller.getEncoder();
    }

    @Override
    public double getRaw() {
        return (ce.getPosition()-resetBase);
    }

    @Override
    public void reset() {
        resetBase = ce.getPosition();
    }
}
