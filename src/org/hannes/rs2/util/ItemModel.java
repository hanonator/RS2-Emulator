package org.hannes.rs2.util;

public class ItemModel {

	/**
	 * The item id
	 */
	private final int itemId;
	
	/**
	 * The zoom
	 */
	private final int zoom;
	
	/**
	 * The interface id (type 6)
	 */
	private final int interfaceId;

	/**
	 * @param itemId
	 * @param zoom
	 * @param interfaceId
	 */
	public ItemModel(int itemId, int zoom, int interfaceId) {
		this.itemId = itemId;
		this.zoom = zoom;
		this.interfaceId = interfaceId;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @return the zoom
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * @return the interfaceId
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

}