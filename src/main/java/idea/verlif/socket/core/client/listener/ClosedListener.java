package idea.verlif.socket.core.client.listener;

/**
 * Socket断开连接回调接口
 *
 * @author Verlif
 */
public interface ClosedListener {

    /**
     * 当连接断开是回调
     */
    void onClosed();
}
