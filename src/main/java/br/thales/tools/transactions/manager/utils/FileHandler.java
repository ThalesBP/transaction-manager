package br.thales.tools.transactions.manager.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static br.thales.tools.transactions.manager.utils.Constants.RESOURCE_PATH;

public class FileHandler {

    public Properties getProperties(String name) {
        try (InputStream input = new FileInputStream(RESOURCE_PATH + name)) {

            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            System.out.println("Fail to load properties file: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
