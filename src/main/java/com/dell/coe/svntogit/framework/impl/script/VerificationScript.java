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
public class VerificationScript extends MigJarScriptEngine {

	/**
	 * @param modelInstance
	 */
	public VerificationScript(MigUserInfoModel modelInstance) {
		super(modelInstance);
		// TODO Auto-generated constructor stub
	}
	final static Logger logger = Logger.getLogger(VerificationScript.class);
	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.framework.IScriptEngine#createScript(java.lang.String)
	 */
	public File createScript() throws IOException {
		File file = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION+"//"+SvnToGitConstants.VER_SCRIPT_BS01);
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		
		try {
			bufferWriter.write("java -jar \"" + getModelInstance().getHomeLocation()
					+ "//svn-migration-scripts.jar\" verify");
			logger.info("Verification Script created successfully.");
			return file;
		} finally {
			file = null;
			bufferWriter.close();
		}
	}

}
