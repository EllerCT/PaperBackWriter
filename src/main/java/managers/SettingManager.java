package managers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class SettingManager {

    private static Properties paperBackProperties = new Properties();

    public static void load() {
        try {
            InputStream in = SettingManager.class.getClassLoader().getResourceAsStream("config.properties");
            paperBackProperties.load(in);
            in.close();
        } catch (IOException e) {
            System.err.println("Loading settings failed.");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            OutputStream out = new FileOutputStream(SettingManager.class.getClassLoader().getResource("config.properties").getFile());
            paperBackProperties.store(out, null);
            out.close();
        } catch (IOException e) {
            System.err.println("Failed to write settings.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Settings file not found.");
            e.printStackTrace();
        }
    }

    public static String read(String name, String defaultValue) {
        if (defaultValue == null || defaultValue.isBlank()) {
            return paperBackProperties.getProperty(name);
        }
        return paperBackProperties.getProperty(name, defaultValue);
    }

    public static void store(String name, String value) {
        paperBackProperties.setProperty(name, value);
    }

}
