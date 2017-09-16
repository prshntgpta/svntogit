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
import com.dell.coe.svntogit.framework.impl.BashScriptEngine;
import com.dell.coe.svntogit.model.MigUserInfoModel;

/**
 * @author Prashant_Gupta2
 *
 */
public class RemoteBranchMigrationScript extends BashScriptEngine {
	
	/**
	 * @param modelInstance
	 */
	public RemoteBranchMigrationScript(MigUserInfoModel modelInstance) {
		super(modelInstance);
		// TODO Auto-generated constructor stub
	}

	final static Logger logger = Logger.getLogger(RemoteBranchMigrationScript.class);

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.framework.IScriptEngine#createScript()
	 */
	public File createScript() throws IOException {

		File file = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION+"//"+SvnToGitConstants.REMOTEBRANCHMIGR_SCRIPT_BS05);
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		
		
		try {
			// -- command : 
			// for branch in `git branch -r | grep -v -E "tag|trunk"`;         
			// do
			// git branch $branch refs/remotes/$branchsss
			// done
			bufferWriter.write("cd \""+getModelInstance().getNormalizedHomeLocation()+"/"+this.getModelInstance().getMigratedGitProjectPath()+"\";");
			//	bufferWriter.write("for branch in `git branch -r | grep -v -E "+'"'+"tag|trunk"+'"'+"`;  "+"\r\n");
				bufferWriter.write("for branch in `git branch -r | grep -v -E "+'"'+"tag|trunk"+'"'+"|sed 's#svn/##'`;  "+"\r\n");
				bufferWriter.write(" do"+"\r\n");
//				bufferWriter.write("git branch $branch refs/remotes/$branch"+"\r\n");
				bufferWriter.write("git branch $branch svn/$branch"+"\r\n");
				bufferWriter.write(" done"+"\r\n");
				logger.info("Remote Tag migration script created successfully at "+getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION+"//"+SvnToGitConstants.REMOTEBRANCHMIGR_SCRIPT_BS05);
				
			
			return file;
		} finally {
			file = null;
			bufferWriter.close();
		}
	
	}

}
