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
public class BankUpdateQuery implements Query<Event> {

	/**
	 * The player
	 */
	private final Player player;
	
	/**
	 * The item that is equiped
	 */
	private final Item[] items;
	
	/**
	 * The slot
	 */
	private final int[] slot;

	public BankUpdateQuery(Player player, Item[] items, int[] slot) {
		this.player = player;
		this.items = items;
		this.slot = slot;
	}

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		PreparedStatement stmtAdd = handler.getQuery("bank-insert");
		PreparedStatement stmtDelete = handler.getQuery("bank-delete");
		
		int length = slot == null ? items.length : slot.length;
		for (int i = 0; i < length; i++) {
			int index = slot == null ? i : slot[i];
			
			if (items[index] != null) {
				stmtAdd.setInt(1, items[index].getId());
				stmtAdd.setInt(2, items[index].getAmount());
				stmtAdd.setInt(3, player.getUid());
				stmtAdd.setInt(4, index);
				stmtAdd.setInt(5, player.getUid());
				stmtAdd.setInt(6, index);
				stmtAdd.addBatch();
			} else {
				stmtDelete.setInt(1, index);
				stmtDelete.setInt(2, player.getUid());
				stmtDelete.addBatch();
			}
		}
		stmtAdd.executeBatch();
		stmtDelete.executeBatch();
		
		PreparedStatement stmtUpdate = handler.getQuery("bank-update");
		for (int i = 0; i < length; i++) {
			int index = slot == null ? i : slot[i];
			
			if (items[index] != null) {
				stmtUpdate.setInt(1, items[index].getId());
				stmtUpdate.setInt(2, items[index].getAmount());
				stmtUpdate.setInt(3, player.getUid());
				stmtUpdate.setInt(4, index);
				stmtUpdate.addBatch();
			}
		}
		stmtUpdate.executeBatch();
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}
}