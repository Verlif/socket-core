package idea.verlif.socket.core.server;

import idea.verlif.socket.core.server.holder.ClientHolder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 12:47
 */
public class Server {

    private final ServerConfig config;
    private ServerSocket server;

    private final List<ClientHolder> holders;

    public Server(ServerConfig config) {
        this.config = config;
        this.holders = new ArrayList<>();
    }

    public void init() throws IOException {
        synchronized (holders) {
            server = new ServerSocket(config.getPort());
            for (int i = 0; i < config.getMax(); i++) {
                ClientHolder holder = new ClientHolder(config.getHandler(), config.getTied());
                holders.add(holder);
            }
        }
    }

    public void start() throws IOException {
        if (server == null) {
            throw new IOException("Please call init() before start()!");
        }
        while (!server.isClosed()) {
            Socket socket = server.accept();
            boolean add = false;
            for (ClientHolder holder : holders) {
                if (holder.addClient(socket)) {
                    add = true;
                    break;
                }
            }
            if (!add) {
                config.getHandler().onRejected(socket);
            }
        }
    }
}
