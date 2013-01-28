package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.event.LoginEvent;
import org.hannes.event.LoginEvent.ReturnCode;
import org.hannes.locale.Realm;
import org.hannes.rs2.util.LoginConfiguration;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

public class LoginQuery implements Query<LoginEvent> {

	/**
	 * 
	 */
	private final LoginConfiguration configuration;

	public LoginQuery(LoginConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public LoginEvent execute(Connection connection, SQLHandler handler) throws SQLException {
		LoginEvent event = new LoginEvent(configuration.getConnecton(), configuration);

		/*
		 * Attempt to create a player object
		 */
		try {
			Realm realm = Main.getRealm();
			Player player = realm.allocatePlayer(configuration.getConnecton());
			configuration.getConnecton().setPlayer(player);
		} catch (NullPointerException ex) {
			configuration.setReturnCode(ReturnCode.WORLD_FULL);
			return event;
		}

		/*
		 * Execute the statement
		 */
		PreparedStatement statement = connection.prepareStatement("SELECT id, username, password FROM characters WHERE username=? AND password=?;");
		statement.setString(1, configuration.getUsername());
		statement.setString(2, configuration.getPassword());
		statement.execute();
		
		/*
		 * If results are returned, the player details are correct n stuf
		 */
		ResultSet results = statement.getResultSet();

		if (Main.getRealm().find(configuration.getUsername())) {
			configuration.setReturnCode(ReturnCode.ALREADY_LOGGED_IN);
		} else if (results.next()) {
			event.getConfiguration().getConnecton().getPlayer().setUid(results.getInt("id"));
			event.getConfiguration().setReturnCode(ReturnCode.SUCCESS);
		} else {
			event.getConfiguration().setReturnCode(ReturnCode.INVALID_DETAILS);	
		}
		return event;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}

}