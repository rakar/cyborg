package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class CBBuilder {
    private CBCANBus canBus;
    private CBPDBSlots pdbSlots;
    private CBDIOBus dioBus;
    private CBStickList stickList;
    private CBAxisList axisList;
    private CBButtonList buttonList;
    
    public CBBuilder setCANBus(CBCANBus canBus) {
        this.canBus = canBus;
        return this;
    }  
    public CBBuilder setPDBSlots(CBPDBSlots pdbSlots) {
        this.pdbSlots = pdbSlots;
        return this;
    }  
    public CBBuilder setDIOBus(CBDIOBus dioBus) {
        this.dioBus = dioBus;
        return this;
    }  
    public CBBuilder setStickList(CBStickList stickList) {
        this.stickList = stickList;
        return this;
    }  
    public CBBuilder setAxisList(CBAxisList axisList) {
        this.axisList = axisList;
        return this;
    }  
    public CBBuilder setButtonList(CBButtonList buttonList) {
        this.buttonList = buttonList;
        return this;
    }  
    
    
    public CBCANTalon CBCANTalon(CBDeviceEnum device) {
        return CBCANTalon.build(canBus.get(device));
    }
    
    public CBEncoder CBEncoder(CBDeviceEnum aChannel, CBDeviceEnum bChannel,
    		EncodingType encodingType, boolean reversed, double distancePerPulse) {
    	return new CBEncoder(dioBus.get(aChannel).get(), dioBus.get(bChannel).get(), encodingType, reversed, distancePerPulse);
    }
    
    public CBDigitalInput CBDigitalInput(CBDeviceEnum channel) {
    	return new CBDigitalInput(dioBus.get(channel).get());
    }
    
    public CBAxis CBAxis(CBDeviceEnum axis) {
    	CBDualID<CBAxisList> ids = axisList.get(axis);
    	return new CBAxis(ids.getA(), ids.getB());
    }
    
    public CBButton CBButton(CBDeviceEnum button) {
    	CBDualID<CBButtonList> ids = buttonList.get(button);
    	return new CBButton(ids.getA(), ids.getB());
    }
    
}
