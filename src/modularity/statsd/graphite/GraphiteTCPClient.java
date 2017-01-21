package modularity.statsd.graphite;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Garu on 09/12/2016.
 */
public class GraphiteTCPClient extends GraphiteClientAbstract {

    private Socket socket;

    private PrintWriter socketWriter;

    private ExecutorService executorService = Executors.newSingleThreadExecutor(DaemonThreadFactory.create());

    public GraphiteTCPClient(String host, String port, String prefix) {
        super(host, port, prefix);
        connect();
    }

    public GraphiteTCPClient() {
        super();
        connect();
    }

    private void connect() {
        try {
            this.socket = new Socket(getHost(), Integer.valueOf(getPort()));
            this.socketWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    void sendMessage(String message) {
        executorService.execute(() -> {
            socketWriter.print(message);
            socketWriter.flush();
        });
    }


    @Override
    public void close() {
        try {
            super.close();
            this.socketWriter.close();
            this.socket.close();
            executorService.shutdown();
        } catch (IOException ignored) {
        }
    }
}
