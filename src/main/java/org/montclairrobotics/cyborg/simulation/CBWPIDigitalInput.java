package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.DigitalInput;

public class CBWPIDigitalInput extends DigitalInput implements CBIDigitalInput {
    /**
     * Create an instance of a Digital Input class. Creates a digital input given a channel.
     *
     * @param channel the DIO channel for the digital input 0-9 are on-board, 10-25 are on the MXP
     */
    public CBWPIDigitalInput(int channel) {
        super(channel);
    }
}
