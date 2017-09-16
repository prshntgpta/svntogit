/**
 * 
 */
package com.dell.coe.svntogit.utilities.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.tmatesoft.svn.core.SVNException;

import com.dell.coe.svntogit.framework.SvnToGitConstants;
import com.dell.coe.svntogit.model.Category;
import com.dell.coe.svntogit.model.CodeDir;
import com.dell.coe.svntogit.model.CodeFile;
import com.dell.coe.svntogit.model.MigUserInfoModel;
import com.dell.coe.svntogit.model.Project;
import com.dell.coe.svntogit.utilities.IGITProjUtil;

/**
 * @author Prashant_Gupta2
 *
 */
public class GITProjUtil implements IGITProjUtil {
	
	final static Logger logger = Logger.getLogger(GITProjUtil.class);
	
	private Repository gitRepository;
	private Project gitProject;
	private MigUserInfoModel modelInstance;
	
	public GITProjUtil(MigUserInfoModel modelInstance) throws IOException, SVNException{
		this.modelInstance = modelInstance;
		gitRepository = new FileRepositoryBuilder().setGitDir(new File(modelInstance.getHomeLocation()+"/"+modelInstance.getMigratedGitProjectPath()+"/.git")).build();
		this.createProject();
	}
	
	
	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IGITProjUtil#addAlltoProject()
	 */
	@Override
	public Project addAlltoProject() throws GitAPIException, IOException{
		
		Git gitInstance = new Git(gitRepository);
		ArrayList<String> branchesNames = new ArrayList<String>();
		ArrayList<String> tagNames = new ArrayList<String>();
		List<Ref> branchesRefs = gitInstance.branchList().setListMode( null ).call();
		for(Ref ref:branchesRefs){
			if(!ref.getName().contains("master")){
				branchesNames.add(ref.getName().split("/")[2]);
				addBranches(ref.getName().split("/")[2]);
			}else{
				addMaster();
			}
		}
		
		List<Ref> tagsRefs = gitInstance.tagList().call();
		for (Ref ref : tagsRefs) {

			tagNames.add(ref.getName().split("/")[2]);
			addTags(ref.getName().split("/")[2]);

		}
		
		return gitProject;
	}
	
	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IGITProjUtil#createProject()
	 */
	@Override
	public Project createProject() throws SVNException {
		
		MigUserInfoModel instance = modelInstance;
		gitProject = new Project();
		gitProject.setBranches(new ArrayList<CodeDir>());
		gitProject.setTags(new ArrayList<CodeDir>());
		gitProject.setTrunk(new CodeDir());

		gitProject.setRepo_url(instance.getGitURL());
		gitProject.setProjectName(instance.getMigratedGitProjectPath());

		return gitProject;
	}
	
	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IGITProjUtil#addBranches(java.lang.String)
	 */
	@Override
	public Project addBranches(String branchName) throws IOException{
	
		Ref master = gitRepository.getRef(branchName);
		RevWalk walk = new RevWalk(gitRepository);

        RevCommit commit = walk.parseCommit(master.getObjectId());
        RevTree tree = commit.getTree();
        logger.info("Having tree: " + tree);
        
		List<CodeFile> files = new ArrayList<CodeFile>();
		CodeDir trunkDir = new CodeDir(master.getName(), master.getName().split("/")[2], Category.BRANCH, null);
        // now use a TreeWalk to iterate over all files in the Tree recursively
        // you can set Filters to narrow down the results if needed
        TreeWalk treeWalk = new TreeWalk(gitRepository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        while (treeWalk.next()) {
        	
        	String fileName = treeWalk.getNameString();
        	
			AnyObjectId objectId = treeWalk.getObjectId(0);
			Long fileSz = treeWalk.getObjectReader().getObjectSize(treeWalk.getObjectId(0), ObjectReader.OBJ_ANY);
			String filePath = master.getName()+SvnToGitConstants.FORWARD_SINGLE_SLASH+treeWalk.getPathString();
			CodeFile file = new CodeFile(fileName,fileSz ,filePath );
			files.add(file);
            //logger.info("found: " + treeWalk.getPathString());
        }
        
        trunkDir.setSvnfiles(files);
		List<CodeDir> dirs = gitProject.getBranches();
		dirs.add(trunkDir);
		gitProject.setBranches(dirs);
		
		return gitProject;
	
		
	
	}

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IGITProjUtil#addTags(java.lang.String)
	 */
	@Override
	public Project addTags(String tagName) throws IOException{

		String tagDirectory = SvnToGitConstants.GIT_TAGS+SvnToGitConstants.FORWARD_SINGLE_SLASH+tagName;
		MigUserInfoModel instance = modelInstance;
		Ref tag = gitRepository.getRef(tagDirectory);
		RevWalk walk = new RevWalk(gitRepository);

        RevCommit commit = walk.parseCommit(tag.getObjectId());
        RevTree tree = commit.getTree();
        logger.info("Having tree: " + tree);
       
		List<CodeFile> files = new ArrayList<CodeFile>();
		CodeDir tagDir = new CodeDir(tag.getName(), tag.getName().split("/")[2], Category.TAG, null);
        // now use a TreeWalk to iterate over all files in the Tree recursively
        // you can set Filters to narrow down the results if needed
        TreeWalk treeWalk = new TreeWalk(gitRepository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        while (treeWalk.next()) {
        	
        	String fileName = treeWalk.getNameString();
        	
			AnyObjectId objectId = treeWalk.getObjectId(0);
			Long fileSz = treeWalk.getObjectReader().getObjectSize(treeWalk.getObjectId(0), ObjectReader.OBJ_ANY);
			String filePath = tag.getName()+SvnToGitConstants.FORWARD_SINGLE_SLASH+treeWalk.getPathString();
			CodeFile file = new CodeFile(fileName,fileSz ,filePath );
			files.add(file);
            //logger.info("found: " + treeWalk.getPathString());
        }
        
        tagDir.setSvnfiles(files);
		List<CodeDir> dirs = gitProject.getTags();
		dirs.add(tagDir);
		gitProject.setTags(dirs);
		
		return gitProject;
	
		
	
	
	}
	
	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IGITProjUtil#addMaster()
	 */
	@Override
	public Project addMaster() throws IOException{

		MigUserInfoModel instance = modelInstance;
		Ref master = gitRepository.getRef(SvnToGitConstants.GIT_MASTER);
		RevWalk walk = new RevWalk(gitRepository);

        RevCommit commit = walk.parseCommit(master.getObjectId());
        RevTree tree = commit.getTree();
        logger.info("Having tree: " + tree);
       
		List<CodeFile> files = new ArrayList<CodeFile>();
		CodeDir trunkDir = new CodeDir(master.getName(), SvnToGitConstants.GIT_MASTER, Category.TRUNK, null);
        // now use a TreeWalk to iterate over all files in the Tree recursively
        // you can set Filters to narrow down the results if needed
        TreeWalk treeWalk = new TreeWalk(gitRepository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        while (treeWalk.next()) {
        	
        	String fileName = treeWalk.getNameString();
        	
			AnyObjectId objectId = treeWalk.getObjectId(0);
			Long fileSz = treeWalk.getObjectReader().getObjectSize(treeWalk.getObjectId(0), ObjectReader.OBJ_ANY);
			String filePath = master.getName()+SvnToGitConstants.FORWARD_SINGLE_SLASH+treeWalk.getPathString();
			CodeFile file = new CodeFile(fileName,fileSz ,filePath );
			files.add(file);
            //logger.info("found: " + treeWalk.getPathString());
        }
        
        trunkDir.setSvnfiles(files);
		
		gitProject.setTrunk(trunkDir);
		
		return gitProject;
	
		
	}

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IGITProjUtil#getGitProject()
	 */
	@Override
	public Project getGitProject() {
		return gitProject;
	}

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IGITProjUtil#setGitProject(com.dell.coe.svntogit.pojo.Project)
	 */
	@Override
	public void setGitProject(Project gitProject) {
		this.gitProject = gitProject;
	}

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IGITProjUtil#getGitRepository()
	 */
	@Override
	public Repository getGitRepository() {
		return gitRepository;
	}

	/* (non-Javadoc)
	 * @see com.dell.coe.svntogit.utilities.impl.IGITProjUtil#setGitRepository(org.eclipse.jgit.lib.Repository)
	 */
	@Override
	public void setGitRepository(Repository gitRepository) {
		this.gitRepository = gitRepository;
	}


	/**
	 * @return the instance
	 */
	public MigUserInfoModel getInstance() {
		return modelInstance;
	}


	/**
	 * @param instance the instance to set
	 */
	public void setInstance(MigUserInfoModel instance) {
		this.modelInstance = instance;
	}
	

}
