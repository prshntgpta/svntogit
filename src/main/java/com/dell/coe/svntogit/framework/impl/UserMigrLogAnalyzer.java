/**
 * 
 */
package com.dell.coe.svntogit.framework.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.model.LogAnalyzeModel;
import com.dell.coe.svntogit.model.MigUserInfoModel;

/**
 * @author vatsala_sharma
 *
 */
public class UserMigrLogAnalyzer extends LogAnalyzer {

	/**
	 * @param modelInstance
	 */
	public UserMigrLogAnalyzer(MigUserInfoModel modelInstance) {
		super(modelInstance);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.framework.ILogAnalyzer#analyzeLog()
	 */
	public LogAnalyzeModel analyzeLog() throws IOException {

		LogAnalyzeModel loginfo = new LogAnalyzeModel();
		FileInputStream fileStream ;
		BufferedReader bufferedReader;
		String strLine;
		try{
		File propFileDir = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.LOG_LOCATION);
		if (propFileDir.exists()) {
			File propFile = new File(propFileDir.getAbsolutePath() + "//"+ SvnToGitConstants.USERMAP_SCRIPT_BS02_LOG);
			if (propFile.exists()) {
				fileStream = new FileInputStream(propFile);
				bufferedReader = new BufferedReader(new InputStreamReader(fileStream));
				//StringBuffer strLine = null;
			//	Map<String,String> errorMap = new HashMap<String,String>();
				strLine = bufferedReader.readLine();
				
				while ((strLine)!= null) {
					
					if(strLine.toString().contains("E215004")){
						loginfo.setErorStatement("Authentication Failed - Could not communicate with Subversion: check if the URL of the repository, the username and the password are all valid");
						loginfo.setErrorLog(strLine);
						loginfo.setScriptExecutionStatus(false);
						break;
					}
					else if((strLine.toString().contains("E730065"))||(strLine.toString().contains("E175012"))){
						loginfo.setErorStatement("Unreachable host URL or Connection timed out - Could not communicate with Subversion: check if the URL of the repository is valid");
						loginfo.setErrorLog(strLine);
						loginfo.setScriptExecutionStatus(false);
						break;
					}
					else if(strLine.toString().contains("Error")){
						StringTokenizer st = new StringTokenizer(strLine,"Error");
						loginfo.setErorStatement(st.nextToken());
						loginfo.setErrorLog(strLine);
						loginfo.setScriptExecutionStatus(false);
						break;
					}
					else{
						loginfo.setScriptExecutionStatus(true);
					}
					strLine = bufferedReader.readLine();
				}
				
			}	
			else{
				loginfo.setErorStatement("Log file doesnot exist");
				loginfo.setErrorLog("Log file doesnot exist");
				loginfo.setScriptExecutionStatus(false);
			}
		}
		else{
			loginfo.setErorStatement("Log folder doesnot exist");
			loginfo.setErrorLog("Log folder doesnot exist");
			loginfo.setScriptExecutionStatus(false);
		}
		}
		finally{
			
			strLine = null;
			bufferedReader = null;
			fileStream = null;
			
		}
		return loginfo;
	}

	
}
