package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NonblockingIO {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(8088));
        server.configureBlocking(false); // non-blocking mode

        System.out.println("Server listening on port 8088...");

        while (true) {
            SocketChannel client = server.accept(); // returns immediately
            if (client != null) {
                System.out.println("Client connected: " + client.getRemoteAddress());
                client.configureBlocking(false);

                new Thread(() -> {
                    while (true) {
                        try {
                            ByteBuffer buffer = ByteBuffer.allocate(256);
                            int bytesRead = client.read(buffer);
                            if (bytesRead > 0) {
                                buffer.flip();
                                String message = new String(buffer.array(), 0, buffer.limit());
                                System.out.println("Received: " + message);
                                client.write(ByteBuffer.wrap(("Echo: " + message).getBytes()));
                                client.close();
                                break;
                            }
                            System.out.printf("after read\n");
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }).start();

//                ByteBuffer buffer = ByteBuffer.allocate(256);
//                int bytesRead = client.read(buffer); // also returns immediately
//                if (bytesRead > 0) {
//                    buffer.flip();
//                    String message = new String(buffer.array(), 0, buffer.limit());
//                    System.out.println("Received: " + message);
//                    client.write(ByteBuffer.wrap(("Echo: " + message).getBytes()));
//                    client.close();
//                }
            }

            // do other work here without blocking
        }
    }
}