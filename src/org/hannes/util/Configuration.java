package org.hannes.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class Configuration {

	private final Map<String, String> attributes = new HashMap<>();

	public Configuration() {
		
	}

	public Configuration(Document document) throws Exception {
		load(document);
	}

	public void load(Document document) throws Exception {
		for (Iterator<Element> iterator = document.getRootElement().elementIterator(); iterator.hasNext(); ) {
			Element element = iterator.next();
			attributes.put(element.getName(), element.getText());
		}
	}

	public String get(String key) {
		return attributes.get(key);
	}

	public boolean contains(String string) {
		return attributes.containsKey(string);
	}

}