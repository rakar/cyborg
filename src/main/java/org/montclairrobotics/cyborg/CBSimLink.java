package org.montclairrobotics.cyborg;

import org.montclairrobotics.cyborg.simulation.*;

import java.util.ArrayList;

public class CBSimLink {
    //public boolean[][] Buttons = new boolean[6][16];
    //public double[][] Axis = new double[6][16];
    public ArrayList<CBSimDigitalInput.CBSimDigitalInputData> digitalInputs = new ArrayList();
    public ArrayList<CBSimDigitalOutput.CBSimDigitalOutputData> digitalOutputs = new ArrayList();
    public ArrayList<CBSimJoystick.CBSimJoystickData> joysticks = new ArrayList();
    public ArrayList<CBSimPDB.CBSimPDBData> pdbs = new ArrayList();
    public ArrayList<CBSimTalon.CBSimTalonData> pwmSpeedControllers = new ArrayList();
    public ArrayList<CBSimEncoder.CBSimEncoderData> encoders = new ArrayList<>();


    public CBSimLink() {
        throw new RuntimeException("CBSimLink is a placeholder and is not yet implemented.");
    }

}
