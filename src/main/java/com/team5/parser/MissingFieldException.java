package com.team5.parser;

import java.util.ArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import com.team5.template.TemplateLoader;
import java.util.ArrayList;


public class MissingFieldException extends Exception{
	private ArrayList<JSONObject> missingFields;
    
    public MissingFieldException(ArrayList<JSONObject> missingFields){
        super();
        this.missingFields = missingFields;
    }
    
    public ArrayList<JSONObject> getMissingField(){
		return this.missingFields;
    }

}
