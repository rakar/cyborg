package org.montclairrobotics.cyborg.devices;

import com.revrobotics.CANEncoder;

public class CBCANSparkMaxEncoder implements CBIEncoder {
    CBCANSparkMax controller;
    CANEncoder ce;



    public CBCANSparkMaxEncoder(CBCANSparkMax controller) {
        this.controller = controller;
        ce = controller.getEncoder();
    }

    @Override
    public int getRaw() {
        return (int) ce.getPosition()*100;
    }

    @Override
    public void reset() {
        ce = controller.getEncoder();
    }
}
