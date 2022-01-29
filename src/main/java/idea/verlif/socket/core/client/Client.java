package idea.verlif.socket.core.client;

import idea.verlif.socket.core.ReceiveHolder;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 13:03
 */
public class Client {

    public static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            1, 2,
            1, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(8),
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(false);
                return thread;
            });

    private final Socket client;
    private final PrintStream ps;
    private final ReceiveHolder handler;

    public Client(ClientConfig config) throws IOException {
        this.client = new Socket(config.getIp(), config.getPort());

        ps = new PrintStream(this.client.getOutputStream());
        handler = new ReceiveHolder(this.client.getInputStream()) {
            @Override
            public void receive(String message) {
                config.getReceiveHandler().receive(Client.this, message);
            }
        };
    }

    public void connect() {
        EXECUTOR.execute(handler);
    }

    public void sendMessage(String message) {
        ps.println(message);
        ps.flush();
    }

    public boolean isConnected() {
        return client.isConnected() && !client.isClosed();
    }

    public void close() {
        ps.close();
        try {
            client.close();
            handler.close();
        } catch (IOException ignored) {
        }
    }

}
