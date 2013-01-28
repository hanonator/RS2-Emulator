package org.hannes.rs2.util;

import org.hannes.net.MessageLength;
import org.hannes.net.codec.MessageType;

@MessageType(MessageLength.VARIABLE_8_BIT)
public class TextMessage {

	private final String text;

	public TextMessage(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}