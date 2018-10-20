package com.team5.utilities;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Responsible for loading configurations for the internal systems.
 */
public final class ConfigurationLoader {
    /**
     * The path to the master configuration file.
     */
    private static final String MASTER_CONFIG_FILE_PATH = "data/configuration/config.json";

    /**
     * The latest reference to the master configuration file.
     */
    private static JSONObject masterConfigFile = null;

    /**
     * Loads an internal project-configuration from the master configuration file.
     * 
     * @param configurationName The name of the project configuration.
     * @return Returns a JSONObject of the project configuration.
     * @throws ConfigurationNotFoundException Thrown if the project configuration is not found.
     */
    public static JSONObject loadConfiguration (String configurationName) throws ConfigurationNotFoundException {
        JSONObject mc = getMasterConfigFile(false); // Get a reference to the master config file

        Object systemConfig_obj = mc.get(configurationName); // Attempt to get the configuration name form the config file

        // If the configuration is not present then throw an expection
        if (systemConfig_obj == null)
            throw new ConfigurationNotFoundException();

        return (JSONObject) systemConfig_obj;
    }

    /**
     * Gets a reference to the master configuration file.
     * 
     * @param overrideExistingReference A flag indicating if any existing reference of the configuration file should be overrided.
     * @return Returns a JSONObject reference to the configuration file.
     */
    private static JSONObject getMasterConfigFile(boolean overrideExistingReference) {
        // In the case that no reference to the master config file exists or if the override existence reference flag is true
        if (masterConfigFile == null || overrideExistingReference) {
            try {
                // Attempt to get the master configuration file
                masterConfigFile = JSONLoader.parseJSONFile(MASTER_CONFIG_FILE_PATH);
            } catch (IOException | ParseException e) { /* Do not update config file */ }
        }
        return masterConfigFile;
    }

    /**
     * Updates the internal reference to the master configuration file. Useful if the file has been changed.
     */
    public static void reloadMasterConfigurationFile() {
        getMasterConfigFile(true);
    }
}