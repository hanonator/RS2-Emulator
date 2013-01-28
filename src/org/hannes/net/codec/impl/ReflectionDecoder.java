package org.hannes.net.codec.impl;

import java.lang.reflect.Field;

import org.hannes.event.Event;
import org.hannes.net.Connection;
import org.hannes.net.Message;
import org.hannes.net.codec.Decoder;
import org.hannes.util.ChannelBufferUtils;

/**
 * Decodes using shizz from XML files
 * 
 * @author red
 *
 */
public class ReflectionDecoder implements Decoder {

	/**
	 * The attributes that need to be decoded
	 */
	private final Attribute[] attributes;
	
	/**
	 * The target class
	 */
	private final Class<?> target;

	/**
	 * 
	 * @param attributes
	 * @param target
	 */
	public ReflectionDecoder(Attribute[] attributes, Class<?> target) {
		this.attributes = attributes;
		this.target = target;
	}

	@Override
	public Event decode(Message message, Connection connection) throws Exception {
		// TODO: Check if target is instance of request
		Event event = (Event) target.getConstructor(Connection.class).newInstance(connection);
		
		for (Attribute attribute : attributes) {
			/*
			 * sup, ma nigga, we be gettin' dem attribuz
			 */
			Field field = target.getDeclaredField(attribute.getField());
			
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
				field.set(event, message.get());
				break;
			case SHORT:
				field.set(event, message.getShort());
				break;
			case INT:
				field.set(event, message.getInt());
				break;
			case LONG:
				field.set(event, message.getLong());
				break;
			case STRING:
				field.set(event, ChannelBufferUtils.readRS2String(message.getBuffer()));
				break;
			case BOOLEAN:
				field.set(event, message.get() == 1);
				break;
			case ARRAY:
				byte[] data = new byte[message.size() - attribute.getOffset()];
				if (data.length > 0) {
					message.get(data, 0, data.length);
				}
				field.set(event, data);
				break;
			case CONSTANT:
				field.set(event, attribute.getValue());
				break;
			default:
				throw new UnsupportedOperationException("no such type supported");
			}
			
			/*
			 * Set the field's accessibility back to what it was
			 */
			field.setAccessible(accessible);
		}
		
		return event;
	}

}