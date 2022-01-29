package idea.verlif.socket.core.server;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 12:48
 */
public class ServerConfig {

    /**
     * 套接字端口
     */
    private int port = 16508;

    public ServerConfig port(int port) {
        this.port = port;
        return this;
    }

    /**
     * 最大处理器数量
     */
    private int max = 2;

    public ServerConfig max(int max) {
        this.max = max;
        return this;
    }

    /**
     * 多少个连接共用一个处理器
     */
    private int tied = 1;

    public ServerConfig tied(int tied) {
        this.tied = tied;
        return this;
    }

    /**
     * @see SocketHandler
     */
    private SocketHandler handler = (client, message) -> {
    };

    public ServerConfig handler(SocketHandler handler) {
        this.handler = handler;
        return this;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getTied() {
        return tied;
    }

    public void setTied(int tied) {
        if (tied > 0) {
            this.tied = tied;
        }
    }

    public SocketHandler getHandler() {
        return handler;
    }

    public void setHandler(SocketHandler handler) {
        this.handler = handler;
    }
}
