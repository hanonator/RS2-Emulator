package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.event.Event;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

public class UpdateSettingsQuery implements Query<Event> {

	/**
	 * The logger;
	 */
	private static final Logger logger = Logger.getLogger(UpdateSettingsQuery.class.getName());

	/**
	 * 
	 */
	private final String column;
	
	/**
	 * 
	 */
	private final Player player;
	
	/**
	 * 
	 */
	private final int value;

	public UpdateSettingsQuery(String column, Player player, int value) {
		this.column = column;
		this.player = player;
		this.value = value;
	}

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		PreparedStatement statement = handler.prepare("UPDATE character_settings SET " + column + "=? WHERE character_id=?;", connection);
		statement.setInt(1, value);
		statement.setInt(2, player.getUid());
		statement.execute();
		
		/*
		 * Debug
		 */
		if (Main.getConfiguration().contains("debug-mode") && 
				Main.getConfiguration().get("debug-mode").equals("true")) {
			logger.info(column + ": " + value);
		}
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}

}