/**
 * 
 */
package com.dell.coe.svntogit.framework.impl;

import com.dell.coe.svntogit.framework.ILogAnalyzer;
import com.dell.coe.svntogit.model.MigUserInfoModel;

/**
 * @author vatsala_sharma
 *
 */
public abstract class LogAnalyzer implements ILogAnalyzer {
	
	private MigUserInfoModel modelInstance;

	/**
	 * @param modelInstance
	 */
	public LogAnalyzer(MigUserInfoModel modelInstance) {
		super();
		this.modelInstance = modelInstance;
	}

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
	
	

	
}
