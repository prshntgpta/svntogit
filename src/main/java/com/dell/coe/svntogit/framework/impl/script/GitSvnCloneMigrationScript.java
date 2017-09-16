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
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;

import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.framework.impl.MigJarScriptEngine;
import com.dell.coe.svntogit.framework.impl.PropertyEngine;
import com.dell.coe.svntogit.model.MigUserInfoModel;
import com.dell.coe.svntogit.utilities.ISVNProjUtil;
import com.dell.coe.svntogit.utilities.impl.SVNProjUtil;

/**
 * @author Prashant_Gupta2
 *
 */
public class GitSvnCloneMigrationScript extends MigJarScriptEngine {
	
	/**
	 * @param modelInstance
	 */
	public GitSvnCloneMigrationScript(MigUserInfoModel modelInstance) {
		super(modelInstance);
		// TODO Auto-generated constructor stub
	}
	final static Logger logger = Logger.getLogger(GitSvnCloneMigrationScript.class);

	@Override
	public StringBuffer runScript(String scriptName) throws IOException,
			InterruptedException {

		StringBuffer output = new StringBuffer();
		Process process;
		Scanner scanner = new Scanner(new File(getModelInstance().getHomeLocation()+
				SvnToGitConstants.SCRIPT_LOCATION + "//" + scriptName));
		String command = scanner.useDelimiter("\\Z").next();
		ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe","/C",command);
		String outputLine;
		BufferedReader bufferReader;
		BufferedWriter bufferWriter;
		try {

			processBuilder.redirectErrorStream(true);
			File passwdSvn = new File(getModelInstance().getHomeLocation()+"//"+"svnPasswd.txt");
			passwdSvn.createNewFile();
			FileWriter fileWriter = new FileWriter(passwdSvn);
			fileWriter.write(this.getModelInstance().getSvnPassword());
			fileWriter.close();
			processBuilder.redirectInput(passwdSvn);
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

			/*logger.info(scriptName + " executed successfully");*/
			logger.info(output);
			bufferReader.close();
			return output;

		} finally {
			output = null;
			process = null;
			scanner.close();
			command = null;
			processBuilder = null;
			outputLine = null;
			bufferReader = null;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dell.coe.svntogit.framework.IScriptEngine#createScript()
	 */
	public File createScript() throws IOException {

		File file = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION + "//"
				+ SvnToGitConstants.SVNGITCLONE_SCRIPT_BS03);
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		boolean isStandardLayout = this.getModelInstance()
				.isStandardLayout();
		String usermappingFile = this.getModelInstance()
				.getUserMappingPath();

		PropertyEngine propertyEngine = new PropertyEngine(getModelInstance().getPropertyFile());

		try {
			if (isStandardLayout) {
				// git svn clone --stdlayout --authors-file=authors.txt <SVN
				// repository URL> --username <userName> --password <password>
				// <folder location> >> log_s3.txt
				bufferWriter.write("git svn clone -s --no-metadata --prefix=svn/ --no-follow-parent --stdlayout --authors-file=\""
						+ getModelInstance().getHomeLocation()+SvnToGitConstants.AUTHORS_FILE_LOCATION
						+ "//"
						+ SvnToGitConstants.AUTHORS_FILE
						+ "\" "
						+ this.getModelInstance().getSvnURL()
						+ " --username "
						+ this.getModelInstance().getSvnUsername()
						+ /*
						 * " --password "+MigUserInfoModel.getInstance().
						 * getSvnPassword()+
						 */" "
						+ "\""+getModelInstance().getHomeLocation()
						+ "//"
						+ this.getModelInstance()
								.getMigratedGitProjectPath()+"\"");
				System.out
						.println("SVN Git Clone Script of standard layout created successfully at "
								+ getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION
								+ "//"
								+ SvnToGitConstants.SVNGITCLONE_SCRIPT_BS03);

			} else {
				StringBuffer trunkFile =  new StringBuffer();
				StringBuffer tagFiles =  new StringBuffer();
				StringBuffer branchesFiles =  new StringBuffer();
				trunkFile.append(" --trunk=/trunk");
				tagFiles.append(" --tags=/tags");
				branchesFiles.append(" --branches=/branches");
				for(String folderName : getSvnStandardRepositoryFolders()){
					if((!folderName.equalsIgnoreCase(SvnToGitConstants.SVN_TRUNK))&&SvnToGitConstants.SVN_TRUNK.equalsIgnoreCase(propertyEngine.readProjectSVNRepoInfoProperty(folderName))){
						trunkFile.append(" --trunk=/"+folderName);
					}
					else if((!folderName.equalsIgnoreCase(SvnToGitConstants.SVN_TAGS))&&SvnToGitConstants.SVN_TAGS.equalsIgnoreCase(propertyEngine.readProjectSVNRepoInfoProperty(folderName))){
						tagFiles.append(" --tags=/"+folderName);
					}
					else if((!folderName.equalsIgnoreCase(SvnToGitConstants.SVN_BRANCHES))&&SvnToGitConstants.SVN_BRANCHES.equalsIgnoreCase(propertyEngine.readProjectSVNRepoInfoProperty(folderName))){
						branchesFiles.append(" --branches=/"+folderName);
					}
				}
				bufferWriter
						.write("git svn clone -s --no-metadata --prefix=svn/ --no-follow-parent "+trunkFile.toString()+branchesFiles.toString()+tagFiles.toString()+" --authors-file="
								+getModelInstance().getHomeLocation()
								+ SvnToGitConstants.AUTHORS_FILE_LOCATION
								+ "//"
								+ SvnToGitConstants.AUTHORS_FILE
								+ " "
								+ this.getModelInstance().getSvnURL()
								+ " --username "
								+ this.getModelInstance().getSvnUsername()
								+ /*
								 * " --password "+MigUserInfoModel.getInstance().
								 * getSvnPassword()+
								 */" "
								+ getModelInstance().getHomeLocation()
								+ "//"
								+ this.getModelInstance()
										.getMigratedGitProjectPath());
				System.out
						.println("SVN Git Clone Script of non standard layout created successfully at "
								+ getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION
								+ "//"
								+ SvnToGitConstants.SVNGITCLONE_SCRIPT_BS03);
			}
			return file;
		} finally {
			file = null;
			bufferWriter.close();
		}

	}
	public List<String> getSvnStandardRepositoryFolders(){
		try{
				ISVNProjUtil svnUtil = new SVNProjUtil(getModelInstance());
				logger.info("List files ---- ");
				List<String> folderList = svnUtil.listSvnRepoFolders();
				logger.info(" files done");
				return folderList;
		}catch(SVNException se){
			logger.error("SVNException while setting up repository : " + se+"\n\n"+se.getStackTrace().toString());
			logger.info("Exception in SVN:"+se.getMessage());
			return null;
		}
	}

}
