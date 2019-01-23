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
    public int getRaw() {
        return (int) (ce.getPosition()-resetBase)*100;
    }

    @Override
    public void reset() {
        resetBase = ce.getPosition();
    }
}
