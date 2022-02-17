package idea.verlif.socket.core.server;

import idea.verlif.socket.core.server.listener.ClosedListener;
import idea.verlif.socket.core.server.listener.ConnectedListener;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 12:48
 */
public class ServerConfig {

    /**
     * 套接字端口
     */
    protected int port = 16508;

    public ServerConfig port(int port) {
        this.port = port;
        return this;
    }

    /**
     * 最大处理器数量
     */
    protected int max = 2;

    public ServerConfig max(int max) {
        this.max = max;
        return this;
    }

    /**
     * 多少个连接共用一个处理器
     */
    protected int tied = 1;

    public ServerConfig tied(int tied) {
        this.tied = tied;
        return this;
    }

    /**
     * @see SocketHandler
     */
    private SocketHandler handler = (client, message) -> {
    };

    /**
     * @see ConnectedListener
     */
    private ConnectedListener connectedListener = handler1 -> {};

    /**
     * @see ClosedListener
     */
    private ClosedListener closedListener = socket -> {};

    public ServerConfig handler(SocketHandler handler) {
        this.handler = handler;
        return this;
    }

    public ServerConfig connectListener(ConnectedListener listener) {
        this.connectedListener = listener;
        return this;
    }

    public ServerConfig closedListener(ClosedListener listener) {
        this.closedListener = listener;
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
        this.max = Math.max(max, 1);
    }

    public int getTied() {
        return tied;
    }

    public void setTied(int tied) {
        this.tied = Math.max(tied, 1);
    }

    public SocketHandler getHandler() {
        return handler;
    }

    public void setHandler(SocketHandler handler) {
        this.handler = handler;
    }

    public ConnectedListener getConnectedListener() {
        return connectedListener;
    }

    public void setConnectedListener(ConnectedListener connectedListener) {
        this.connectedListener = connectedListener;
    }

    public ClosedListener getClosedListener() {
        return closedListener;
    }

    public void setClosedListener(ClosedListener closedListener) {
        this.closedListener = closedListener;
    }
}
