package org.hannes.entity;

/**
 * Called RSObject so I don't have to manually import it every
 * time I do something wid it yo
 * 
 * @author red
 *
 */
public class RSObject extends Entity {

	/**
	 * The object id
	 */
	private int id;

	/**
	 * The type of object
	 */
	private int type;
	
	/**
	 * The object's orientation
	 */
	private int orientation;
	
	/**
	 * The width of the object (in tiles)
	 */
	private int width;
	
	/**
	 * The height of the object (in tiles)
	 */
	private int height;

	public RSObject() {
		super(-1);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the orientation
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public static enum ObjectClass {
		DOOR, FENCE, TREE;
	}

}