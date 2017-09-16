/**
 * 
 */
package com.dell.coe.svntogit.utilities.impl;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.dell.coe.svntogit.model.MigUserInfoModel;
import com.dell.coe.svntogit.utilities.ISVNProjUtil;

/**
 * @author Prashant_Gupta2
 *
 */
public class SVNProjUtil extends ISVNProjUtil {
	
	private MigUserInfoModel instance ;
	public SVNProjUtil(MigUserInfoModel modelInstance) throws SVNException {

		instance = modelInstance;
		DAVRepositoryFactory.setup();
		svnRepository = SVNRepositoryFactory.create(SVNURL
				.parseURIDecoded(instance.getSvnURL()));
		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(instance.getSvnUsername(),
						instance.getSvnPassword());
		svnRepository.setAuthenticationManager(authManager);
		this.createProject();

	}
}
