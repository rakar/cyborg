package org.montclairrobotics.cyborg.devices;

public class CBAxisList extends CBDualBus<CBAxisList, CBStickList> {

	public CBAxisList(CBStickList list) {
		super(0, 100, list);
	}
}
