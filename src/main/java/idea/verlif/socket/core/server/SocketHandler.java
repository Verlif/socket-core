package idea.verlif.socket.core.server;

import idea.verlif.socket.core.server.holder.ClientHolder;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 13:13
 */
public interface SocketHandler {

    /**
     * 当服务器连接数到达最大值时的拒绝策略
     *
     * @param socket 被拒绝的连接
     */
    default void onRejected(Socket socket) throws IOException {
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.println("the server is overload!");
        ps.flush();
        ps.close();
        socket.close();
    }

    /**
     * 当接收到数据时回调
     *
     * @param client  客户端套接字
     * @param message 接收到的数据
     */
    void receive(ClientHolder.ClientHandler client, String message);
}
