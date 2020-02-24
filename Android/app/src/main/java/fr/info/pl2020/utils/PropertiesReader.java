package fr.info.pl2020.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private PropertiesReader(){}

    public static String getProperty(String key, Context context) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream is = assetManager.open("app.properties");
        properties.load(is);
        return properties.getProperty(key);
    }
}
