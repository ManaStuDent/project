package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 6000));

        if (!connect) {
            while (!socketChannel.finishConnect()) {

            }
        }

        ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());
        socketChannel.write(buffer);
        System.in.read();
    }
}
