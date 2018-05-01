package org.montclairrobotics.cyborg.devices;

public class CBButtonList extends CBDualBus<CBButtonList,CBStickList> {

	public CBButtonList(CBStickList list) {
		super(0, 100, list);
	}
}

