package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.Joystick;

public class CBWPIJoystick extends Joystick implements CBIJoystick {

    /**
     * Construct an instance of a joystick. The joystick index is the USB port on the drivers
     * station.
     *
     * @param port The port on the Driver Station that the joystick is plugged into.
     */
    public CBWPIJoystick(int port) {
        super(port);
    }
}
