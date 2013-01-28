package org.hannes.event.hub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.Element;
import org.hannes.event.Event;
import org.hannes.net.codec.Decoders;

/**
 * FIXME: I have absolutely no control over what handler is attempting to be cast to another.
 * This needs to be fixed when I feel like fixing it. So probably never.
 * 
 * @author red
 *
 */
public class EventHub {

	/**
	 * The system ClassLoader
	 */
	private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	/**
	 * The logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Decoders.class.getName());

	/**
	 * The event handlers
	 */
	private final Map<Class<? extends Event>, List<EventHandler<? extends Event>>> map = new HashMap<>();

	/**
	 * TODO: Holy shit this is bad
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void initialize(Document document) throws Exception {
		/*
		 * The root element
		 */
		Element root = document.getRootElement();
		
		/*
		 * Parse all packet information
		 */
		for (Iterator<Element> iterator = root.elementIterator("handler"); iterator.hasNext(); ) {
			Element element = iterator.next();
			
			String className = element.attributeValue("class");
			String handlerName = element.getText();
			
			add((Class<? extends Event>) classLoader.loadClass(className),
					(EventHandler<? extends Event>) classLoader.loadClass(handlerName).newInstance());
			
			logger.info("eventhandler [" + handlerName + "] added for " + className);
		}
	}

	/**
	 * Add a {@link EventHandler}
	 * @param handler
	 */
	public void add(Class<? extends Event> c, EventHandler<? extends Event> handler) {
		List<EventHandler<? extends Event>> list = map.get(c);
		if (list == null) {
			list = new ArrayList<>();
			map.put(c, list);
		}
		list.add(handler);
	}

	/**
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public <T extends Event> void offer(T event) throws Exception {
		if (event == null) {
			throw new NullPointerException("event is null");
		}
		
		List<EventHandler<? extends Event>> list = map.get(event.getClass());
		if (list == null) {
			return;
		}
		
		for (Iterator<EventHandler<? extends Event>> iterator = list.iterator(); iterator.hasNext(); ) {
			EventHandler<T> handler = (EventHandler<T>) iterator.next();
			handler.handleEvent(event, event.getConnection().getPlayer());
		}
	}

}