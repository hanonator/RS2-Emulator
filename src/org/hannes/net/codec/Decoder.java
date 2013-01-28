package org.hannes.net.codec;

import org.hannes.event.Event;
import org.hannes.net.Connection;
import org.hannes.net.Message;

/**
 * Decodes messages n shit
 * 
 * @author red
 *
 */
public interface Decoder {

	/**
	 * 
	 * 
	 * @param message
	 * @param connection
	 * @throws Exception
	 */
	public abstract Event decode(Message message, Connection connection) throws Exception;

}