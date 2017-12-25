package org.montclairrobotics.cyborg.devices;

import java.util.HashMap;

public class CBBus<T> {
    private HashMap<Integer, CBPortID<T>> ports = new HashMap<Integer,CBPortID<T>>();
    private int min, max;

    public CBBus(int min, int max) {
        this.min=min;
        this.max=max;
    }
    @SuppressWarnings("unchecked")
    public T add(CBDeviceEnum device, int port ) {
    	//
    	// TODO: this check is messed up, it needs to check the value not the ordinal
    	// 
        if (port<min || port>max || ports.containsKey(device.ordinal())) {
            //throw Invalid Bus Config Error
        }
        else {
            ports.put(device.ordinal(),new CBPortID<T>(port));
        }
        return (T)this;
    }

    public CBPortID<T> get(CBDeviceEnum device){
        return (CBPortID<T>)ports.get(device.ordinal());
    }
}

