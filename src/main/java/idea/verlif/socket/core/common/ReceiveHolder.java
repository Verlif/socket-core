package idea.verlif.socket.core.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/1/28 13:49
 */
public abstract class ReceiveHolder implements Runnable {

    private final InputStream stream;
    private boolean available;

    public ReceiveHolder(InputStream stream) {
        this.stream = stream;
        this.available = true;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(stream);
        try {
            while (available) {
                receive(scanner.nextLine());
            }
        } catch (Exception ig) {
            try {
                close();
            } catch (IOException ignored) {
            }
        }
    }

    public void close() throws IOException {
        available = false;
        stream.close();
    }

    /**
     * 当接收到数据时回调
     *
     * @param message 接收到的数据
     */
    public abstract void receive(String message);
}
