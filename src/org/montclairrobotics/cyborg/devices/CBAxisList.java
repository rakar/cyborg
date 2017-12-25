package org.montclairrobotics.cyborg.devices;

public class CBAxisList extends CBDualBus<CBAxisList> {

	public CBAxisList(CBStickList list) {
		super(100, list);
	}
}
