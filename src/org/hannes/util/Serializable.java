package org.hannes.util;



/**
 * The interface to be extended by classes that are sent
 * to the client in the form of a message.
 * 
 * @author goku
 */
public interface Serializable {

	/**
	 * Serializes the class into a message
	 * 
	 * @return
	 */
	public abstract Object serialize();

}