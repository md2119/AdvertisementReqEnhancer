package com.adv.enhance.api;

import java.util.List;

public interface ISite {
	
	/**
	 * 
	 * @return
	 */
	public String getId();
	
	/**
	 * 
	 * @return
	 */
	public void setURL(String url);
	
	/**
	 * 
	 * @return
	 */
	public String getURL();
	
	/**
	 * 
	 * @return
	 */
	public List<IProperty> getProperties();
	
	/**
	 * 
	 * @return
	 */
	public IProperty getProperty(String name);

	/**
	 * 
	 * @param property
	 */
	public void addProperty(IProperty property);
	
}
