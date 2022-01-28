package idea.verlif.socket.command.server.holder;

import idea.verlif.socket.command.ReceiveHolder;
import idea.verlif.socket.command.server.SocketHandler;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 客户端连接处理器
 *
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 13:04
 */
public class ClientHolder {

    private final ThreadPoolExecutor executor;

    /**
     * 当前管理器可容纳的客户连接最大值。
     */
    private final int max;
    private final List<ClientHandler> clientList;
    private final SocketHandler handler;

    public ClientHolder(SocketHandler handler, int max) {
        this.clientList = new ArrayList<>();
        this.handler = handler;
        this.max = max;

        executor = new ThreadPoolExecutor(
                max / 2, max,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8), (ThreadFactory) Thread::new);
    }

    public synchronized boolean addClient(Socket client) {
        recycle();
        if (clientList.size() >= max) {
            return false;
        }
        try {
            ClientHandler handler = new ClientHandler(client, this.handler);
            clientList.add(handler);
            executor.execute(handler);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getMax() {
        return max;
    }

    public synchronized void recycle() {
        List<ClientHandler> list = new ArrayList<>(clientList);
        for (ClientHandler handler : list) {
            if (handler.getClient() == null || handler.getClient().isClosed()) {
                clientList.remove(handler);
            }
        }
    }

    public static class ClientHandler implements Runnable {

        private final Socket client;
        private final PrintStream ps;
        private final ReceiveHolder receive;

        public ClientHandler(Socket client, SocketHandler handler) throws IOException {
            this.client = client;

            ps = new PrintStream(client.getOutputStream());
            receive = new ReceiveHolder(client.getInputStream()) {
                @Override
                public void receive(String message) {
                    handler.receive(ClientHandler.this, message);
                }
            };
        }

        public void sendMessage(String message) {
            ps.println(message);
            ps.flush();
        }

        public void close() throws IOException {
            receive.close();
            ps.close();
            client.close();
        }

        public Socket getClient() {
            return client;
        }

        @Override
        public void run() {
            receive.run();
        }
    }
}
