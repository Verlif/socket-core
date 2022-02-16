package idea.verlif.socket.core.client;

/**
 * 当Socket连接上时回调
 *
 * @author Verlif
 */
public interface ConnectedListener {

    /**
     * 当连接创建时
     *
     * @param client 连接的客户端对象
     */
    void onConnected(Client client);
}
