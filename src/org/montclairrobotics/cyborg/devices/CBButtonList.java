package org.montclairrobotics.cyborg.devices;

public class CBButtonList extends CBDualBus<CBButtonList> {

	public CBButtonList(CBStickList list) {
		super(100, list);
	}
}

