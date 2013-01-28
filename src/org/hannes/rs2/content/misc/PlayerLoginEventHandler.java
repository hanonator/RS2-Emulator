package org.hannes.rs2.content.misc;

import java.util.logging.Logger;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.event.LoginEvent;
import org.hannes.event.LoginEvent.ReturnCode;
import org.hannes.event.hub.EventHandler;
import org.hannes.locale.Realm;
import org.hannes.net.Connection;
import org.hannes.net.MessageBuilder;
import org.hannes.rs2.util.LoginConfiguration;
import org.hannes.util.ConnectionState;

/**
 * The EventHandler that sends the login response and registers the player
 * to this realm
 * 
 * @author red
 *
 */
public class PlayerLoginEventHandler implements EventHandler<LoginEvent> {

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(PlayerLoginEventHandler.class.getName());

	@Override
	public void handleEvent(LoginEvent event, Player player) throws Exception {
		LoginConfiguration configuration = event.getConfiguration();
		
		switch (configuration.getReturnCode()) {
		case SUCCESS:
			/*
			 * Get the world
			 */
			Realm realm = Main.getRealm();
			
			/*
			 * Set the player's client type
			 */
			player.setClientType(configuration.getMemoryUsage());
			
			/*
			 * Set the player's username
			 */
			player.setUsername(configuration.getUsername());
			
			/*
			 * Send the response
			 */
			sendResponse(event.getConnection(), configuration.getReturnCode());
			
			/*
			 * Flush the connection for instant coolness
			 */
			event.getConnection().flush();
			
			/*
			 * Register the player
			 */
			realm.register(player);
			
			/*
			 * Make the ConnectionState active
			 */
			event.getConnection().setState(ConnectionState.ACTIVE);
			break;
		
		default:
			/*
			 * Just send the response
			 */
			sendResponse(event.getConnection(), configuration.getReturnCode());
			break;
		}
		
		/*
		 * Debug information
		 */
		logger.info("Login for " + configuration.getUsername() + " returned " + configuration.getReturnCode());
	}

	/**
	 * 
	 * @param connection
	 * @param code
	 */
	private void sendResponse(Connection connection, ReturnCode code) {
		MessageBuilder builder = new MessageBuilder();
		builder.put((byte) code.ordinal());
		builder.put((byte) 0);
		builder.put((byte) 0);
		connection.write(builder.build());
	}

}