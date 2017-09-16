package com.dell.coe.svntogit.framework;

import java.util.HashMap;

/**
 * @author Prashant_Gupta2
 *
 */

public interface IUserInputEngine {

	HashMap<String,String> getUserInput();

    void writeUserInput(HashMap<String,String> userInput);
	
}
