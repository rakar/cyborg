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
    	boolean err = port<min || port>max;
        if (!err) {
        	for(CBPortID<T> p:ports.values()) {
    			if (p.get()==port) {
    				err=true;
    				break;
    			}
        	}
        }
       	if (!err && ports.containsKey(device.ordinal())) {
       		err=true;
       	}
        if(err) {
        	// throw Invalid Bus Configuration
        	// throw new Exception("Invalid Bus Configuration");
        } else {
            ports.put(device.ordinal(),new CBPortID<T>(port));
        }
        return (T)this;
    }

    public CBPortID<T> get(CBDeviceEnum device){
        return (CBPortID<T>)ports.get(device.ordinal());
    }
}

