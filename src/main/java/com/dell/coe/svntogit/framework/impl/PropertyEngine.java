/**
 * 
 */
package com.dell.coe.svntogit.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import com.dell.coe.svntogit.framework.IPropertyEngine;
import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.framework.exceptions.InvalidPropertyFileException;

/**
 * @author Prashant_Gupta2
 *
 */
public class PropertyEngine implements IPropertyEngine {
	
	private String propertyFile;

	public PropertyEngine(String propertyFile){
		this.propertyFile = propertyFile;
	}
	
	
	/** 
	 * If false, file does not exist and will be created. If true, file exists */
	public boolean validatePropertyFile() throws IOException,InvalidPropertyFileException{
		boolean isValid = false;
		ArrayList<String> validKeys = new ArrayList<>();
		validKeys.add("project.name"); 
		validKeys.add("standard.layout"); 
		validKeys.add("git.url"); 
		validKeys.add("git.username"); 
		validKeys.add("git.password"); 
		validKeys.add("svn.url"); 
		validKeys.add("svn.username"); 
		validKeys.add("svn.password"); 
		
		
		if("".equals(propertyFile) || null == propertyFile){
			isValid = false;
			throw new InvalidPropertyFileException("Blank or NULL Property File.");
			
		}else if(!propertyFile.substring(propertyFile.lastIndexOf('.')+1).equals("props")){
			isValid = false;
			throw new InvalidPropertyFileException("Invalid Extension of Property File:"+propertyFile);
		}else{
			FileInputStream fileInS = new FileInputStream(propertyFile);
			Properties properties = new Properties();
			try {
				properties.load(fileInS);
				isValid = properties.keySet().containsAll(validKeys);
				if(!isValid){
					throw new InvalidPropertyFileException("Invalid content of Property File. Check if you have all required valid keys.");
				}
			} finally {
				fileInS.close();
			}
		}
		
		return isValid;
	}

	public String readScriptPropertyValue(String propertyName, String scriptName)
			throws IOException {
		Properties properties = new Properties();
		FileInputStream fileInS = new FileInputStream(propertyFile);
		
		try {
			properties.load(fileInS);
			String propertyValue = properties.getProperty(scriptName + "."
					+ propertyName);
			return propertyValue;
		} finally {
			fileInS.close();
		}
	}

	public Boolean writeScriptPropertyValue(String propertyName,
			String propertyValue, boolean isAppend)
			throws IOException {
		
		InputStream inStream = new FileInputStream(propertyFile);
		OutputStream outStream = new FileOutputStream(propertyFile, isAppend);
		Properties prop = new Properties();
		
		
			prop.load(inStream);
			String readValue = (String) prop.get(propertyName);
			if (readValue == null || "".equals(readValue)) {
				prop.clear();
				prop.setProperty(propertyName, propertyValue);
				prop.store(outStream, null);
			} else {
				outStream.close();
				outStream = new FileOutputStream(propertyFile, false);
				Properties dupProp = new Properties();
				Enumeration<Object> keys = prop.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					if (!key.equals(propertyName)) {
						dupProp.setProperty(key, prop.getProperty(key));
					}
				}
				dupProp.setProperty(propertyName,
						propertyValue);
				dupProp.store(outStream, null);
			}
			inStream.close();
			outStream.close();
			return true;
		
	}

	
	public String readInternalPropertyValue(String propertyName)
			throws IOException {
		
		InputStream inputStream = PropertyEngine.class.getClassLoader().getResourceAsStream(SvnToGitConstants.INT_PROP_FILE);
        PropertyResourceBundle internalResourcebundle = new PropertyResourceBundle(inputStream);
        String propValue = internalResourcebundle.getString(propertyName);
		return propValue;
	}
	
	
	public String readProjectSVNRepoInfoProperty(String propertyName)
			throws IOException {
		
		File propFileDir = new File(SvnToGitConstants.PROP_FILE_LOCATION);
		if (!propFileDir.exists()) {
			propFileDir.mkdirs();
		}
		
		File propFile = new File(propFileDir.getAbsolutePath() + "//"
				+ SvnToGitConstants.SVN_REPO_FOLDER_FILE);
		if (!propFile.exists()) {
			propFile.createNewFile();
		}
		Properties properties = new Properties();
		FileInputStream fileInS = new FileInputStream(propFile);
		
		try {
			properties.load(fileInS);
			String propertyValue = properties.getProperty(propertyName);
			return propertyValue;
		} finally {
			fileInS.close();
			propFile = null;
			propFileDir = null;
		}
	}
	
	public Boolean writeProjectSVNRepoInfoProperty(List<String> folderList)
			throws IOException {

		
		File propFileDir = new File(SvnToGitConstants.PROP_FILE_LOCATION);
		if (!propFileDir.exists()) {
			propFileDir.mkdirs();
		}
		
		File propFile = new File(propFileDir.getAbsolutePath() + "//"
				+ SvnToGitConstants.SVN_REPO_FOLDER_FILE);
		if (!propFile.exists()) {
			propFile.createNewFile();
		}
		
		InputStream inStream = new FileInputStream(propFile);
		OutputStream outStream = new FileOutputStream(propFile);
		Properties prop = new Properties();
		
		for(String foldername : folderList){
			if (foldername != null && (!("".equals(foldername)))) {
				prop.clear();
				prop.setProperty(foldername, "");
				prop.store(outStream, null);
			}
		}
			
			inStream.close();
			outStream.close();
			return true;
		
	}

	/**
	 * @return the propertyFile
	 */
	public String getPropertyFile() {
		return propertyFile;
	}

	/**
	 * @param propertyFile the propertyFile to set
	 */
	public void setPropertyFile(String propertyFile) {
		this.propertyFile = propertyFile;
	}


}
