package idea.verlif.socket.core.client;

/**
 * 接收服务端消息接口
 *
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 13:57
 */
public interface ReceiveHandler {

    /**
     * 当接收到数据时回调
     *
     * @param client  客户端对象
     * @param message 接收到的数据
     */
    void receive(Client client, String message);
}
