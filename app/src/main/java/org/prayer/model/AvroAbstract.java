package org.prayer;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;

public class AvroAbstract<T> {
	// add appropriate schema generation for any class that will be loaded to disk and used
	// within data model stored in avro format
	private T object;
	public Map<String, Object> map = new HashMap<>();

	public AvroAbstract(T object) {
		this.object = object;
		this.setMap();
	}
	// map
	public Map<String, Object> getMap() {
		return this.map;
	}
	public void setMap() {
		try {
			for (Field field: this.object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				String key = field.getName();
				String value = String.valueOf(field.get(this.object));
				this.map.put(key, value);
			}	
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
