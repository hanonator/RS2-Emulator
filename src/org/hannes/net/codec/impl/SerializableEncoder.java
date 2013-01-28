package org.hannes.net.codec.impl;

import org.hannes.net.codec.Encoder;
import org.hannes.util.Serializable;

/**
 * Support for Serializable (hanobi 1.0)
 * 
 * @author red
 */
public class SerializableEncoder implements Encoder {

	@Override
	public Object encode(Object object) {
		if (object instanceof Serializable) {
			return ((Serializable) object).serialize();
		}
		return null;
	}

}