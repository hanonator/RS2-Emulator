package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hannes.entity.Player;
import org.hannes.event.Event;
import org.hannes.rs2.container.Item;
import org.hannes.rs2.container.impl.Equipment;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

public class EquipmentLoadQuery implements Query<Event> {

	/**
	 * The player
	 */
	private final Player player;

	public EquipmentLoadQuery(Player player) {
		this.player = player;
	}

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		PreparedStatement statement = handler.getQuery("equipment-load");
		statement.setInt(1, player.getUid());
		statement.execute();
		
		Equipment equipment = player.getEquipment();
		ResultSet results = statement.getResultSet();
		
		equipment.setFireUpdate(false);
		while (results.next()) {
			int slot = results.getInt("slot");
			int itemId = results.getInt("item_id");
			int amount = results.getInt("amount");
			
			if (itemId != -1) {
				equipment.load(slot, new Item(itemId, amount));
			}
		}
		equipment.setFireUpdate(true);
		equipment.refresh();
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}

}