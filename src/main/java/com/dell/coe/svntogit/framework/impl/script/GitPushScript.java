/**
 * 
 */
package com.dell.coe.svntogit.framework.impl.script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.framework.impl.BashScriptEngine;
import com.dell.coe.svntogit.model.MigUserInfoModel;

/**
 * @author Prashant_Gupta2
 *
 */
public class GitPushScript extends BashScriptEngine {
	
	/**
	 * @param modelInstance
	 */
	public GitPushScript(MigUserInfoModel modelInstance) {
		super(modelInstance);
		// TODO Auto-generated constructor stub
	}



	final static Logger logger = Logger.getLogger(GitPushScript.class);
	
		public StringBuffer runScript(String scriptName) throws IOException,InterruptedException {

			StringBuffer output = new StringBuffer();
			Process process;
			Scanner scanner = new Scanner(new File(getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION+"//"+scriptName));
			String command = scanner.useDelimiter("\\Z").next();
			ProcessBuilder processBuilder = new ProcessBuilder("bash.exe",getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION+"//"+scriptName);
			String outputLine;
			BufferedReader bufferReader;
			BufferedWriter bufferWriter;
			try {
				
			processBuilder.redirectErrorStream(true);
			File passwdGit = new File(getModelInstance().getHomeLocation()+"//"+"gitPasswd.txt");
			passwdGit.createNewFile();
			FileWriter fileWriter = new FileWriter(passwdGit);
			fileWriter.write(this.getModelInstance().getGitPassword());
			fileWriter.close();
			processBuilder.redirectInput(passwdGit);
			process = processBuilder.start();

			InputStream inputStream = process.getInputStream();

			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			
			
			
			
			/*OutputStream outputStream = process.getOutputStream();
			OutputStreamWriter out = new OutputStreamWriter(outputStream);
			out.write(MigUserInfoModel.getInstance().getSvnPassword());*/
			bufferReader = new BufferedReader(inputStreamReader);
			outputLine = bufferReader.readLine();
			while ((outputLine) != null) {
				logger.info(outputLine);
				output.append(outputLine + "\n");
				outputLine = bufferReader.readLine();
			}

			logger.info(scriptName + " executed successfully");
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
	
	

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.framework.IScriptEngine#createScript()
	 */
	public File createScript() throws IOException {

		File file = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION + "//"
				+ SvnToGitConstants.GITPUSH_SCRIPT_BS06);
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);

		try {
			// -- command :
			// for tag in `git branch -r | grep "tags/" | sed 's/ tags\///'`;
			// do
			// git tag -a -m"Converting SVN tags" $tag refs/remotes/$tag
			// done

			bufferWriter.write("cd "+"\""+getModelInstance().getHomeLocation()+"/"+this.getModelInstance().getMigratedGitProjectPath()+"\";");
			bufferWriter.write("git remote add origin "+this.getModelInstance().getGitURL());
			bufferWriter.newLine();
			bufferWriter.write("git push --all");
			bufferWriter.newLine();
			bufferWriter.write("git push --tags");
			logger.info("GIT push migration script created successfully at "
							+ getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION
							+ "//"
							+ SvnToGitConstants.GITPUSH_SCRIPT_BS06);

			return file;
		} finally {
			file = null;
			bufferWriter.close();
		}

	}

}
