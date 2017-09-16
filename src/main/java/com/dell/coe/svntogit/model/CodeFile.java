/**
 * 
 */
package com.dell.coe.svntogit.model;

/**
 * @author Prashant_Gupta2
 *
 */
public class CodeFile {

	private String fileName;
	private Long fileSize;
	private String fileUrl;
	
	public CodeFile(){
		
	}
	
	/**
	 * @param fileName
	 * @param fileSz
	 * @param fileUrl
	 */
	public CodeFile(String fileName, Long fileSz, String fileUrl) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSz;
		this.fileUrl = fileUrl;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the fileSz
	 */
	public Long getFileSz() {
		return fileSize;
	}
	/**
	 * @param fileSz the fileSz to set
	 */
	public void setFileSz(Long fileSz) {
		this.fileSize = fileSz;
	}
	/**
	 * @return the fileUrl
	 */
	public String getFileUrl() {
		return fileUrl;
	}
	/**
	 * @param fileUrl the fileUrl to set
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	
}
