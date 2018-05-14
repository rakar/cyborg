package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.DigitalOutput;

public class CBWPIDigitalOutput extends DigitalOutput implements CBIDigitalOutput {
    /**
     * Create an instance of a digital output. Create an instance of a digital output given a
     * channel.
     *
     * @param channel the DIO channel to use for the digital output. 0-9 are on-board, 10-25 are on
     *                the MXP
     */
    public CBWPIDigitalOutput(int channel) {
        super(channel);
    }
}
