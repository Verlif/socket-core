package idea.verlif.socket.core.client;

import idea.verlif.socket.core.common.ReceiveHolder;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
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
            0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(8),
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });

    protected final Socket client;
    protected final ClientConfig config;
    protected PrintStream ps;
    protected ReceiveHolder handler;

    public Client(ClientConfig config) {
        this.client = new Socket();
        this.config = config;
    }

    /**
     * 连接远程服务器
     *
     * @return 是否连接成功
     */
    public boolean connect() {
        try {
            this.client.connect(new InetSocketAddress(config.getIp(), config.getPort()));
            ps = new PrintStream(this.client.getOutputStream());
            handler = new ReceiveHolder(this.client) {

                @Override
                public void onClosed(Socket socket) {
                    config.getClosedListener().onClosed();
                }

                @Override
                public void receive(String message) {
                    config.getReceiveHandler().receive(Client.this, message);
                }
            };
            EXECUTOR.execute(handler);
            config.getConnectedListener().onConnected(this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            close();
            return false;
        }
    }

    public Socket getClient() {
        return client;
    }

    public void sendMessage(String message) {
        ps.println(message);
        ps.flush();
    }

    public boolean isConnected() {
        return !client.isClosed() && client.isConnected();
    }

    public void close() {
        if (ps != null) {
            ps.close();
        }
        try {
            client.close();
            if (handler != null) {
                handler.close();
            }
        } catch (IOException ignored) {
        }
    }
}
