package org.hannes.locale;

import org.hannes.Main;
import org.hannes.entity.Player;

/**
 * TODO
 * @author red
 *
 */
public class Viewport {

	/**
	 * The player's viewport
	 */
	private Region[] regions;

	/**
	 * @param regions
	 */
	public Viewport(Region[] regions) {
		this.regions = regions;
	}

	public Viewport(Player player) {
		this (Main.getRealm().getSurroundingRegions(player.getRegion()));
	}

	public void update(Player player) {
		this.update(player.getRegion());
	}

	public void update(Region region) {
		this.regions = Main.getRealm().getSurroundingRegions(region);
	}

	public boolean contains(Player player) {
		for (Region region : regions) {
			if (!region.contains(player)) {
				return false;
			}
		}
		return true;
	}

	public boolean contains(Region region) {
		for (int i = 0; i < regions.length; i++) {
			if (region.getX() == regions[i].getX() && region.getY() == regions[i].getY()) {
				return true;
			}
		}
		return false;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Viewport) {
			Viewport vp = (Viewport) obj;
			for (int i = 0; i < regions.length; i++) {
				if (!regions[i].equals(vp.regions[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}