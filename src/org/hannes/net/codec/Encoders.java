package org.hannes.net.codec;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.Element;
import org.hannes.net.codec.impl.Attribute;
import org.hannes.net.codec.impl.AttributeType;
import org.hannes.net.codec.impl.ReflectionEncoder;
import org.hannes.net.codec.impl.SerializableEncoder;
import org.hannes.util.Serializable;

/**
 * The class that manages all the encoder stuff
 * 
 * @author red
 *
 */
public class Encoders {

	/**
	 * The system ClassLoader
	 */
	private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	/**
	 * The logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Encoders.class.getName());

	/**
	 * The collection of encoders
	 */
	private static final Map<Class<? extends Object>, Encoder> encoders = new HashMap<Class<? extends Object>, Encoder>();

	/**
	 * The class that decodes {@link Serializable} objects
	 */
	private static final Encoder serializableEncoder = new SerializableEncoder();


	/**
	 * Initializes the encoders lol
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
			 * Get the target class
			 */
			Class<?> source = classLoader.loadClass(element.element("source").getText());
			
			/*
			 * If there is a target encoder set, use that
			 */
			if (element.elementText("target") != null && !element.elementText("target").equals("null")) {
				Class<?> target = classLoader.loadClass(element.element("target").getText());
				
				/*
				 * Map the encoder
				 */
				encoders.put(source, (Encoder) target.newInstance());
				
				/*
				 * Debug information
				 */
				logger.info("Encoder added for " + source + " with " + target + " attributes.");
				continue;
			}
			
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
				// FIXME: int value = element.elementText("value") == null;
				int value = -1;
				
				// FIXME: 
				int offset = -1;
				
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
			encoders.put(source, new ReflectionEncoder(attributes, index, source));
			
			/*
			 * Debug information
			 */
			logger.info("Encoder added for " + source + " with " + attributes.length + " attributes.");
		}
		logger.info("Decoders OK");
	}

	/**
	 * Get the encoder bound to the given class
	 * 
	 * @param class1
	 * @return
	 */
	public static Encoder getEncoder(Class<? extends Object> class1) {
		for (Class<?> iface : class1.getInterfaces()) {
			if (iface.equals(Serializable.class)) {
				return serializableEncoder;
			}
		}
		return encoders.get(class1);
	}

}