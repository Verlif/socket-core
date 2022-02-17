package idea.verlif.socket.core.server.listener;

import java.net.Socket;

/**
 * 客户端断开连接时回调接口
 *
 * @author Verlif
 * @version 1.0
 * @date 2022/2/17 11:09
 */
public interface ClosedListener {

    /**
     * 当客户端连接断开时回调
     *
     * @param socket 断开的socket
     */
    void onClientClosed(Socket socket);
}
