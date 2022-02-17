package idea.verlif.socket.core.server.listener;

import idea.verlif.socket.core.server.holder.ClientHolder;

/**
 * 客户端连接时回调接口
 *
 * @author Verlif
 * @version 1.0
 * @date 2022/2/17 11:07
 */
public interface ConnectedListener {

    /**
     * 当客户端连接成功时回调
     *
     * @param handler 可用的客户端处理器
     */
    void onClientConnected(ClientHolder.ClientHandler handler);
}
