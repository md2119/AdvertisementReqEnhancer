package com.adv.enhance.base;

import java.util.List;
import java.util.NoSuchElementException;

import com.adv.enhance.api.IProperty;
import com.adv.enhance.api.ISite;

public class Site implements ISite {

	private String id;
	private String url;
	private List<IProperty> properties;
	
	public Site(String id, String url) {
		this.id = id;
		this.url = url;
	}
	
	public String getId() {
		return id;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getURL() {
		return this.url;
	}

	public List<IProperty> getProperties() {
		return this.properties;
	}

	public void addProperty(IProperty property) {
		//iterate over the list to check whether property exists
		this.properties.add(property);
		
	}

	public IProperty getProperty(String name) {
		
		for(IProperty property : this.properties)
			if(property.getName().equals(name))
				return property;
		throw new NoSuchElementException("Property not found");
	}
}
