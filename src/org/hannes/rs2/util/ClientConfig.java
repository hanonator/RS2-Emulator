package org.hannes.rs2.util;

import org.hannes.util.Serializable;

public class ClientConfig implements Serializable {

	/**
	 * The value of the configuration setting
	 */
	private final int key;
	
	/**
	 * The value of the configuration setting
	 */
	private final int value;

	public ClientConfig(int key, int value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public Object serialize() {
		return value > Byte.MAX_VALUE || value < Byte.MIN_VALUE ? 
				new Large(key, value) : new Small(key, value);
	}

}