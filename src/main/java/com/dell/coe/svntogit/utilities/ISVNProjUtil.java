/**
 * 
 */
package com.dell.coe.svntogit.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.io.SVNRepository;

import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.model.Category;
import com.dell.coe.svntogit.model.CodeDir;
import com.dell.coe.svntogit.model.CodeFile;
import com.dell.coe.svntogit.model.Project;
import com.dell.coe.svntogit.utilities.impl.SVNProjUtil;

/**
 * @author Prashant_Gupta2
 *
 */
public class ISVNProjUtil {

	static final Logger logger = Logger.getLogger(SVNProjUtil.class);
	protected SVNRepository svnRepository;
	private Project svnProject;

	/**
	 * 
	 */
	public ISVNProjUtil() {
		super();
	}

	public Project addAlltoProject() throws SVNException {
	
		ArrayList<String> branchesNames = new ArrayList<String>();
		String branchName=null;
		String tagName = null;
		ArrayList<String> tagNames = new ArrayList<String>();
		Collection<?> branchEntries = svnRepository.getDir(
				SvnToGitConstants.SVN_BRANCHES, -1, null, (Collection<?>) null);
		Iterator<?> iteratorForBranches = branchEntries.iterator();
		while (iteratorForBranches.hasNext()) {
			SVNDirEntry entry = (SVNDirEntry) iteratorForBranches.next();
			if(entry.getKind()==SVNNodeKind.DIR){
				branchName = entry.getName();
				branchesNames.add(branchName);
				addBranch(branchName);
			}
		}
		
		Collection<?> tagEntries = svnRepository.getDir(
				SvnToGitConstants.SVN_TAGS, -1, null, (Collection<?>) null);
		Iterator<?> iteratorForTags = tagEntries.iterator();
		while (iteratorForTags.hasNext()) {
			SVNDirEntry entry = (SVNDirEntry) iteratorForTags.next();
			if(entry.getKind()==SVNNodeKind.DIR){
				tagName = entry.getName();
				tagNames.add(tagName);
				addTag(tagName);
			}
		}
		
		addTrunk();
		return svnProject;
		/*
		 * for(Ref ref:branchesRefs){ if(!ref.getName().contains("master")){
		 * branchesNames.add(ref.getName().split("/")[2]);
		 * addBranches(ref.getName().split("/")[2]); }else{ addMaster(); }
		 */
	}

	public void listEntries(SVNRepository repository, String path, List<CodeFile> files)
			throws SVNException {
				Collection<?> entries = repository.getDir(path, -1, null,
						(Collection<?>) null);
				Iterator<?> iterator = entries.iterator();
				while (iterator.hasNext()) {
					SVNDirEntry entry = (SVNDirEntry) iterator.next();
					/*
					 * logger.info( "/" + (path.equals( "" ) ? "" : path + "/" )
					 * + entry.getName( ) + " ( author: '" + entry.getAuthor( ) +
					 * "'; revision: " + entry.getRevision( ) + "; date: " +
					 * entry.getDate( ) + ")" );
					 */
					if (entry.getKind() == SVNNodeKind.FILE) {
						String fileName = entry.getName();
						Long fileSz = entry.getSize();
						String filePath = entry.getURL().getPath().substring(5);
						CodeFile file = new CodeFile(fileName, fileSz, filePath);
						files.add(file);
			
					}
					if (entry.getKind() == SVNNodeKind.DIR) {
						listEntries(repository,
								(path.equals("")) ? entry.getName() : path
										+ SvnToGitConstants.FORWARD_SINGLE_SLASH
										+ entry.getName(), files);
					}
				}
			}

	public Project createProject() throws SVNException {
	
		svnProject = new Project();
		svnProject.setBranches(new ArrayList<CodeDir>());
		svnProject.setTags(new ArrayList<CodeDir>());
		svnProject.setTrunk(new CodeDir());
	
		svnProject
				.setRepo_url(svnRepository.getRepositoryRoot(true).toString());
		svnProject.setProjectName(svnRepository.getLocation().getPath()
				.substring(5));
	
		return null;
	}

	public Project addBranch(String branchName) throws SVNException {
	
		String branchDirectory = SvnToGitConstants.FORWARD_SINGLE_SLASH
				+ SvnToGitConstants.SVN_BRANCHES
				+ SvnToGitConstants.FORWARD_SINGLE_SLASH + branchName;
	
		String branchURL = svnRepository.getRepositoryRoot(true)
				.toDecodedString() + branchDirectory;
	
		SVNProperties prop = null;
		List<CodeFile> branchFiles = new ArrayList<CodeFile>();
		CodeDir branchDir = new CodeDir(branchURL, branchName, Category.BRANCH,
				null);
		listEntries(svnRepository, branchDirectory, branchFiles);
		branchDir.setSvnfiles(branchFiles);
		List<CodeDir> dirs = svnProject.getBranches();
		dirs.add(branchDir);
		svnProject.setBranches(dirs);
		logger.info("Files in branch " + branchName + " of SVN = "
				+ branchFiles.size());
		return svnProject;
	}

	public Project addTag(String tagName) throws SVNException {
	
		String tagDirectory = SvnToGitConstants.FORWARD_SINGLE_SLASH
				+ SvnToGitConstants.SVN_TAGS
				+ SvnToGitConstants.FORWARD_SINGLE_SLASH + tagName;
	
		String tagURL = svnRepository.getRepositoryRoot(true).toDecodedString()
				+ tagDirectory;
	
		SVNProperties prop = null;
		List<CodeFile> tagFiles = new ArrayList<CodeFile>();
		CodeDir tagDir = new CodeDir(tagURL, tagName, Category.TAG, null);
		listEntries(svnRepository, tagDirectory, tagFiles);
		tagDir.setSvnfiles(tagFiles);
		List<CodeDir> dirs = svnProject.getTags();
		dirs.add(tagDir);
		svnProject.setTags(dirs);
		logger.info("Files in tag " + tagName + " of SVN = "
				+ tagFiles.size());
		return svnProject;
	
	}

	public Project addTrunk() throws SVNException {
	
		String trunkDirectory = SvnToGitConstants.FORWARD_SINGLE_SLASH
				+ SvnToGitConstants.SVN_TRUNK;
		String trunkURL = svnRepository.getRepositoryRoot(true)
				.toDecodedString() + trunkDirectory;
	
		SVNProperties prop = null;
		/*
		 * Collection trunk = svnRepository.getDir( trunkDirectory,
		 * SVNRevision.HEAD.getNumber(), prop, (Collection) null);
		 */
		List<CodeFile> trunkFiles = new ArrayList<CodeFile>();
		CodeDir trunkDir = new CodeDir(trunkURL, SvnToGitConstants.SVN_TRUNK,
				Category.TRUNK, null);
		listEntries(svnRepository, trunkDirectory, trunkFiles);
		trunkDir.setSvnfiles(trunkFiles);
		List<CodeDir> dirs = new ArrayList<CodeDir>();
		dirs.add(trunkDir);
		svnProject.setTrunk(trunkDir);
		logger.info("Files in trunk of SVN = " + trunkFiles.size());
		return svnProject;
	}

	public List<String> listSvnRepoFolders() throws SVNException {
		 List<String> folderList = new ArrayList<String>();
	    try {
	        Collection<?> entries = svnRepository.getDir("", -1, null,
	                (Collection<?>) null);
	        Iterator<?> iterator = entries.iterator();
	        while (iterator.hasNext()) {
	            SVNDirEntry entry = (SVNDirEntry) iterator.next();
	            if (entry.getKind() == SVNNodeKind.DIR) {
	            	
	            	/*folderinfo.append("/" + entry.getName() + " (author: '" + entry.getAuthor()
	                    + "'; revision: " + entry.getRevision() + "; date: " + entry.getDate() + ")");*/
	            	
	            	folderList.add(entry.getName());
	            }
	        }
	    
	    } catch (SVNException svne) {
	        logger.error("error while listing folder entries: "
	                + svne.getMessage());
	        System.exit(1);
	    }
	    return folderList;
	}

	/**
	 * @return the svnRepository
	 */
	public SVNRepository getSvnRepository() {
		return svnRepository;
	}

	/**
	 * @param svnRepository
	 *            the svnRepository to set
	 */
	public void setSvnRepository(SVNRepository svnRepository) {
		this.svnRepository = svnRepository;
	}

	/**
	 * @param svnProject
	 *            the svnProject to set
	 */
	public void setSvnProject(Project svnProject) {
		this.svnProject = svnProject;
	}

	public Project getSvnProject() {
	
		return svnProject;
	
		/*
		 * while(iterator.hasNext()){ SVNDirEntry next = iterator.next();
		 * SVNNodeKind kind = next.getKind(); if(kind == kind.FILE){ String
		 * fileName = next.getName(); Long fileSz = next.getSize(); String
		 * filePath = next.getURL().getPath().substring(5); CodeFile file = new
		 * CodeFile(fileName,fileSz ,filePath ); files.add(file); }else if(kind
		 * == kind.DIR){ SVNURL url = next.getURL(); }
		 * 
		 * }
		 */
	
	}

}