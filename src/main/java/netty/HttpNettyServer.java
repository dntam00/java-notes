package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpNettyServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new KQueueEventLoopGroup();
        EventLoopGroup workerGroup = new KQueueEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(KQueueServerSocketChannel.class) // Use NIO for macOS
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(new HttpServerCodec());
                     p.addLast(new HttpObjectAggregator(65536));
                     p.addLast(new SimpleChannelInboundHandler<FullHttpRequest>() {
                         @Override
                         protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) {

                             log.info("receive request: {}", req.uri());

                             FullHttpResponse response = new DefaultFullHttpResponse(
                                     HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                             response.content().writeBytes("Hello, Netty HTTP!".getBytes());
                             response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                             response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
                             ctx.writeAndFlush(response);
                         }
                     });
                 }
             });

            ChannelFuture f = b.bind(8081).sync();
            System.out.println("HTTP server started on port 8081");
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
