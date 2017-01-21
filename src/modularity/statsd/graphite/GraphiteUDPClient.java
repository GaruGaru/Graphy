package modularity.statsd.graphite;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Garu on 08/12/2016.
 */
public class GraphiteUDPClient extends GraphiteClientAbstract {

    private ExecutorService executorService = Executors.newSingleThreadExecutor(DaemonThreadFactory.create());
    private DatagramSocket datagramSocket;

    private InetAddress hostAddress;

    public GraphiteUDPClient() {
        super();
        try {
            this.hostAddress = InetAddress.getByName(getHost());
            this.datagramSocket = new DatagramSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void sendMessage(String message) {
        executorService.execute(() -> {
            try {
                datagramSocket.send(createPacket(message + "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private DatagramPacket createPacket(String messageString) {
        byte[] message = messageString.getBytes();
        return new DatagramPacket(message, message.length, hostAddress, Integer.parseInt(getPort()));
    }

    @Override
    public void close() {
        super.close();
        datagramSocket.close();
        this.executorService.shutdown();
    }
}
