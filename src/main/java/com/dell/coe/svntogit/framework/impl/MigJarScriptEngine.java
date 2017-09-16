/**
 * 
 */
package com.dell.coe.svntogit.framework.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.dell.coe.svntogit.framework.IScriptEngine;
import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.model.MigUserInfoModel;

/**
 * @author Prashant_Gupta2
 *
 */
public abstract class MigJarScriptEngine extends ScriptEngine implements IScriptEngine {


	/**
	 * @param modelInstance
	 */
	public MigJarScriptEngine(MigUserInfoModel modelInstance) {
		super(modelInstance);
		// TODO Auto-generated constructor stub
	}
	final static Logger logger = Logger.getLogger(MigJarScriptEngine.class);
	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.framework.IScriptEngine#runScript()
	 */
	public StringBuffer runScript(String scriptName) throws IOException, InterruptedException {
		
		StringBuffer output = new StringBuffer();
		Process process;
		Scanner scanner = new Scanner(new File(getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION+"//"+scriptName));
		String command = scanner.useDelimiter("\\Z").next();
		ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe","/C",command);
		String outputLine;
		BufferedReader bufferReader;
		BufferedWriter bufferWriter;
		try {
			
			processBuilder.redirectErrorStream(true);
			processBuilder.redirectInput();
			Instant start = Instant.now();
			process = processBuilder.start();
			
			InputStream inputStream = process.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			bufferReader = new BufferedReader(inputStreamReader);
			outputLine = bufferReader.readLine();
			while ((outputLine)!= null) {
				
				output.append(outputLine + "\n");
				outputLine = bufferReader.readLine();
			}
			Instant end = Instant.now();
			output.append("\n"+"Time taken by the script is "+Duration.between(start, end)+" minutes"+"\n");
		/*	logger.info(scriptName+" executed successfully");*/
			logger.info(output);
			bufferReader.close();
			return output;
			
		} finally{
			output = null;
			process = null;
			scanner.close();
			command = null;
			processBuilder = null;
			outputLine = null;
			bufferReader = null;
			
		}

	
	}

}
