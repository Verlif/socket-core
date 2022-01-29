package idea.verlif.socket.core.client;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 13:57
 */
public interface ReceiveHandler {

    /**
     * 当接收到数据时回调
     *
     * @param client  客户端套接字
     * @param message 接收到的数据
     */
    void receive(Client client, String message);
}
