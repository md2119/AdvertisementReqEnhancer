package com.adv.enhance.base;

import com.adv.enhance.api.IProperty;

public class DemographyProperty implements IProperty {

	private String name;
	private String value;
	
	public String getId() {
		return name;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
