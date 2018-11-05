package com.adv.enhance.api;

import java.io.Serializable;

public interface IProperty {

	/**
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * 
	 * @return
	 */
	public Serializable getValue();
	
	/**
	 * 
	 */
	public void setValue(String value);
}
