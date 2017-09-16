package com.dell.coe.svntogit.framework;

import java.io.IOException;

import com.dell.coe.svntogit.model.LogAnalyzeModel;

/**
 * @author Prashant_Gupta2
 *
 */


public interface ILogAnalyzer {

	public LogAnalyzeModel analyzeLog() throws IOException;

}
