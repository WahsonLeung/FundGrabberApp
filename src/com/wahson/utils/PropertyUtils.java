package com.wahson.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wahsonleung on 15/4/14.
 */
public class PropertyUtils {

    public static final String PROP_PATH = "src/resources/prop.properties";
    public static String getValue(String key) {
        Properties properties = new Properties();
        String val = null;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PROP_PATH));
            properties.load(in);
            in.close();
            val = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }
}
