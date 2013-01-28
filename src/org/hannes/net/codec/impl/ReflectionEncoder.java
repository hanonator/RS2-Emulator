package org.hannes.net.codec.impl;

import java.lang.reflect.Field;

import org.hannes.net.MessageBuilder;
import org.hannes.net.MessageLength;
import org.hannes.net.codec.Encoder;
import org.hannes.net.codec.MessageType;

public class ReflectionEncoder implements Encoder {

	/**
	 * The attributes that need to be decoded
	 */
	private final Attribute[] attributes;
	
	/**
	 * The opcode of the message that is sent to the client
	 */
	private final int opcode;
	
	/**
	 * The target class
	 */
	private final Class<?> source;

	public ReflectionEncoder(Attribute[] attributes, int opcode, Class<?> source) {
		this.attributes = attributes;
		this.opcode = opcode;
		this.source = source;
	}

	@Override
	public Object encode(Object object) throws Exception {
		/*
		 * Default message length is fixed
		 */
		MessageLength length = MessageLength.FIXED;
		
		/*
		 * Get the adjusted value
		 */
		MessageType type = source.getAnnotation(MessageType.class);
		if (type != null) {
			length = type.value();
		}
		
		/*
		 * Create the builder with the correct opcode
		 */
		MessageBuilder builder = new MessageBuilder(opcode, length);

		/*
		 * Add all the attributes in the correct order
		 */
		for (Attribute attribute : attributes) {
			/*
			 * sup, ma nigga, we be gettin' dem attribuz
			 */
			Field field = source.getDeclaredField(attribute.getField());
			
			/*
			 * Remember the accessibility
			 */
			boolean accessible = field.isAccessible();
			
			/*
			 * Set the field as accessible
			 */
			field.setAccessible(true);
			
			/*
			 * Add the correct value to the buffer
			 */
			switch (attribute.getType()) {
			case BYTE:
				builder.put((byte) field.getInt(object));
				break;
			case SHORT:
				builder.putShort((short) field.getInt(object));
				break;
			case INT:
				builder.putInt(field.getInt(object));
				break;
			case LONG:
				builder.putLong(field.getLong(object));
				break;
			case BOOLEAN:
				builder.put((byte) (field.getBoolean(object) ? 1 : 0));
				break;
			case STRING:
				builder.putString((String) field.get(object));
				break;
			default:
				throw new UnsupportedOperationException("no such type supported");
			}
			
			/*
			 * Set the field's accessibility back to what it was
			 */
			field.setAccessible(accessible);
		}
		
		/*
		 * Build the message and send it
		 */
		return builder.build();
	}

}