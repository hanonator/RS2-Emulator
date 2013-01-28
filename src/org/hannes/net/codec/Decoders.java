package org.hannes.net.codec;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.Element;
import org.hannes.Main;
import org.hannes.event.Event;
import org.hannes.net.Connection;
import org.hannes.net.Message;
import org.hannes.net.codec.impl.Attribute;
import org.hannes.net.codec.impl.AttributeType;
import org.hannes.net.codec.impl.LoginDecoder;
import org.hannes.net.codec.impl.RS2LoginDecoder;
import org.hannes.net.codec.impl.ReflectionDecoder;

/**
 * The class that manages all the decoder stuff
 * 
 * @author red
 *
 */
public class Decoders {

	/**
	 * The packet sizes
	 */
	public static final int[] PACKET_SIZES = new int[256];

	/**
	 * The system ClassLoader
	 */
	private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	/**
	 * The logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Decoders.class.getName());
	
	/**
	 * The collection of decoders.
	 */
	private static final Decoder[] decoders = new Decoder[256];
	
	/**
	 * The login decoder.
	 */
	private static LoginDecoder loginDecoder = new RS2LoginDecoder();

	/**
	 * Initializes the decoders lol
	 * 
	 * @throws Exception
	 */
	public static void initialize(Document decoderDocument) throws Exception {
		
		/*
		 * Parse all packet information
		 */
		for (Iterator<Element> iterator = decoderDocument.getRootElement().elements().iterator(); iterator.hasNext(); ) {
			Element element = iterator.next();

			/*
			 * Get the index of the decoder
			 */
			int index = Integer.valueOf(element.element("index").getText());
			
			/*
			 * Set the size of the packets
			 */
			PACKET_SIZES[index] = Integer.valueOf(element.elementText("size"));
			
			/*
			 * Get the target class
			 */
			Class<?> target = classLoader.loadClass(element.element("target").getText());
			
			/*
			 * Parse the decoder's attributes
			 */
			List<Element> elements = element.element("attributes").elements("attribute");
			
			/*
			 * The decoder's attributes
			 */
			Attribute[] attributes = new Attribute[elements.size()];
			
			/*
			 * Index cus I is lazy
			 */
			int idx = 0;
			
			/*
			 * loop through attributes
			 */
			for (Iterator<Element> it = elements.iterator(); it.hasNext(); ) {
				Element node = it.next();
				
				/*
				 * Get the type of the attribute
				 */
				AttributeType type = AttributeType.valueOf(node.attributeValue("type").toUpperCase());
				
				/*
				 * Get the value of the attribute. This is -1 when no constant value is set
				 */
				int value = type == AttributeType.CONSTANT ? Integer.valueOf(node.attributeValue("value")) : -1;
				
				/*
				 * Get the offset value (if any)
				 */
				String offsetText = node.attributeValue("offset");
				int offset = offsetText == null ? 0 : Integer.valueOf(offsetText);
				
				/*
				 * Get the name of the field
				 */
				String field = node.getText();
				
				/*
				 * Set the attribute
				 */
				attributes[idx++] = new Attribute(type, field, value, offset);
			}
			
			/*
			 * Add the decoder to the collection
			 */
			decoders[index] = new ReflectionDecoder(attributes, target);
			
			/*
			 * Debug information
			 */
			logger.info("Decoder added on index " + index + " with " + attributes.length + " attributes.");
		}
		logger.info("Decoders OK");
	}

	/**
	 * 
	 * @param message
	 * @param connection
	 */
	public static void dispatch(Message message, Connection connection) throws Exception {
		Decoder decoder = message.getOpcode() == -1 ? loginDecoder : decoders[message.getOpcode()];
		if (decoder != null) {
			Event event = decoder.decode(message, connection);
			if (event != null) {
				Main.getEventHub().offer(event);
			}
		} else {
			logger.info("Unknown message received with opcode " + message.getOpcode() + " with size " + message.size());
		}
	}

}