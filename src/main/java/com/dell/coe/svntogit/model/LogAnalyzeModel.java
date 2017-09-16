/**
 * 
 */
package com.dell.coe.svntogit.model;

/**
 * @author vatsala_sharma
 *
 */
public class LogAnalyzeModel {
	
	private boolean scriptExecutionStatus;
	private String erorStatement;
	private String errorLog;
	
	
	public boolean isScriptExecutionStatus() {
		return scriptExecutionStatus;
	}
	public void setScriptExecutionStatus(boolean scriptExecutionStatus) {
		this.scriptExecutionStatus = scriptExecutionStatus;
	}
	public String getErorStatement() {
		return erorStatement;
	}
	public void setErorStatement(String erorStatement) {
		this.erorStatement = erorStatement;
	}
	public String getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}
	
	

}
