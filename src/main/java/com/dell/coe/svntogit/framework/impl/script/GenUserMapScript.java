/**
 * 
 */
package com.dell.coe.svntogit.framework.impl.script;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.framework.impl.MigJarScriptEngine;
import com.dell.coe.svntogit.model.MigUserInfoModel;

/**
 * @author Prashant_Gupta2
 *
 */
public class GenUserMapScript extends MigJarScriptEngine{
	
	/**
	 * @param modelInstance
	 */
	public GenUserMapScript(MigUserInfoModel modelInstance) {
		super(modelInstance);
		// TODO Auto-generated constructor stub
	}

	final static Logger logger = Logger.getLogger(GenUserMapScript.class);

	public File createScript() throws IOException {

		File file = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION+"//"+SvnToGitConstants.USERMAP_SCRIPT_BS02);
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		
		try {
			bufferWriter.write("java -jar \"" + getModelInstance().getHomeLocation()
					+ "//svn-migration-scripts.jar\" authors "+this.getModelInstance().getSvnURL()+" "+this.getModelInstance().getSvnUsername()+" "+this.getModelInstance().getSvnPassword());
			logger.info("User Info Mapping Script created successfully at "+getModelInstance().getHomeLocation()+" "+SvnToGitConstants.LOG_LOCATION+"//"+SvnToGitConstants.USERMAP_SCRIPT_BS02_LOG);
			return file;
		} finally {
			file = null;
			bufferWriter.close();
			
		}
	
	}

}
