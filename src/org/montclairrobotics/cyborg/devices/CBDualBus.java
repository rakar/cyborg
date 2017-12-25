package org.montclairrobotics.cyborg.devices;

//public class CBMatrixObjectMap {
import java.util.HashMap;

public class CBDualBus<T> {
    private HashMap<Integer, CBDualID<T>> ports = new HashMap<Integer,CBDualID<T>>();
    private int max;
    private CBBus bus;

    public CBDualBus(int max, CBBus bus) {
        this.max = max;
        this.bus = bus;
    }
    @SuppressWarnings("unchecked")
    public T add(CBDeviceEnum device, CBDeviceEnum row, int col) {
    	//
    	// TODO: this check is messed up, it needs to check the value not the ordinal
    	// 
    	//int key = bus.get(device).get()*max+col;
        if (ports.containsKey(device.ordinal())) {
            //throw Invalid Bus Config Error
        }
        else {
            ports.put(device.ordinal(), new CBDualID<T>(bus.get(device).get(), col));
        }
        return (T)this;
    }

    public CBDualID<T> get(CBDeviceEnum device){
        return (CBDualID<T>)ports.get(device.ordinal());
    }
}