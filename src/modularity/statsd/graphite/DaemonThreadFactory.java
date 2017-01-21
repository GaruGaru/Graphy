package modularity.statsd.graphite;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {
    public static DaemonThreadFactory create() {
        return new DaemonThreadFactory();
    }

    private DaemonThreadFactory (){
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}