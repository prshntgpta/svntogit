package com.dell.coe.svntogit.framework;

/**
 * @author Prashant_Gupta2
 *
 */

import java.io.File;
import java.io.IOException;

public interface IScriptEngine {
	
	boolean createFiles() throws IOException;
	
    File createScript() throws IOException;

    StringBuffer runScript(String scriptName) throws IOException,InterruptedException;
    
    void writeLog(StringBuffer log, String logName) throws IOException;

}
