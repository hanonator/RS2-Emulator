package org.hannes.event;

import org.hannes.net.Connection;

public class ButtonEvent extends Event {

	/**
	 * The button id
	 */
	private int index;

	public ButtonEvent(Connection connection) {
		super(connection);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}