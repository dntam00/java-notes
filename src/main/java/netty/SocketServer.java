package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.kqueue.KQueueIoHandler;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;

@Slf4j
public class SocketServer {

    public static void main(String[] args) throws Exception {
        int port = 8082;

        EventLoopGroup bossGroup = new MultiThreadIoEventLoopGroup(1, KQueueIoHandler.newFactory());
        IoHandlerFactory ioHandlerFactory = KQueueIoHandler.newFactory();

        ThreadFactory workerThreadFactory = r -> {
            Thread t = new Thread(r);
            t.setName("kaixin-worker-thread-" + t.getId());
            return t;
        };

        EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(20, workerThreadFactory, ioHandlerFactory);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(KQueueServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(new HttpServerCodec());
                     p.addLast(new HttpObjectAggregator(65536));
                     p.addLast(new WebSocketServerProtocolHandler("/ws"));
                     p.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
                         @Override
                         protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
                             String text = msg.text();
                             log.info("Received WebSocket message: {}", text);
                             ctx.writeAndFlush(new TextWebSocketFrame("Echo: " + text));
                         }

                         @Override
                         public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                             log.error("Exception: ", cause);
                             ctx.close();
                         }
                     });
                 }
             });

            ChannelFuture f = b.bind(port).sync();
            log.info("WebSocket server started on port {}", port);
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
