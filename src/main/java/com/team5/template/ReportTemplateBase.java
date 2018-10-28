/**
 * 
 */
package com.team5.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import com.team5.utilities.JSONLoader;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * @author Saad
 *
 */

/**
 * The base template system class to be used for all reports
 */
public class ReportTemplateBase extends TemplateBase{
	
	/**
	 * Creates a ReportTemplateBase object with the root directory set to the iCare template directory
	 * 
	 * @return Returns a ReportTemplateBase object with root directory
	 * @throws ConfigurationNotFoundException
	 */
	public static ReportTemplateBase create() throws ConfigurationNotFoundException {		
		JSONObject configJSON = ConfigurationLoader.loadConfiguration("iCare-template-system");
		String rootDirPath = (String) configJSON.get("root-template-directory");
		
		return new ReportTemplateBase(rootDirPath);
	}
	
	/**
	 * Constructs the ReportTemplateBase object
	 * 
	 * @param rootDirPath
	 */
	private ReportTemplateBase(String rootDirPath) {
		super(rootDirPath);
	}
}