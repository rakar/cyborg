package org.montclairrobotics.cyborg;

import org.montclairrobotics.cyborg.simulation.CBSimDigitalInput;
import org.montclairrobotics.cyborg.simulation.CBSimJoystick;

public class CBSimLink {
    //public boolean[][] Buttons = new boolean[6][16];
    //public double[][] Axis = new double[6][16];
    public CBSimDigitalInput.CBSimDigitalInputData[] DIs = new CBSimDigitalInput.CBSimDigitalInputData[26];
    public CBSimJoystick.CBSimJoystickData[] joysticks = new CBSimJoystick.CBSimJoystickData[6];
}
