package com.adv.enhance.base;

import java.util.List;

import com.adv.enhance.api.IProperty;

public class Publisher {

	private String id;
	private String name;
	private List<IProperty> properties;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<IProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<IProperty> properties) {
		this.properties = properties;
	}
	
}
