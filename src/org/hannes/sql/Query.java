package org.hannes.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.hannes.event.Event;

/**
 * Represents a query
 * 
 * @author goku
 *
 * @param <T>
 */
public interface Query<T extends Event> {

	/**
	 * 
	 * 
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public abstract T execute(Connection connection, SQLHandler handler) throws SQLException;
	
	/**
	 * Called when an exception occured during the execution of the query
	 * 
	 * @param t
	 */
	public abstract void exceptionCaught(Throwable t);

}