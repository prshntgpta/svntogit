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
public class RemoteTagMigrationScript extends BashScriptEngine {

	/**
	 * @param modelInstance
	 */
	public RemoteTagMigrationScript(MigUserInfoModel modelInstance) {
		super(modelInstance);
		// TODO Auto-generated constructor stub
	}
	final static Logger logger = Logger.getLogger(RemoteTagMigrationScript.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dell.coe.svntogit.framework.IScriptEngine#createScript()
	 */
	public File createScript() throws IOException {

		File file = new File(getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION + "//"
				+ SvnToGitConstants.REMOTETAGMIGR_SCRIPT_BS04);
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);

		try {
			// -- command :
			// for tag in `git branch -r | grep "tags/" | sed 's/ tags\///'`;
			// do
			// git tag -a -m"Converting SVN tags" $tag refs/remotes/$tag
			// done

			bufferWriter.write("cd \""
					+ getModelInstance().getNormalizedHomeLocation()
					+ "/"
					+ this.getModelInstance()
							.getMigratedGitProjectPath() + "\";");
			bufferWriter.write("for tag in `git branch -r | grep " + '"'
					+ "tags/" + '"' + " | sed 's#svn/tags/##" + "'`;"
					+ "\r\n");
			
			/*bufferWriter.write("for tag in `git branch -r | grep " + '"'
					+ "tags/" + '"' + " | sed 's/ tags" + "\\" + "///" + "'`;"
					+ "\r\n");*/
			bufferWriter.write(" do" + "\r\n");
			bufferWriter.write("git tag -a -m" + '"' + "Converting SVN tags"
					+ '"' + " $tag svn/tags/$tag " + "\r\n");
/*			bufferWriter.write("git tag -a -m" + '"' + "Converting SVN tags"
					+ '"' + " $tag refs/remotes/$tag " + "\r\n");
*/			bufferWriter.write(" done" + "\r\n");
			System.out
					.println("Remote Tag migration script created successfully at "
							+ getModelInstance().getHomeLocation()+SvnToGitConstants.SCRIPT_LOCATION
							+ "//"
							+ SvnToGitConstants.REMOTETAGMIGR_SCRIPT_BS04);

			return file;
		} finally {
			file = null;
			bufferWriter.close();
		}

	}

}
