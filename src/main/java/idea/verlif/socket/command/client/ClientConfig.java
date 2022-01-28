package idea.verlif.socket.command.client;

import idea.verlif.socket.command.ReceiveHolder;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 13:42
 */
public class ClientConfig {

    /**
     * 服务端IP地址
     */
    private String ip = "127.0.0.1";

    /**
     * 服务端端口
     */
    private int port = 16508;

    /**
     * 信息处理接口
     */
    private ReceiveHandler receiveHandler = (client, message) -> {
    };

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ReceiveHandler getReceiveHandler() {
        return receiveHandler;
    }

    public void setReceiveHandler(ReceiveHandler receiveHandler) {
        this.receiveHandler = receiveHandler;
    }
}
