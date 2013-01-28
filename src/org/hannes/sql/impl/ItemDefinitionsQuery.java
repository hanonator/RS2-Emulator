package org.hannes.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.hannes.event.Event;
import org.hannes.rs2.util.ItemDefinition;
import org.hannes.sql.Query;
import org.hannes.sql.SQLHandler;

public class ItemDefinitionsQuery implements Query<Event> {

	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(ItemDefinitionsQuery.class.getName());

	@Override
	public Event execute(Connection connection, SQLHandler handler) throws SQLException {
		PreparedStatement countStatement = connection.prepareStatement("SELECT COUNT(*) FROM item_definitions;");
		countStatement.execute();
		
		ResultSet countset = countStatement.getResultSet();
		countset.next();
		
		ItemDefinition[] definitions = new ItemDefinition[countset.getInt(1)];
		
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM item_definitions;");
		if (statement.execute()) {
			ResultSet set = statement.getResultSet();
			while (set.next()) {
				int columnIndex = 2;
				
				int item_id = set.getInt(columnIndex++);
				String name = set.getString(columnIndex++);
				String examine = set.getString(columnIndex++);
				boolean noted = set.getBoolean(columnIndex++);
				boolean noteable = set.getBoolean(columnIndex++);
				boolean stackable = set.getBoolean(columnIndex++); 
				int parentId = set.getInt(columnIndex++);
				int notedId = set.getInt(columnIndex++);
				boolean members = set.getBoolean(columnIndex++);
				int shopValue = set.getInt(columnIndex++);
				int highAlcValue = set.getInt(columnIndex++);
				int lowAlcValue = set.getInt(columnIndex++);
				
				int[] attackBonus = new int[5];
				attackBonus[0] = set.getInt(columnIndex++);
				attackBonus[1] = set.getInt(columnIndex++);
				attackBonus[2] = set.getInt(columnIndex++);
				attackBonus[3] = set.getInt(columnIndex++);
				attackBonus[4] = set.getInt(columnIndex++);
				
				int[] defenceBonus = new int[5];
				defenceBonus[0] = set.getInt(columnIndex++);
				defenceBonus[1] = set.getInt(columnIndex++);
				defenceBonus[2] = set.getInt(columnIndex++);
				defenceBonus[3] = set.getInt(columnIndex++); 
				defenceBonus[4] = set.getInt(columnIndex++);
				
				int prayer = set.getInt(columnIndex++);
				int strength = set.getInt(columnIndex++);
				int rangeStrength = set.getInt(columnIndex++);

				int equipmentSlot = set.getInt(columnIndex++);
				int mask = set.getInt(columnIndex++);
				
				boolean twoHanded = set.getBoolean(columnIndex++);
				
				definitions[item_id] = new ItemDefinition(item_id,name,examine,noted,noteable,stackable,parentId,notedId,members,shopValue,highAlcValue,lowAlcValue,attackBonus,defenceBonus,prayer,strength,rangeStrength, mask, equipmentSlot, twoHanded);
			}
			ItemDefinition.set(definitions);
			logger.info("Loaded " + definitions.length + " item definitions.");
		}
		return null;
	}

	@Override
	public void exceptionCaught(Throwable t) {
		t.printStackTrace();
	}

}