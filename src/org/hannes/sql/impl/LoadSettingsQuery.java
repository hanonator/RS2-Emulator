package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.event.Event;
import org.hannes.rs2.util.Status;
import org.hannes.rs2.util.Settings;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

/**
 * 
 * 
 * @author red
 */
public class LoadSettingsQuery implements Query<Event> {

	private static final Logger logger = Logger.getLogger(LoadSettingsQuery.class.getName());

	/**
	 * The player to load the settings for
	 */
	private final Player player;

	public LoadSettingsQuery(Player player) {
		this.player = player;
	}

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		Status status = player.getStatus();
		Settings settings = player.getSettings();
		
		PreparedStatement statement = handler.getQuery("load-settings");
		statement.setInt(1, player.getUid());
		statement.execute();
		
		ResultSet resultset = statement.getResultSet();
		if (resultset.next()) {
			status.setPublicChat(resultset.getInt("public_chat"));
			status.setPrivateChat(resultset.getInt("private_chat"));
			status.setTrade(resultset.getInt("trade"));
			
			settings.setBrightness(resultset.getInt("brightness"));
			settings.setChatEffects(resultset.getInt("chat_effects"));
			settings.setMouseButtons(resultset.getInt("mouse_buttons"));
			settings.setMusicVolume(resultset.getInt("music_volume"));
			settings.setSoundVolume(resultset.getInt("effect_volume"));
			settings.setSplitChat(resultset.getInt("split_chat"));
		}

		/*
		 * Debug if necessary
		 */
		if (Main.getConfiguration().contains("debug-mode") && 
				Main.getConfiguration().get("debug-mode").equals("true")) {
			logger.info("status: pub=:" + status.getPublicChat() + ";prv=" + status.getPrivateChat() + ";trade=" + status.getTrade());
			logger.info("settings: brt=:" + settings.getBrightness() + ";mbt=" + settings.getMouseButtons() + ";che=" + settings.getChatEffects() + ";spc=" + settings.getSplitChat());
			logger.info("volume: msc=" + settings.getMusicVolume() + ";efx=" + settings.getEffectVolume());
		}
		
		player.getConnection().write(status);
		player.getConnection().write(settings);
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}

}