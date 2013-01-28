package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hannes.entity.Player;
import org.hannes.event.Event;
import org.hannes.rs2.container.Item;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

/**
 * The equipment update queries
 * 
 * @author red
 */
public class EquipmentUpdateQuery implements Query<Event> {

	/**
	 * The player
	 */
	private final Player player;
	
	/**
	 * The item that is equiped
	 */
	private final Item item;
	
	/**
	 * The slot
	 */
	private final int slot;

	public EquipmentUpdateQuery(Player player, Item item, int slot) {
		this.player = player;
		this.item = item;
		this.slot = slot;
	}

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		if (item != null) {
			PreparedStatement statement = handler.getQuery("equipment-insert");
			statement.setInt(1, item.getId());
			statement.setInt(2, item.getAmount());
			statement.setInt(3, player.getUid());
			statement.setInt(4, slot);
			statement.setInt(5, player.getUid());
			statement.setInt(6, slot);
			statement.execute();
			
			statement = handler.getQuery("equipment-update");
			statement.setInt(1, item.getId());
			statement.setInt(2, item.getAmount());
			statement.setInt(3, player.getUid());
			statement.setInt(4, slot);
			statement.execute();
		} else {
			PreparedStatement statement = handler.getQuery("equipment-delete");
			statement.setInt(1, slot);
			statement.setInt(2, player.getUid());
			statement.execute();
		}
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}
}