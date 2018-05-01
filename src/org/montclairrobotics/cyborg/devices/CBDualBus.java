package org.montclairrobotics.cyborg.devices;

//public class CBMatrixObjectMap {
import java.util.HashMap;

public class CBDualBus<T,R> {
    private HashMap<Integer, CBDualID<T>> ports = new HashMap<Integer,CBDualID<T>>();
    private int min;
    private int max;
	private CBBus<R> bus;

    public CBDualBus(int min, int max, CBBus<R> bus) {
    	this.min = min;
        this.max = max;
        this.bus = bus;
    }
    
    @SuppressWarnings("unchecked")
    public T add(CBDeviceEnum device, CBDeviceEnum row, int col) {
    	
    	int rowPort = bus.get(row).get();
    	boolean err = col<min || col>max;
        if (!err) {
        	for(CBDualID<T> d:ports.values()) {
    			if (d.getA()==rowPort && d.getB()==col) {
    				err=true;
    				break;
    			}
        	}
        }
       	if (!err && ports.containsKey(device.ordinal())) {
       		err=true;
       	}
        if(err) {
        	//throw new Exception("Invalid Bus Configuration");
        } else {
            ports.put(device.ordinal(), new CBDualID<T>(rowPort, col));
        }
        return (T)this;
    }

    public CBDualID<T> get(CBDeviceEnum device){
        return (CBDualID<T>)ports.get(device.ordinal());
    }
}