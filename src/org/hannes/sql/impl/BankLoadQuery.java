package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hannes.entity.Player;
import org.hannes.event.Event;
import org.hannes.rs2.container.Item;
import org.hannes.rs2.container.impl.Bank;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

public class BankLoadQuery implements Query<Event> {

	/**
	 * The player
	 */
	private final Player player;

	public BankLoadQuery(Player player) {
		this.player = player;
	}

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		PreparedStatement statement = handler.getQuery("bank-load");
		statement.setInt(1, player.getUid());
		statement.execute();
		
		Bank bank = player.getBank();
		ResultSet results = statement.getResultSet();
		
		bank.setFireUpdate(false);
		while (results.next()) {
			int slot = results.getInt("slot");
			int itemId = results.getInt("item_id");
			int amount = results.getInt("amount");
			
			if (itemId != -1) {
				bank.load(slot, new Item(itemId, amount));
			}
		}
		bank.setFireUpdate(true);
		bank.refresh();
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}

}