package modularity.statsd.graphite;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Garu on 04/01/2017.
 */
public class EProperties {

    public static EProperties from(String file) {
        return new EProperties(file);
    }

    private Properties properties;

    public EProperties(String file) {
        this.properties = new Properties();
        this.load(file);
    }

    private void load(String file) {
        try {
            InputStream input = new FileInputStream(file);
            this.properties.load(input);
        } catch (IOException ex) {
            throw new IllegalArgumentException(String.format("Invalid properties %s, not found", file));
        }
    }

    public String get(String key) {
        if (properties.containsKey(key))
            return properties.getProperty(key);
        else
            throw new NullPointerException(String.format("Key %s not found", key));
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getSize() {
        return properties.size();
    }

}
