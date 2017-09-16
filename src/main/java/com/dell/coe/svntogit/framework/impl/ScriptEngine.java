/**
 * 
 */
package com.dell.coe.svntogit.framework.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.dell.coe.svntogit.framework.IScriptEngine;
import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.framework.impl.script.VerificationScript;
import com.dell.coe.svntogit.model.MigUserInfoModel;

/**
 * @author Prashant_Gupta2
 *
 */
public abstract class ScriptEngine implements IScriptEngine {

	final static Logger logger = Logger.getLogger(ScriptEngine.class);
	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.framework.IScriptEngine#createFiles()
	 */
	
	private MigUserInfoModel modelInstance;
	
	public ScriptEngine(MigUserInfoModel modelInstance){
		this.modelInstance = modelInstance;
	}
	
	public boolean createFiles() throws IOException {

		
		InputStream inputStream = VerificationScript.class.getClassLoader().getResourceAsStream(SvnToGitConstants.SVN_MIG_SCRIPT_JAR);
   	    File bfile =new File(modelInstance.getHomeLocation()+"//"+SvnToGitConstants.SVN_MIG_SCRIPT_JAR);
   	    FileOutputStream outStream = new FileOutputStream(bfile);
   	    byte[] buffer = new byte[1024];
   	    File scriptFile = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION+"//"+SvnToGitConstants.VER_SCRIPT_BS01);
		File scriptLogFile = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.LOG_LOCATION+"//"+SvnToGitConstants.VER_SCRIPT_BS01_LOG);
   	    int length;

   	    boolean flag1;
		boolean flag2;
		try {
			//copy the file content in bytes 
			while ((length = inputStream.read(buffer)) > 0){
				outStream.write(buffer, 0, length);
			}
			flag1 = scriptFile.createNewFile();
			flag2 = scriptLogFile.createNewFile();
			logger.info("SVN Migration Jar is copied successful!");
			return (flag1&&flag2);
		} finally{
			 inputStream.close();
		   	 outStream.close();
		   	 scriptFile = null;
		   	 scriptLogFile = null;
		   	 buffer = null;
		   	 bfile = null;
		}
	
	}


	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.framework.IScriptEngine#writeLog(java.lang.StringBuffer, java.lang.String)
	 */
	public void writeLog(StringBuffer log, String logName) throws IOException {
		File file = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.LOG_LOCATION+"//"+logName);
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferReader = new BufferedWriter(fileWriter);
		
		try {
			bufferReader.write(log.toString());
			logger.info(logName+" Output logged successfully.");
		} finally{
			file = null;
			bufferReader.close();
		}

	

	}


	/**
	 * @return the modelInstance
	 */
	public MigUserInfoModel getModelInstance() {
		return modelInstance;
	}


	/**
	 * @param modelInstance the modelInstance to set
	 */
	public void setModelInstance(MigUserInfoModel modelInstance) {
		this.modelInstance = modelInstance;
	}

}
