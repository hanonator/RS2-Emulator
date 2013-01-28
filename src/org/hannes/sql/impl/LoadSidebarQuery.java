package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hannes.entity.Player;
import org.hannes.event.Event;
import org.hannes.rs2.util.Sidebar;
import org.hannes.rs2.util.SidebarInterface;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

public class LoadSidebarQuery implements Query<Event> {

	/**
	 * The player to load the sidebars for
	 */
	private final Player player;

	public LoadSidebarQuery(Player player) {
		this.player = player;
	}

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		PreparedStatement statement = handler.getQuery("load-sidebar");
		statement.setInt(1, player.getUid());
		if (!statement.execute()) {
			return null;
		}
		
		Sidebar sidebar = player.getSidebar();
		ResultSet results = statement.getResultSet();
		if (results.next()) {
			for (int i = 0; i < player.getSidebar().size(); i++) {
				sidebar.set(i, new SidebarInterface(i, results.getInt(i + 3)));
			}
		}
		
		player.getConnection().write(player.getSidebar().get());
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}

}