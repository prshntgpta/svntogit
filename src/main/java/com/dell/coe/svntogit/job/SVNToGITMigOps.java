/**
 * 
 */
package com.dell.coe.svntogit.job;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.tmatesoft.svn.core.SVNException;

import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.framework.impl.GitSvnCloneMigrLogAnalyzer;
import com.dell.coe.svntogit.framework.impl.PropertyEngine;
import com.dell.coe.svntogit.framework.impl.UserMigrLogAnalyzer;
import com.dell.coe.svntogit.framework.impl.VerificationLogAnalyzer;
import com.dell.coe.svntogit.framework.impl.script.GenUserMapScript;
import com.dell.coe.svntogit.framework.impl.script.GitPushScript;
import com.dell.coe.svntogit.framework.impl.script.GitSvnCloneMigrationScript;
import com.dell.coe.svntogit.framework.impl.script.RemoteBranchMigrationScript;
import com.dell.coe.svntogit.framework.impl.script.RemoteTagMigrationScript;
import com.dell.coe.svntogit.framework.impl.script.VerificationScript;
import com.dell.coe.svntogit.model.LogAnalyzeModel;
import com.dell.coe.svntogit.model.MigUserInfoModel;
import com.dell.coe.svntogit.model.Project;
import com.dell.coe.svntogit.utilities.IExcelUtil;
import com.dell.coe.svntogit.utilities.IGITProjUtil;
import com.dell.coe.svntogit.utilities.ISVNProjUtil;
import com.dell.coe.svntogit.utilities.impl.ExcelUtil;
import com.dell.coe.svntogit.utilities.impl.GITProjUtil;
import com.dell.coe.svntogit.utilities.impl.SVNProjUtil;

/**
 * @author Prashant_Gupta2
 *
 */
public class SVNToGITMigOps {

	final static Logger logger = Logger.getLogger(SVNToGITMigOps.class);
	
	private MigUserInfoModel modelInstance;

	public SVNToGITMigOps(MigUserInfoModel modelInstance) {
		this.modelInstance = modelInstance;
	}

	/*
	 * void printContactNames(){
	 * 
	 * try { Resource resource = new ClassPathResource("spring-context.xml");
	 * BeanFactory factory = new XmlBeanFactory(resource);
	 * System.out.println(factory.toString() + "\n");
	 * 
	 * LDAPContactDAO ldapContact =
	 * (LDAPContactDAO)factory.getBean("ldapContact");
	 * 
	 * //List contactList = ldapContact.getContactDetails("30662"); List
	 * contactList =ldapContact.getAllContactNames();
	 * System.out.println(contactList.size());
	 * 
	 * 
	 * 
	 * } catch (DataAccessException e) { System.out.println("Error occured " +
	 * e.getCause()); } }
	 */

	/*
	 * void writeProperties() throws IOException{ PropertyEngine propEng = new
	 * PropertyEngine(); propEng.writeScriptPropertyValue("project.name",
	 * SvnToGitConstants.PROJ_NAME, true, "common");
	 * propEng.writeScriptPropertyValue("svn.url",
	 * "http://160.110.220.242:18080/svn/UXSite", true, "common");
	 * propEng.writeScriptPropertyValue("svn.username", "", true, "common");
	 * propEng.writeScriptPropertyValue("svn.password", "", true, "common");
	 * propEng.writeScriptPropertyValue("git.username", "", true, "common");
	 * propEng.writeScriptPropertyValue("git.password", "", true, "common");
	 * propEng.writeScriptPropertyValue("git.url",
	 * "https://prashant_gupta2:"+propEng
	 * .readScriptPropertyValue("git.password",
	 * "common")+"@stash.appmod.dell.com/scm/con/weblify.git", true, "common");
	 * //propEng.writeScriptPropertyValue("git.url",
	 * "ssh://@stash.appmod.dell.com/scm/con/weblify.git", true, "common");
	 * propEng.writeScriptPropertyValue("usermapping.path", "USERMAP.PATH",
	 * true, "common"); //ToDo check for standard layout
	 * propEng.writeScriptPropertyValue("standard.layout", "true", true,
	 * "svngitclone");
	 * propEng.writeScriptPropertyValue("git.migratedproject.path",
	 * "Migrated_Project", true, "common"); }
	 */

	

	void createDirs(MigUserInfoModel modelInstance) {

		// Migrated Git Project Folder
		File migratedProjectFolder = new File(modelInstance.getHomeLocation()
				+ "//" + modelInstance.getMigratedGitProjectPath());
		if (!(migratedProjectFolder.exists()))
			migratedProjectFolder.mkdirs();
		
		// Script Folder
		File scriptFolder = new File(modelInstance.getHomeLocation()
				+ SvnToGitConstants.SCRIPT_LOCATION);
		if (!(scriptFolder.exists()))
			scriptFolder.mkdirs();

		// Authors Folder
		File authorFolder = new File(modelInstance.getHomeLocation()
				+ SvnToGitConstants.AUTHORS_FILE_LOCATION);
		if (!(authorFolder.exists()))
			authorFolder.mkdirs();

		// Log Folder
		File logFolder = new File(modelInstance.getHomeLocation()
				+ SvnToGitConstants.LOG_LOCATION);
		if (!(logFolder.exists()))
			logFolder.mkdirs();

	}

	public boolean scriptHandler() throws GitAPIException, SVNException {

		boolean isAllScriptExecutionSuccess;
		VerificationScript verificationScriptHandler = new VerificationScript(
				getModelInstance());
		GenUserMapScript genUserMapScript = new GenUserMapScript(
				getModelInstance());
		GitSvnCloneMigrationScript genGitSvnCloneMigrScript = new GitSvnCloneMigrationScript(
				getModelInstance());
		RemoteTagMigrationScript genRemoteTagMigrScript = new RemoteTagMigrationScript(
				getModelInstance());
		RemoteBranchMigrationScript genRemoteBranchMigrScript = new RemoteBranchMigrationScript(
				getModelInstance());
		GitPushScript gitPushScript = new GitPushScript(getModelInstance());
		VerificationLogAnalyzer verificationLogAnalyzer = new VerificationLogAnalyzer(getModelInstance());
		UserMigrLogAnalyzer userMigrLogAnalyzer = new UserMigrLogAnalyzer(getModelInstance());
		GitSvnCloneMigrLogAnalyzer gitSvnCloneMigrLogAnalyzer = new GitSvnCloneMigrLogAnalyzer(getModelInstance());
		LogAnalyzeModel veriflogAnalyzeModel;
		LogAnalyzeModel userMaplogAnalyzeModel;
		LogAnalyzeModel gitSvnClonelogAnalyzeModel;
		PropertyEngine propertyEngine = new PropertyEngine(getModelInstance()
				.getPropertyFile());

		try {

			// Script 01 : Verification of GIT and SVN Installation
			verificationScriptHandler.createFiles();
			File verificationScript = verificationScriptHandler.createScript();
			StringBuffer verificationScriptOutput = verificationScriptHandler
					.runScript(SvnToGitConstants.VER_SCRIPT_BS01);
			verificationScriptHandler.writeLog(verificationScriptOutput,
					SvnToGitConstants.VER_SCRIPT_BS01_LOG);
			veriflogAnalyzeModel = verificationLogAnalyzer.analyzeLog();
			if (veriflogAnalyzeModel.isScriptExecutionStatus()) {
				logger.info(SvnToGitConstants.VER_SCRIPT_BS01
						+ " executed successfully");
				// Script 02 : User Mapping File Generation
				File userMapScript = genUserMapScript.createScript();
				StringBuffer genUserMapScriptOutput = genUserMapScript
						.runScript(SvnToGitConstants.USERMAP_SCRIPT_BS02);
				File file = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.AUTHORS_FILE_LOCATION
						+ "//" + SvnToGitConstants.AUTHORS_FILE);
				FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
				bufferWriter.write(genUserMapScriptOutput.toString());
				bufferWriter.close();
				genUserMapScript.writeLog(genUserMapScriptOutput,
						SvnToGitConstants.USERMAP_SCRIPT_BS02_LOG);
				userMaplogAnalyzeModel = userMigrLogAnalyzer.analyzeLog();
				if (userMaplogAnalyzeModel.isScriptExecutionStatus()) {
					logger.info(SvnToGitConstants.USERMAP_SCRIPT_BS02
							+ " executed successfully");
					// Script 03 : Git Svn Clone Migration
					if (isSvnStandardRepository()) {
						propertyEngine.writeScriptPropertyValue("standard.layout", "true", true);
					} else {
						propertyEngine
								.writeScriptPropertyValue("standard.layout","false", true);
						propertyEngine
								.writeProjectSVNRepoInfoProperty(getSvnStandardRepositoryFolders());
						logger.info("Please check and update mapping of non standard svn folder directory. File location is  "
								+ SvnToGitConstants.PROP_FILE_LOCATION
								+ "//"
								+ SvnToGitConstants.SVN_REPO_FOLDER_FILE);
						logger.info("Thread is in sleeping state for 60 seconds. Finish this activity");
						Thread.sleep(60000l);
						logger.info("Thread is active");
					}
					File migrationScript = genGitSvnCloneMigrScript
							.createScript();
					StringBuffer genGitSvnCloneMigrScriptOutput = genGitSvnCloneMigrScript
							.runScript(SvnToGitConstants.SVNGITCLONE_SCRIPT_BS03);
					genGitSvnCloneMigrScript.writeLog(
							genGitSvnCloneMigrScriptOutput,
							SvnToGitConstants.SVNGITCLONE_SCRIPT_BS03_LOG);
					gitSvnClonelogAnalyzeModel = gitSvnCloneMigrLogAnalyzer
							.analyzeLog();
					if (gitSvnClonelogAnalyzeModel.isScriptExecutionStatus()) {
						logger.info(SvnToGitConstants.SVNGITCLONE_SCRIPT_BS03
								+ " executed successfully");
						// Script 04 : Git Remote Branch Migration
						File remoteBranchMigrScript = genRemoteBranchMigrScript
								.createScript();
						StringBuffer genRemoteBranchMigrScriptOutput = genRemoteBranchMigrScript
								.runScript(SvnToGitConstants.REMOTEBRANCHMIGR_SCRIPT_BS05);
						genRemoteBranchMigrScript
								.writeLog(
										genRemoteBranchMigrScriptOutput,
										SvnToGitConstants.REMOTEBRANCHMIGR_SCRIPT_BS05_LOG);

						// Script 05 : Git Remote Tag Migration
						File remoteTagMigrScript = genRemoteTagMigrScript
								.createScript();
						StringBuffer genRemoteTagMigrScriptOutput = genRemoteTagMigrScript
								.runScript(SvnToGitConstants.REMOTETAGMIGR_SCRIPT_BS04);
						genRemoteTagMigrScript
								.writeLog(
										genRemoteTagMigrScriptOutput,
										SvnToGitConstants.REMOTETAGMIGR_SCRIPT_BS04_LOG);

						// Pre Push Excel Write Utility
						Project svnProject = this.svnRepositoryHandler();
						Project gitProject = this.gitRepositoryHandler();

						IExcelUtil jxlsUtility = new ExcelUtil(svnProject,
								gitProject, getModelInstance());
						boolean prePushSanityCheck = jxlsUtility.writeToExcel();

						// Script 06 : Git Push
						File gitPushScriptFile = gitPushScript.createScript();

						if (prePushSanityCheck) {
							StringBuffer gitPushScriptOutput = gitPushScript
									.runScript(SvnToGitConstants.GITPUSH_SCRIPT_BS06);
							genRemoteTagMigrScript.writeLog(
									gitPushScriptOutput,
									SvnToGitConstants.GITPUSH_SCRIPT_BS06_LOG);
							isAllScriptExecutionSuccess = true;
						} else {
							logger.error(" Error Statement: Code will not be pushed.");
							logger.error(" Error Log: Pre Push Sanity Failed.\nSVN Branches ## "
									+ svnProject.getBranches().size()
									+ "\nGIT Branches ## "
									+ gitProject.getBranches().size()
									+ "\n\nSVN Tags ## "
									+ svnProject.getTags().size()
									+ "\nGIT Tags ## "
									+ gitProject.getTags().size()
									+ "\n\nSVN Trunk Files ## "
									+ svnProject.getTrunk().getSvnfiles()
											.size()
									+ "\nGIT Trunk Files ## "
									+ gitProject.getTrunk().getSvnfiles()
											.size());
							isAllScriptExecutionSuccess = false;

						}

					} else {
						logger.error(SvnToGitConstants.SVNGITCLONE_SCRIPT_BS03
								+ " execution failed");
						logger.error(" Error Statement: "
								+ gitSvnClonelogAnalyzeModel.getErorStatement());
						logger.error(" Error Log: "
								+ gitSvnClonelogAnalyzeModel.getErrorLog());
						;
						isAllScriptExecutionSuccess = false;
					}
				} else {
					logger.error(SvnToGitConstants.USERMAP_SCRIPT_BS02
							+ " execution failed");
					logger.error(" Error Statement: "
							+ userMaplogAnalyzeModel.getErorStatement());
					logger.error(" Error Log: "
							+ userMaplogAnalyzeModel.getErrorLog());
					isAllScriptExecutionSuccess = false;
				}
			} else {
				logger.error(SvnToGitConstants.VER_SCRIPT_BS01
						+ " execution failed");
				logger.error(" Error Statement: "
						+ veriflogAnalyzeModel.getErorStatement());
				logger.error(" Error Log: "
						+ veriflogAnalyzeModel.getErrorLog());
				isAllScriptExecutionSuccess = false;
			}
			return isAllScriptExecutionSuccess;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isAllScriptExecutionSuccess = false;
			return isAllScriptExecutionSuccess;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isAllScriptExecutionSuccess = false;
			return isAllScriptExecutionSuccess;
		}

	}

	public Project svnRepositoryHandler() {
		try {
			ISVNProjUtil svnUtil = new SVNProjUtil(getModelInstance());
			Project svnProject = svnUtil.addAlltoProject();
			logger.info("Setup complete");
			return svnProject;
		} catch (SVNException se) {
			logger.error("SVNException while setting up repository : " + se
					+ "\n\n" + se.getStackTrace().toString());
			logger.info("Exception in SVN:" + se.getMessage());
			return null;
		}
	}

	public List<String> getSvnStandardRepositoryFolders() {
		try {
			ISVNProjUtil svnUtil = new SVNProjUtil(getModelInstance());
			logger.info("List files ---- ");
			List<String> folderList = svnUtil.listSvnRepoFolders();
			return folderList;
		} catch (SVNException se) {
			logger.error("SVNException while setting up repository : " + se
					+ "\n\n" + se.getStackTrace().toString());
			logger.info("Exception in SVN:" + se.getMessage());
			return null;
		}
	}

	public boolean isSvnStandardRepository() {

		List<String> folderList = getSvnStandardRepositoryFolders();
		if ((folderList.size() == 3)
				&& (folderList.contains(SvnToGitConstants.SVN_TRUNK))
				&& (folderList.contains(SvnToGitConstants.SVN_TAGS))
				&& (folderList.contains(SvnToGitConstants.SVN_BRANCHES))) {
			return true;
		} else {
			return false;
		}
	}

	public Project gitRepositoryHandler() throws GitAPIException, SVNException {
		try {
			IGITProjUtil gitUtil = new GITProjUtil(getModelInstance());
			Project gitProject = gitUtil.addAlltoProject();
			logger.info("Setup complete");
			return gitProject;
		} catch (IOException se) {
			logger.error("GITException while setting up repository : " + se
					+ "\n\n" + se.getStackTrace().toString());
			logger.info("Exception in GIT:" + se.getMessage());
			return null;
		}
	}

	/**
	 * @return the modelInstance
	 */
	public MigUserInfoModel getModelInstance() {
		return modelInstance;
	}

	/**
	 * @param modelInstance
	 *            the modelInstance to set
	 */
	public void setModelInstance(MigUserInfoModel modelInstance) {
		this.modelInstance = modelInstance;
	}

}
