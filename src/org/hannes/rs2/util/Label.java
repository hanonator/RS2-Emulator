package org.hannes.rs2.util;

import org.hannes.net.MessageLength;
import org.hannes.net.codec.MessageType;

@MessageType(MessageLength.VARIABLE_16_BIT)
public class Label {

	/**
	 * The index
	 */
	private final int index;

	/**
	 * The text
	 */
	private final String text;

	/**
	 * @param index
	 * @param text
	 */
	public Label(int index, String text) {
		this.index = index;
		this.text = text;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

}