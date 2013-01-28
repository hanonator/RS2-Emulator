package org.hannes.locale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hannes.Main;
import org.hannes.entity.Player;
import org.hannes.entity.RSObject;

/**
 * Represents an 8x8 tile map-chunk. This is used in the entity
 * updating sequence thingermabob and has use in pathfinding and
 * stuff like that.
 * 
 * @author red
 *
 */
public class Region {

	/**
	 * The width of a single region
	 */
	public static final int WIDTH = 8;
	
	/**
	 * The height of a single region
	 */
	public static final int HEIGHT = 8;

	/**
	 * The players in this particular region
	 */
	private final List<Player> players = new ArrayList<>();

	/**
	 * The players in this particular region
	 */
	private final List<? extends RSObject> objects = new ArrayList<>();

	/**
	 * The x-value of this region's coordinate
	 */
	private final int x;
	
	/**
	 * The y-value of this region's coordinate
	 */
	private final int y;

	/**
	 * The clipping flags for this region
	 */
	private int[][] clipmap;

	/**
	 * Gets the region for the tile that is given
	 * 
	 * @param location
	 */
	public Region(Location location) {
		this.x = location.getX() >> 3;
		this.y = location.getY() >> 3;
	}

	/**
	 * Creates a region from a given x and y
	 * 
	 * @param x
	 * @param y
	 */
	public Region(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Register a player
	 * 
	 * @param player
	 */
	public void register(Player player) {
		this.players.add(player);
	}

	/**
	 * Remove a player from this region
	 * 
	 * @param player
	 */
	public void remove(Player player) {
		this.players.remove(player);
	}

	/**
	 * checks if a player is in this region
	 * 
	 * @param player
	 * @return
	 */
	public boolean contains(Player player) {
		return players.contains(player);
	}

	/**
	 * Destroys this region
	 */
	public void destroy() {
		players.clear();
		Main.getRealm().remove(this);
	}

	@SuppressWarnings("unchecked")
	public <T extends RSObject> T getObject(int x, int y, int z, Class<?> c) {
		for (RSObject object  : objects) {
			if (object.getLocation().equals(new Location(x, y, z))
					&& object.getClass().equals(c)) {
				return (T) object;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public int size() {
		return players.size();
	}

	/**
	 * 
	 * @return
	 */
	public Iterator<Player> iterator() {
		return players.iterator();
	}

	/**
	 * 
	 * @return
	 */
	public List<Player> getPlayers() {
		return players;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Region) {
			Region other = (Region) obj;
			return other.x == x && other.y == y;
		}
		return super.equals(obj);
	}

	public int[][] getClipmap() {
		return clipmap;
	}

	public void setClipmap(int[][] clipmap) {
		this.clipmap = clipmap;
	}

	public int getClippingFlag(int x, int y) {
		return clipmap[x][y];
	}

}