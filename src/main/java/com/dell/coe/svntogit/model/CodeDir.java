/**
 * 
 */
package com.dell.coe.svntogit.model;

import java.util.List;

/**
 * @author Prashant_Gupta2
 *
 */
public class CodeDir {
	
	private String url;
	private String name;
	private Category category;
	private List<CodeFile> svnfiles;
	
	public CodeDir(){
		
	}
	
	/**
	 * @param url
	 * @param name
	 * @param category
	 * @param svnfiles
	 */
	public CodeDir(String url, String name, Category category,
			List<CodeFile> svnfiles) {
		super();
		this.url = url;
		this.name = name;
		this.category = category;
		this.svnfiles = svnfiles;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	/**
	 * @return the svnfiles
	 */
	public List<CodeFile> getSvnfiles() {
		return svnfiles;
	}
	/**
	 * @param svnfiles the svnfiles to set
	 */
	public void setSvnfiles(List<CodeFile> svnfiles) {
		this.svnfiles = svnfiles;
	}
	
	
	

}
