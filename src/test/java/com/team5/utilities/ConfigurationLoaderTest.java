package com.team5.utilities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.json.simple.JSONObject;

public class ConfigurationLoaderTest {
    final String config1 = "iCare-template-system";
    final String config1_data = "{\"root-template-directory\":\"data\\/templates\\/iCare-templates\"}";

    @Test
    @DisplayName("Load existent configuration")
    void testLoadExistentConfiguration() throws ConfigurationNotFoundException {
        JSONObject c_data = ConfigurationLoader.loadConfiguration(config1);
        assertEquals(config1_data, c_data.toJSONString());
    }

    @Test
    @DisplayName("Load non-existent configuration")
    void testLoadNonExistentConfiguration() {
        assertThrows(ConfigurationNotFoundException.class, ()->{
            ConfigurationLoader.loadConfiguration("some-config-that-DNE");
        },  "exception was thrown for non-existent configuration");
    }

}