package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TestBacklogClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8081;
    private static final int NUMBER_OF_CONNECTIONS = 30; // More than the backlog of 10

    // Counters to track success and failure
    private static final AtomicInteger successCount = new AtomicInteger(0);
    private static final AtomicInteger failCount = new AtomicInteger(0);
    private static final CountDownLatch latch = new CountDownLatch(NUMBER_OF_CONNECTIONS);

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000) // Timeout if can't connect
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) {
                     // Simple handler
                 }
             });

            log.info("Attempting to create {} connections...", NUMBER_OF_CONNECTIONS);

            // Rapidly fire off many connection attempts
            for (int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
                final int connectionId = i;
                b.connect(HOST, PORT).addListener((ChannelFuture future) -> {
                    if (future.isSuccess()) {
                        log.info("Connection {} succeeded.", connectionId);
                        successCount.incrementAndGet();
                        // Close the connection immediately for this demo
                        future.channel().close();
                    } else {
                        log.error("Connection {} FAILED: {}", connectionId, future.cause().getMessage());
                        failCount.incrementAndGet();
                    }
                    latch.countDown();
                });
            }

            // Wait for all connection attempts to finish
            latch.await();

            // Print the results
            log.info("--- Final Results ---");
            log.info("Successful connections: {}", successCount.get());
            log.info("Failed connections: {}", failCount.get());

        } finally {
            group.shutdownGracefully();
        }
    }
}