/**
 * 
 */
package com.dell.coe.svntogit.job;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.tmatesoft.svn.core.SVNException;

import com.dell.coe.svntogit.model.MigUserInfoModel;

/**
 * @author Prashant_Gupta2
 *
 */
public class MigrationJob implements Callable<Boolean> {
	
	final static Logger logger = Logger.getLogger(MigrationJob.class);
	
	MigrationJob(String propFile, String userHome){
		
		
		modelInstance = new MigUserInfoModel(propFile,userHome);
		this.propFile = propFile;
		mainInstance = new SVNToGITMigOps(modelInstance);
		
	}
	
	
	private MigUserInfoModel modelInstance;
	private String propFile;
	private SVNToGITMigOps mainInstance;
	
	/*@Override
	public void run(){
		
		try {
			mainInstance.readAndSetToModel(modelInstance,propFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mainInstance.createDirs(modelInstance);
		try {
			boolean isScriptExecutionSuccess = mainInstance.scriptHandler();
		} catch (GitAPIException | SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
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

	/**
	 * @return the propFile
	 */
	public String getPropFile() {
		return propFile;
	}

	/**
	 * @param propFile the propFile to set
	 */
	public void setPropFile(String propFile) {
		this.propFile = propFile;
	}

	/**
	 * @return the mainInstance
	 */
	public SVNToGITMigOps getMainInstance() {
		return mainInstance;
	}

	/**
	 * @param mainInstance the mainInstance to set
	 */
	public void setMainInstance(SVNToGITMigOps mainInstance) {
		this.mainInstance = mainInstance;
	}


	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Boolean call() throws Exception {

		mainInstance.createDirs(modelInstance);
		try {
			boolean isScriptExecutionSuccess = mainInstance.scriptHandler();
			return isScriptExecutionSuccess;
		} catch (GitAPIException | SVNException e) {
			logger.error("GIT SVN Exception while executing Scripts.    "+e.getMessage());
			return false;
		}
	
	}

	

}
