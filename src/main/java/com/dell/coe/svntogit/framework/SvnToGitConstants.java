package com.dell.coe.svntogit.framework;

/**
 * @author Prashant_Gupta2
 *
 */

public interface SvnToGitConstants {
	
	/*String PROJ_NAME = "DefaultProject";*/
	
	/*public static  String HOME_LOCATION = "C://.virtuoso//.svngitmig//"+SvnToGitConstants.PROJ_NAME;
	public static  String HOME_LOCATION_LINUX = "/C/.virtuoso/.svngitmig/"+SvnToGitConstants.PROJ_NAME;*/
	
	
	
	String SVN_TRUNK = "trunk";
	String SVN_BRANCHES = "branches";
	String SVN_TAGS = "tags";

	String GIT_MASTER= "master";
	String GIT_BRANCHES = "branches";
	String GIT_TAGS = "tags";

	String FORWARD_SINGLE_SLASH = "/";
	String FORWARD_DOUBLE_SLASH = "//";

	
	String LOG_LOCATION =	"//logs";
	String SCRIPT_LOCATION = "//scripts";
	String LINUX_SCRIPT_LOCATION =  "//scripts";
	
	
	String VER_SCRIPT_BS01 = "verificationScript.bat";
	String VER_SCRIPT_BS01_LOG = "verificationScriptLog.log";
	
	String USERMAP_SCRIPT_BS02 = "userMapping.bat";
	String USERMAP_SCRIPT_BS02_LOG = "userMappingScriptLog.log";
	
	String PROP_FILE_LOCATION = "C://.virtuoso.//props//";
	String PROP_FILE = "svngitmig_properties.txt";
	String SVN_REPO_FOLDER_FILE = "svnRepoFolderList.txt";
	
	String AUTHORS_FILE_LOCATION = "//authors";
	String AUTHORS_FILE = "Authors.txt";
	
	String INT_PROP_FILE = "svngitmig_internal.properties";
	String SVN_MIG_SCRIPT_JAR = "svn-migration-scripts.jar";

	String SVNGITCLONE_SCRIPT_BS03 = "svngitcloneScript.bat";
	String SVNGITCLONE_SCRIPT_BS03_LOG = "svngitcloneScriptLog.log";

	String REMOTETAGMIGR_SCRIPT_BS04 = "remoteTagMigrScript.bash";
	String REMOTETAGMIGR_SCRIPT_BS04_LOG = "remoteTagMigrScriptLog.log";
	
	String REMOTEBRANCHMIGR_SCRIPT_BS05 = "remoteBranchMigrScript.bash";
	String REMOTEBRANCHMIGR_SCRIPT_BS05_LOG = "remoteBranchMigrScriptLog.log";
	
	String GITPUSH_SCRIPT_BS06 = "gitPushMigrScript.bash";
	String GITPUSH_SCRIPT_BS06_LOG = "gitPushMigrScriptLog.log";
	
}
