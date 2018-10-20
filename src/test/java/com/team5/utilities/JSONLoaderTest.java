package com.team5.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JSONLoaderTest {
  final String rootPath = "testFiles/JSONTestFiles";
  final String file1Path = rootPath + "/testJSONFile_valid1.json";
  final String file1Contents = "{\"field1\":5,\"field3\":true,\"field2\":\"abc\"}";
  final String file2Path = rootPath + "/testJSONFile_valid2.json";
  final String file2Contents = "{\"field1\":-2,\"field3\":false,\"field2\":\"bcd\"}";
  final String invalidJSONPath = rootPath + "/testJSONFile_invalid.json";
  final String file3Path = rootPath + "/dir2/testJSONFile_valid3.json";
  final String file3Contents = "{\"field1\":\"cat\",\"field3\":-1000,\"field2\":\"abc\",\"field4\":1050}";

 public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
  //  JSONObject obj = JSONLoader.parseJSONFile(new File("src/test/java/com/team5/utilities/testJSONFile_valid2.json"));
  //  System.out.println(obj.toString());
  ArrayList<JSONObject> objs = JSONLoader.parseAllJSONFiles(new File("testFiles/JSONTestFiles/testJSONFile_valid1.json"), false);
  System.out.println(objs);
 }
  
  @Test
  @DisplayName("Parse valid JSON file from file")
  void testParseValidJSONFileFromFile() throws FileNotFoundException, IOException, ParseException {
    JSONObject obj = JSONLoader.parseJSONFile(new File(file1Path));
    assertEquals(file1Contents, obj.toJSONString());
  }
  
  @Test
  @DisplayName("Parse non-existent JSON file from file")
  void testParseNonExistentJSONFileFromFile() throws FileNotFoundException, IOException, ParseException {
    assertThrows(FileNotFoundException.class, ()->{
      JSONLoader.parseJSONFile(new File(rootPath + "/DNE.json"));
    },  "exception was thrown for non-existent file");
  }
  
  @Test
  @DisplayName("Parse invalid JSON file from file")
  void testParseInvalidJSONFileFromFile() throws FileNotFoundException, IOException, ParseException {
    assertThrows(ParseException.class, ()->{
      JSONLoader.parseJSONFile(new File(invalidJSONPath));
    },  "exception was thrown for invalid json file");
  }
  
  @Test
  @DisplayName("Parse valid JSON file from path")
  void testParseValidJSONFileFromPath() throws FileNotFoundException, IOException, ParseException {
    JSONObject obj = JSONLoader.parseJSONFile(file1Path);
    assertEquals(file1Contents, obj.toJSONString());
  }
  
  @Test
  @DisplayName("Parse non-existent JSON file from path")
  void testParseNonExistentJSONFileFromPath() throws FileNotFoundException, IOException, ParseException {
    assertThrows(FileNotFoundException.class, ()->{
      JSONLoader.parseJSONFile(rootPath + "/DNE.json");
    },  "exception was thrown for non-existent file");
  }
  
  @Test
  @DisplayName("Parse invalid JSON file from path")
  void testParseInvalidJSONFileFromPath() throws FileNotFoundException, IOException, ParseException {
    assertThrows(ParseException.class, ()->{
      JSONLoader.parseJSONFile(invalidJSONPath);
    },  "exception was thrown for invalid json file");
  }
  
  @Test
  @DisplayName("Parse all JSON files in directory non recursive")
  void testParseAllJSONInDirNonRecursive() throws NotDirectoryException {
    ArrayList<JSONObject> objs = JSONLoader.parseAllJSONFiles(rootPath, false);
    assertEquals("[" + file1Contents + ", " + file2Contents + "]", objs.toString());
  }

  @Test
  @DisplayName("Parse all JSON files in directory recursive")
  void testParseAllJSONInDirRecursive() throws NotDirectoryException {
    ArrayList<JSONObject> objs = JSONLoader.parseAllJSONFiles(rootPath, true);
    assertEquals("[" + file3Contents + ", " + file1Contents + ", " + file2Contents + "]", objs.toString());
  }

  @Test
  @DisplayName("Parse all JSON files given a file, not a directory")
  void testParseAllJSONGivenFile()  {
    assertThrows(NotDirectoryException.class, ()->{
      JSONLoader.parseAllJSONFiles(file1Path, false);
    },  "exception was thrown for the base file not being a directory");
  }
}
