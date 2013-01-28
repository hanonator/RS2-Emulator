package org.hannes.net.codec.impl;


/**
 * Represents an attribute
 * 
 * @author red
 *
 */
public class Attribute {
	
	/**
	 * The attribute's type
	 */
	private final AttributeType type;
	
	/**
	 * The name of the field
	 */
	private final String field;
	
	/**
	 * When a constant value is to be set to the field. This is always
	 * of an integer type. So I don't have to program too much, yo.
	 */
	private final int value;
	
	/**
	 * Read offset for byte-arrays
	 */
	private final int offset;

	/**
	 * 
	 * @param type
	 * @param field
	 */
	public Attribute(AttributeType type, String field, int value, int offset) {
		this.type = type;
		this.field = field;
		this.value = value;
		this.offset = offset;
	}

	public AttributeType getType() {
		return type;
	}

	public String getField() {
		return field;
	}

	public int getValue() {
		return value;
	}

	public int getOffset() {
		return offset;
	}
	
}