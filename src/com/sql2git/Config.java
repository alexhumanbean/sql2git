package com.sql2git;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;

/**
 * Created by Alex on 25.10.2015.
 */
public class Config {
    public static String CONF_CONFIG_FILE = "config.json";
    public static String CONF_REPOSITORY_PATH;
    public static String CONF_WORKING_DIR;

    public static void LoadConfig() {
        String json = "";
        try {
            File file = new File(CONF_CONFIG_FILE);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF-8"
                    )
            );
            String line;
            while ((line = br.readLine()) != null) {
                json += line;
            }
            br.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Не найден конфигурационный файл:\n" + CONF_CONFIG_FILE);
            System.exit(0);
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, "Неподдерживаемая кодировка файла:\n" + CONF_CONFIG_FILE);
            System.exit(0);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удается прочитать файл:\n" + CONF_CONFIG_FILE);
            System.exit(0);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject) parser.parse(json);
            CONF_REPOSITORY_PATH = (String) jsonObj.get("CONF_REPOSITORY_PATH");
            CONF_WORKING_DIR = (String) jsonObj.get("CONF_WORKING_DIR");
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Неправильный формат конфигурационного файла:\n" + e.toString());
            System.exit(0);
        }
    }
}
