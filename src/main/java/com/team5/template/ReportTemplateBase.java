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
	
	static	{
	config = "iCare-template-system";
	configItem = "root-template-directory";
	}
	
	/**
	 * Constructs a ReportTemplateBase object with the root directory set to the iCare template directory
	 * 
	 * @throws ConfigurationNotFoundException if iCare template system config file is not found
	 */
	public ReportTemplateBase() throws ConfigurationNotFoundException {
		super();
	}
}