/**
 * 
 */
package com.dell.coe.svntogit.model;

import java.util.List;

/**
 * @author Prashant_Gupta2
 *
 */
public class Project {
	private String projectName;
	private String repo_url;
	private List<CodeDir> branches;
	private CodeDir trunk;
	private List<CodeDir> tags;
	
	public Project(){
		
	}
	
	/**
	 * @param projectName
	 * @param svn_url
	 * @param codeBranches
	 */
	public Project(Integer id, String projectName, String svn_url,
			List<CodeDir> branches) {
		super();
		this.projectName = projectName;
		this.repo_url = svn_url;
		this.branches = branches;
	}


	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the repo_url
	 */
	public String getRepo_url() {
		return repo_url;
	}

	/**
	 * @param svn_url the repo_url to set
	 */
	public void setRepo_url(String repo_url) {
		this.repo_url = repo_url;
	}

	/**
	 * @return the codeBranches
	 */
	public List<CodeDir> getBranches() {
		return branches;
	}

	/**
	 * @param codeBranches the codeBranches to set
	 */
	public void setBranches(List<CodeDir> branches) {
		this.branches = branches;
	}

	/**
	 * @return the trunk
	 */
	public CodeDir getTrunk() {
		return trunk;
	}

	/**
	 * @param trunk the trunk to set
	 */
	public void setTrunk(CodeDir trunk) {
		this.trunk = trunk;
	}

	/**
	 * @return the tags
	 */
	public List<CodeDir> getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(List<CodeDir> tags) {
		this.tags = tags;
	}
	
	

}
