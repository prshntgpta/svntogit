/**
 * 
 */
package com.dell.coe.svntogit.job;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import com.dell.coe.svntogit.framework.exceptions.InvalidDirectoryException;
import com.dell.coe.svntogit.framework.exceptions.InvalidPropertyFileException;
import com.dell.coe.svntogit.framework.impl.PropertyEngine;

/**
 * @author Prashant_Gupta2
 *
 */
public class SVNToGITMigration {
	
	final static Logger logger = Logger.getLogger(SVNToGITMigration.class);
	
	public static void main(String... args){

		File dir = null;
		ArrayList<String> propFiles = new ArrayList<>();
		try{
			if(args.length!=0){
				dir = new File(args[0]);
			}else{
				throw new InvalidDirectoryException("No Directory specified for Properties file.");
			}
			
			String[] everythingInThisDir = dir.list();
			if(everythingInThisDir==null){
				throw new InvalidDirectoryException("No properties file in specified Directory:"+dir.getAbsolutePath());
			}
			
			for (String name : everythingInThisDir) {
				propFiles.add(name);
			}
			int size=propFiles.size();
			
	
			Map<String,Future<Boolean>> resultList = new HashMap<>() ;
			Future<Boolean> submit = null;
			ThreadPoolExecutor tpe = (ThreadPoolExecutor)Executors.newFixedThreadPool(size);
			String name = null;
			String homeLocation = System.getProperty("homelocation");
			if("".equals(homeLocation) || null == homeLocation){
				Path currentRelativePath = Paths.get("");
				homeLocation = currentRelativePath.toAbsolutePath().toString();
				logger.info("No System Value present for homelocation. Home location is set to default:"+homeLocation);
			}
			for(String propFil:propFiles){
					if(new PropertyEngine(dir.getAbsolutePath()+"\\"+propFil).validatePropertyFile()){
					submit = tpe.submit(new MigrationJob(dir.getAbsolutePath()+"\\"+propFil,homeLocation));
					resultList.put(name,submit);
				}
			}
				
			tpe.shutdown();
		}catch(IOException|InvalidDirectoryException|InvalidPropertyFileException iEx){
			logger.error(iEx.getMessage());
		}
				/*			
				t = new Thread(new MigrationJob(dir.getAbsolutePath()+"\\"+propFil,projectName,name),name);
				arrayOfThreads.put(name,t);
				i++;
				t.start();
				*/
			
			
			/*for(String str:resultList.keySet()){
				logger.info("Result of Migration Job:"+str+": "+resultList.get(str).isDone());
			}*/
			
			
			/*SvnToGitMigMain svnToGitMigMain = new SvnToGitMigMain();
			//svnToGitMigMain.writeProperties();
			svnToGitMigMain.readAndSetToModel();
			svnToGitMigMain.updateConstants();
			svnToGitMigMain.createDirs();
			boolean isScriptExecutionSuccess = svnToGitMigMain.scriptHandler();
			if(isScriptExecutionSuccess){
				
			}*/
		
		}

}
