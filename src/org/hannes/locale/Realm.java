package org.hannes.locale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hannes.Main;
import org.hannes.entity.Entity;
import org.hannes.entity.NPC;
import org.hannes.entity.Player;
import org.hannes.event.InitializeEvent;
import org.hannes.net.Connection;
import org.hannes.util.EntityList;

/**
 * A realm is the collection of map-chunks, entities, and all other
 * stuff needed for the server to function properly.
 * 
 * @author red
 *
 */
public class Realm {

	/**
	 * The global player list
	 */
	private final List<Player> players = new EntityList<>(2046);

	/**
	 * The global npc list
	 */
	private final List<NPC> npcs = new EntityList<>(8192);

	/**
	 * Map of occupied regions
	 */
	private final Map<Integer, Region> regions = new HashMap<Integer, Region>();

	/**
	 * Finds a player
	 * 
	 * @return
	 */
	public boolean find(String username) {
		for (Player player : players) {
			if (player.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * destroys the player object
	 * 
	 * @param player
	 */
	public void destroy(Player player) {
		player.getRegion().remove(player);
		players.remove(player.getIndex());
	}

	/**
	 * Allocate a new player object with a free index
	 * 
	 * @return
	 */
	public Player allocatePlayer(Connection connection) {
		for (int i = 0; i < players.size(); i++) {
			if (!players.contains(i)) {
				return new Player(i, connection);
			}
		}
		throw new NullPointerException("no space left on them servers");
	}

	/**
	 * Allocate a new NPC object with a free index
	 * 
	 * @return
	 */
	public NPC allocateNpc(int type) {
		for (int i = 0; i < players.size(); i++) {
			if (!players.contains(i)) {
				return new NPC(i, type);
			}
		}
		throw new NullPointerException("no space left to allocate NPC.");
	}

	/**
	 * Registers the player into the list
	 * 
	 * @param player
	 */
	public void register(Player player) throws Exception {
		if (players.get(player.getIndex()) != null) {
			throw new IllegalStateException("cannot register player");
		}
		players.add(player);
		player.getConnection().setPlayer(player);
		Main.getEventHub().offer(new InitializeEvent(player.getConnection()));
	}

	/**
	 * Registers the player into the list
	 * 
	 * @param player
	 */
	public void register(NPC npc) throws Exception {
		if (npcs.get(npc.getIndex()) != null) {
			throw new IllegalStateException("cannot register NPC");
		}
		npcs.add(npc);
	}

	/**
	 * Gets the region the given entity is located in
	 * 
	 * @param entity
	 * @return
	 */
	public Region get(Entity entity) {
		return get(entity.getLocation());
	}

	/**
	 * Gets the region the given tile is located in
	 * 
	 * @param tile
	 * @return
	 */
	public Region get(Location location) {
		return get((location.getX() >> 3), (location.getY() >> 3));
	}

	/**
	 * Gets the region for a given coordinate
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Region get(int x, int y) {
		int identifier = (x << 16) | y;
		if (!regions.containsKey(identifier)) {
			regions.put(identifier, new Region(x, y));
		}
		return regions.get(identifier);
	}

	/**
	 * Removes a region attached to the given identifier
	 * 
	 * @param identifier
	 */
	public void remove(int identifier) {
		regions.remove(identifier);
	}

	/**
	 * Removes a region
	 * 
	 * @param region
	 */
	public void remove(Region region) {
		remove((region.getX() << 16) | region.getY());
	}

	/**
	 * Gets the surrounding regions for this region (given region included)
	 * 
	 * @param region
	 * @return
	 */
	public Region[] getSurroundingRegions(Region region) {
		Region[] regions = new Region[9];
		for (int i = 0; i < regions.length; i++) {
			regions[i] = get(region.getX() - 1 + i % 3, region.getY() - 1 + i / 3);
		}
		return regions;
	}

	/**
	 * Get the visible players for a given region
	 * 
	 * @return
	 */
	public List<Player> getVisiblePlayers(Region region) {
		List<Player> players = new ArrayList<Player>();
		for (Region r : getSurroundingRegions(region)) {
			players.addAll(r.getPlayers());
		}
		return players;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<NPC> getNpcs() {
		return npcs;
	}

}