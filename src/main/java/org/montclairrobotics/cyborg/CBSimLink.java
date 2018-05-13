package org.montclairrobotics.cyborg;

import org.montclairrobotics.cyborg.simulation.CBSimDigitalInput;

public class CBSimLink {
    public boolean[][] Buttons = new boolean[6][16];
    public double[][] Axis = new double[6][16];
    // TODO: Set correct DigitalInput Limits considering NavX expansion port
    public CBSimDigitalInput.CBSimDigitalInputData[] DIs = new CBSimDigitalInput.CBSimDigitalInputData[25];
}
