package com.dell.coe.svntogit.framework;

import java.io.IOException;


/**
 * @author Prashant_Gupta2
 *
 */

public interface IPropertyEngine {

	 String readScriptPropertyValue(String propertyName, String scriptName) throws IOException;
	 
	 String readInternalPropertyValue(String propertyName) throws IOException;
	 
	// Boolean writeScriptPropertyValue(String propertyName, String propertyValue, boolean isAppend, String scriptName) throws IOException;
}
