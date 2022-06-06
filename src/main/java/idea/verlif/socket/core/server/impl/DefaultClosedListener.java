package idea.verlif.socket.core.server.impl;

import idea.verlif.socket.core.server.listener.ClosedListener;

import java.net.Socket;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/5/26 16:14
 */
public class DefaultClosedListener implements ClosedListener {
    @Override
    public void onClientClosed(Socket socket) {
    }
}
