package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hannes.entity.Player;
import org.hannes.event.Event;
import org.hannes.rs2.util.TextMessage;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

public class SavePlayerQuery implements Query<Event> {

	/**
	 * 
	 */
	private final Player player;

	public SavePlayerQuery(Player player) {
		this.player = player;
	}

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		PreparedStatement statement = handler.getQuery("save-settings");
		
		/*
		 * Add the player's status
		 */
		statement.setInt(1, player.getStatus().getPublicChat());
		statement.setInt(2, player.getStatus().getPrivateChat());
		statement.setInt(3, player.getStatus().getTrade());

		/*
		 * Add the player's settings
		 */
		statement.setInt(4, player.getSettings().getBrightness());
		statement.setInt(5, player.getSettings().getChatEffects());
		statement.setInt(6, player.getSettings().getMouseButtons());
		statement.setInt(7, player.getSettings().getSplitChat());
		statement.setInt(8, player.getSettings().getMusicVolume());
		statement.setInt(9, player.getSettings().getEffectVolume());
		
		/*
		 * Set the player's UID
		 */
		statement.setInt(10, player.getUid());
		statement.execute();
		
		player.getConnection().write(new TextMessage(player.getStatus().getPublicChat() + ", " + player.getStatus().getPrivateChat() + "," + player.getStatus().getTrade()));
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}

}