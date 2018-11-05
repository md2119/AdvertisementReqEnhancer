package com.adv.enhance.base;

import java.io.Serializable;

import com.adv.enhance.api.IProperty;

public class SiteProperty implements IProperty {

	String name;
	Serializable value;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Serializable getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
