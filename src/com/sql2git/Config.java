package com.sql2git;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Created by Alex on 25.10.2015.
 */
public class Config extends Common {
    public static String CONF_CONFIG_FILE = "config.json";
    public static String CONF_REPOSITORY_PATH;
    public static String CONF_REPOSITORY_PATH_LOCAL;
    public static String CONF_LOGGING_ENABLED;

    public void LoadConfig() {
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
            logFatalError("Не найден конфигурационный файл:\n" + CONF_CONFIG_FILE);
        } catch (UnsupportedEncodingException e) {
            logFatalError("Неподдерживаемая кодировка файла:\n" + CONF_CONFIG_FILE);
        } catch (IOException e) {
            logFatalError("Не удается прочитать файл:\n" + CONF_CONFIG_FILE);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject) parser.parse(json);
            CONF_REPOSITORY_PATH = (String) jsonObj.get("CONF_REPOSITORY_PATH");
            CONF_REPOSITORY_PATH_LOCAL = (String) jsonObj.get("CONF_REPOSITORY_PATH_LOCAL");
            CONF_LOGGING_ENABLED = (String) jsonObj.get("CONF_LOGGING_ENABLED");
            if("1".equals(CONF_LOGGING_ENABLED)) {
                Common.loggingEnabled = 1;
            }
        } catch (ParseException e) {
            logFatalError("Неправильный формат конфигурационного файла:\n" + e.toString());
        }
    }
}
