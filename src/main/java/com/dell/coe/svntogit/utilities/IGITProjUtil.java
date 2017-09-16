/**
 * 
 */
package com.dell.coe.svntogit.utilities;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.tmatesoft.svn.core.SVNException;

import com.dell.coe.svntogit.model.Project;

/**
 * @author Prashant_Gupta2
 *
 */
public interface IGITProjUtil {

	public abstract Project addAlltoProject() throws GitAPIException,
			IOException;

	public abstract Project createProject() throws SVNException;

	public abstract Project addBranches(String branchName) throws IOException;

	public abstract Project addTags(String tagName) throws IOException;

	public abstract Project addMaster() throws IOException;

	/**
	 * @return the gitProject
	 */
	public abstract Project getGitProject();

	/**
	 * @param gitProject the gitProject to set
	 */
	public abstract void setGitProject(Project gitProject);

	/**
	 * @return the gitRepository
	 */
	public abstract Repository getGitRepository();

	/**
	 * @param gitRepository the gitRepository to set
	 */
	public abstract void setGitRepository(Repository gitRepository);

}