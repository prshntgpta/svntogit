/**
 * 
 */
package com.dell.coe.svntogit.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.dell.coe.svntogit.framework.impl.PropertyEngine;

/**
 * @author Prashant_Gupta2
 *
 */
public final class MigUserInfoModel {
	
	private final String projectName;
	private final String svnURL;
	private final String gitURL;
	private final String homeLocation;
	//private final String linuxHomeLocation;
	private final String svnUsername;
	private final String gitUsername;
	private final String propertyFile;
	
	private final String svnPassword;
	private final String gitPassword;
	
	private final String userMappingPath;
	
	private final boolean isStandardLayout;
	
	private final String migratedGitProjectPath;
	
	//private final static MigUserInfoModel INSTANCE = new MigUserInfoModel();;
	
	/*private MigUserInfoModel(){
		
	}*/
	
	/*public static MigUserInfoModel getInstance(){
		return INSTANCE;
	}*/
	
	public MigUserInfoModel(String userPropertyFile, String UserhomeLocation) {

		propertyFile = userPropertyFile ;
		PropertyEngine propertyEngine = new PropertyEngine(propertyFile);
		Properties properties = new Properties();
		
		FileInputStream fileInS;
		try {
			fileInS = new FileInputStream(propertyFile);
			properties.load(fileInS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		projectName = properties.getProperty("project.name");
		homeLocation = UserhomeLocation+"//"+projectName;
		svnURL = properties.getProperty("svn.url");
		svnUsername = properties.getProperty("svn.username");
		svnPassword = properties.getProperty("svn.password");
		gitURL = properties.getProperty("git.url");
		gitUsername = properties.getProperty("git.username");
		gitPassword = properties.getProperty("git.password");
		userMappingPath = properties.getProperty("usermapping.path");
		isStandardLayout = Boolean.valueOf(properties.getProperty("standard.layout"));
		migratedGitProjectPath = "MigratedCode";
	}
	
	/**
	 * @return the svnURL
	 */
	public String getSvnURL() {
		return svnURL;
	}
	
	/**
	 * @return the gitURL
	 */
	public String getGitURL() {
		return gitURL;
	}
	
	
	/**
	 * @return the svnUsername
	 */
	public String getSvnUsername() {
		return svnUsername;
	}
	
	/**
	 * @return the gitUsername
	 */
	public String getGitUsername() {
		return gitUsername;
	}
	/**
	 * @param gitUsername the gitUsername to set
	 */
	
	/**
	 * @return the svnPassword
	 */
	public String getSvnPassword() {
		return svnPassword;
	}
	
	/**
	 * @return the gitPassword
	 */
	public String getGitPassword() {
		return gitPassword;
	}


	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	

	/**
	 * @return the userMappingPath
	 */
	public String getUserMappingPath() {
		return userMappingPath;
	}

	

	/**
	 * @return the isStandardLayout
	 */
	public boolean isStandardLayout() {
		return isStandardLayout;
	}

	
	
	/**
	 * @return the migratedGitProjectPath
	 */
	public String getMigratedGitProjectPath() {
		return migratedGitProjectPath;
	}

	
	
	/**
	 * @return the homeLocation
	 */
	public String getHomeLocation() {
		return homeLocation;
	}
	
	/**
	 * @return the linuxHomeLocation
	 */
	/*public String getLinuxHomeLocation() {
		return linuxHomeLocation;
	}
*/
	/**
	 * @return the propertyFile
	 */
	public String getPropertyFile() {
		return propertyFile;
	}

	/**
	 * @return
	 */
	public String getNormalizedHomeLocation() {
		return "/"+this.homeLocation.replaceAll(":", "/").trim();
	}

}
