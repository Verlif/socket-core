package idea.verlif.socket.core.client;

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

    public ClientConfig ip(String ip) {
        this.ip = ip;
        return this;
    }

    /**
     * 服务端端口
     */
    private int port = 16508;

    public ClientConfig port(int port) {
        this.port = port;
        return this;
    }

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

}
