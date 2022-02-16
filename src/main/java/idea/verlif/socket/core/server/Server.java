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

    protected final ServerConfig config;
    protected ServerSocket server;

    protected final List<ClientHolder> holders;

    public Server(ServerConfig config) {
        this.config = config;
        this.holders = new ArrayList<>();
    }

    /**
     * 服务端初始化，此时会加载配置。<br/>
     * 需要修改配置，请在init前修改。
     *
     * @throws IOException 当服务器端口被占用或是
     */
    public void init() throws IOException {
        synchronized (holders) {
            holders.clear();
            for (int i = 0; i < config.getMax(); i++) {
                ClientHolder holder = new ClientHolder(config.getHandler(), config.getTied());
                holders.add(holder);
            }
        }
    }

    public void start() throws IOException {
        server = new ServerSocket(config.getPort());
        if (holders.size() == 0) {
            throw new IOException("Please call init() before start()!");
        }
        while (!server.isClosed()) {
            Socket socket = server.accept();
            boolean add = false;
            for (ClientHolder holder : holders) {
                ClientHolder.ClientHandler handler = holder.addClient(socket);
                if (handler != null) {
                    config.getHandler().onClientConnected(handler);
                    add = true;
                    break;
                }
            }
            if (!add) {
                config.getHandler().onRejected(socket);
            }
        }
    }

    public void stop() throws IOException {
        server.close();
    }

    public ServerConfig getConfig() {
        return config;
    }

}
