package modularity.statsd.graphite;


import java.util.logging.Logger;

/**
 * Created by Garu on 08/12/2016.
 */
public abstract class GraphiteClientAbstract {

    private final Logger logger = Logger.getLogger("Graphite");

    private String host;

    private String port;

    private String prefix;

    public GraphiteClientAbstract(String host, String port, String prefix) {
        this.host = host;
        this.port = port;
        this.prefix = prefix;
    }

    public GraphiteClientAbstract() {
        EProperties prop = EProperties.from("statsd.properties");
        this.host = prop.get("host");
        this.port = prop.get("port");
        this.prefix = prop.get("prefix", "graphy");
    }

    public void increment(String key) {
        this.send(key, 1);
    }

    public void decrement(String key) {
        this.send(key, -1);
    }

    public void set(String key, Number value) {
        this.send(key, value);
    }

    private void send(String key, Number value) {
        this.sendMessage(getMessage(key, value));
    }

    abstract void sendMessage(String message);

    private String getKey(CharSequence keys) {
        if (prefix.isEmpty())
            return keys.toString();
        return String.format("%s.%s", getPrefix(), String.join(".", keys));
    }

    protected String getMessage(String key, Number value) {
        return String.format("%s %s %d%n", getKey(key), value, System.currentTimeMillis() / 1000);
    }


    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getPrefix() {
        return prefix;
    }

    public void close() {
    }

    protected void log(String message) {
        logger.info(message);
    }
}
