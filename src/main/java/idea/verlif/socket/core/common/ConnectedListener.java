package idea.verlif.socket.core.common;

import java.net.Socket;

/**
 * 当Socket连接上时回调
 *
 * @author Verlif
 */
public interface ConnectedListener {

    /**
     * 当连接创建时
     *
     * @param socket 连接到的套接字对象
     */
    void onConnected(Socket socket);

}
