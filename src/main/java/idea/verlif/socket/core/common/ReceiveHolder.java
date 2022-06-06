package idea.verlif.socket.core.common;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 13:49
 */
public abstract class ReceiveHolder implements Runnable {

    protected final Socket socket;
    protected boolean available;

    public ReceiveHolder(Socket socket) {
        this.socket = socket;
        this.available = true;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (available && !socket.isClosed()) {
                receive(scanner.nextLine());
            }
        } catch (Exception ig) {
            try {
                close();
            } catch (IOException ignored) {
            }
        }
        onClosed(socket);
    }

    public void close() throws IOException {
        available = false;
        socket.close();
    }

    /**
     * 当连接关闭时回调
     *
     * @param socket 被关闭的socket
     */
    public void onClosed(Socket socket) {

    }

    /**
     * 当接收到数据时回调
     *
     * @param message 接收到的数据
     */
    public abstract void receive(String message);
}
