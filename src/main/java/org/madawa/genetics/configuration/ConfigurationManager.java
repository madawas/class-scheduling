/*
 * Copyright (c) 2019 Madawa Soysa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.madawa.genetics.configuration;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConfigurationManager {
    private static final Logger log = Logger.getLogger(ConfigurationManager.class);
    private static AppConfig appConfig;
    private static AppData appData;

    private static void readAppConfiguration(String configPath) {
        Yaml yaml = new Yaml();
        try (InputStream in = Files.newInputStream(Paths.get(configPath))) {
            appConfig = yaml.loadAs(in, AppConfig.class);
        } catch (IOException e) {
            log.error("Error occurred when reading the config file.");
            JOptionPane.showMessageDialog(new JFrame(), "Error occurred when reading the config file.", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        log.info("Successfully read the application configurations");
    }

    /**
     * Reads appConfig from the given path
     *
     * @param configPath path to config file.
     * @return {@link AppConfig}
     */
    public static AppConfig getAppConfiguration(String configPath) {
        if (appConfig == null) {
            readAppConfiguration(configPath);
        }

        return appConfig;
    }

    public static AppData getAppData(String configPath) {
        if (appData == null) {
            readApplicationData(configPath);
            appData.setResourceIds();
        }

        return appData;
    }

    private static void readApplicationData(String configPath) {
        Yaml yaml = new Yaml();

        try (InputStream in = Files.newInputStream(Paths.get(configPath))) {
            appData = yaml.loadAs(in, AppData.class);
        } catch (IOException e) {
            log.error("Error occurred when reading the data file.");
            JOptionPane.showMessageDialog(new JFrame(), "Error occurred when reading the data file.", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        log.info("Successfully read the data file and generated objects.");
    }
}
