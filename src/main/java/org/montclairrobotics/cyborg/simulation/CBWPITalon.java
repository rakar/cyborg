package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.Talon;

public class CBWPITalon extends Talon implements CBITalon{
    /**
     * Constructor for a Talon (original or Talon SR).
     *
     * <p>Note that the Talon uses the following bounds for PWM values. These values should work
     * reasonably well for most controllers, but if users experience issues such as asymmetric
     * behavior around the deadband or inability to saturate the controller in either direction,
     * calibration is recommended. The calibration procedure can be found in the Talon User Manual
     * available from CTRE.
     *
     * <p>- 2.037ms = full "forward" - 1.539ms = the "high end" of the deadband range - 1.513ms =
     * center of the deadband range (off) - 1.487ms = the "low end" of the deadband range - .989ms
     * = full "reverse"
     *
     * @param channel The PWM channel that the Talon is attached to. 0-9 are on-board, 10-19 are on
     *                the MXP port
     */
    public CBWPITalon(int channel) {
        super(channel);
    }
}
