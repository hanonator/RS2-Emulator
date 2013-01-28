package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hannes.event.Event;
import org.hannes.rs2.container.Item;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

public class InsertItemQuery implements Query<Event> {

	/**
	 * 
	 */
	private final Item item;

	public InsertItemQuery(Item item) {
		this.item = item;
	}

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		PreparedStatement statement = handler.getQuery("insert-item");
		statement.setInt(1, item.getId());
		statement.setInt(3, item.getId());
		statement.setInt(2, item.getAmount());
		statement.setInt(4, item.getAmount());
		statement.execute();
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		
	}

}
