package org.hannes.net.codec;


/**
 * Encodes a message so it can be sent to the user
 * 
 * @author red
 *
 */
public interface Encoder {

	/**
	 * 
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public Object encode(Object object) throws Exception;

}