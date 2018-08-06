package org.iit.genetics.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigurationManager {
    private static AppConfig appConfig;

    private static void readAppConfiguration(String configPath) {
        Yaml yaml = new Yaml();
        try (InputStream in = Files.newInputStream(Paths.get(configPath))) {
            appConfig = yaml.loadAs(in, AppConfig.class);
        } catch (IOException e) {
//            JOptionPane.showMessageDialog(new JFrame(),
//                    "Error occurred when reading the config file.", "Dialog",
//                    JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
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
}
