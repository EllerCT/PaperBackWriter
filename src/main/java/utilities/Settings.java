package utilities;

import java.io.*;
import java.util.Properties;

//TODO: Turn this into a singleton?
public class Settings {

    private static Properties paperBackProperties = new Properties();

    public static void load() {
        try {
            InputStream in = new FileInputStream("config.properties");
            paperBackProperties.load(in);
            in.close();
        } catch (FileNotFoundException e) {
            System.err.println("Settings file not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        save(null);
    }

    public static void save(String comments) {
        try {
            OutputStream out;
            if (!new File("config.properties)").exists()) {
                System.err.println("Creating new config file");
                new File("config.properties").createNewFile();
            }
            out = new FileOutputStream("config.properties");
            paperBackProperties.store(out, comments);
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
