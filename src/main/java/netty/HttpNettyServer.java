package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.kqueue.KQueueIoHandler;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpNettyServer {

    private static final int SO_BACKLOG = 2;

    public static void main(String[] args) throws Exception {

        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
        System.setProperty("org.slf4j.simpleLogger.log.io.netty", "debug");
        System.setProperty("org.slf4j.simpleLogger.showThreadName", "true");
        System.setProperty("org.slf4j.simpleLogger.showShortLogName", "true");

        // Force Netty to use SLF4J
        InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);

        // Optional: Enable leak detection
        System.setProperty("io.netty.leakDetection.level", "PARANOID");


        EventLoopGroup bossGroup = new MultiThreadIoEventLoopGroup(KQueueIoHandler.newFactory());
        EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(KQueueIoHandler.newFactory());
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(KQueueServerSocketChannel.class) // Use NIO for macOS
             .option(ChannelOption.SO_BACKLOG, SO_BACKLOG)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(new HttpServerCodec());
                     p.addLast(new HttpObjectAggregator(65536));
                     p.addLast(new SimpleChannelInboundHandler<FullHttpRequest>() {

                         @Override
                         public void channelActive(ChannelHandlerContext ctx) throws Exception {
                             log.info("accepted new connection from: {}", ctx.channel().remoteAddress());
                             super.channelActive(ctx);
                         }

                         @Override
                         protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) {
                             String body = req.content().toString(java.nio.charset.StandardCharsets.UTF_8);
                             log.info("Request URI: {}, Body: {}", req.uri(), body);

                             FullHttpResponse response = new DefaultFullHttpResponse(
                                     HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                             response.content().writeBytes("Hello, Netty HTTP!".getBytes());
                             response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                             response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
                             ctx.writeAndFlush(response);
                         }
                     });

                     p.addLast(new LoggingHandler(LogLevel.DEBUG));
                 }

                 @Override
                 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                     log.error("Exception caught in channel: {}", cause.getMessage());
                     throw new RuntimeException("Channel exception", cause);
                 }
             });


            bossGroup.scheduleAtFixedRate(() -> {
                try {
                    log.info("Boss thread is busy doing simulated work...");
                    // This sleep will block the single boss thread, preventing it from accepting connections.
                    Thread.sleep(5000); // 5 seconds of "work"
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, 1, 1, TimeUnit.SECONDS); // Start after 1 sec, repeat every 1 sec

            ChannelFuture f = b.bind(8081).sync();
            System.out.println("HTTP server started on port 8081");
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
