//package netty;
//
//import io.netty.channel.ChannelMetadata;
//import io.netty.channel.kqueue.AbstractKQueueServerChannel;
//import io.netty.channel.kqueue.KQueueServerSocketChannelConfig;
//import io.netty.util.internal.logging.InternalLogger;
//import io.netty.util.internal.logging.InternalLoggerFactory;
//
//import java.net.SocketAddress;
//
//public class DebuggableKQueueServerSocketChannel extends AbstractKQueueServerChannel {
//
//    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DebuggableKQueueServerSocketChannel.class);
//    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
//    private final KQueueServerSocketChannelConfig config;
//
//    public DebuggableKQueueServerSocketChannel() {
//        super(newSocket(), false);
//        this.config = new KQueueServerSocketChannelConfig(this);
//        logger.debug("Created new DebuggableKQueueServerSocketChannel");
//    }
//
//    private static int newSocket() {
//        try {
//            // Use reflection as a last resort since BsdSocket isn't public
//            Class<?> bsdSocketClass = Class.forName("io.netty.channel.kqueue.BsdSocket");
//            return (int) bsdSocketClass.getMethod("newSocketStream").invoke(null);
//        } catch (Exception e) {
//            throw new IllegalStateException("Unable to create kqueue socket", e);
//        }
//    }
//
//    @Override
//    protected void doBind(SocketAddress localAddress) throws Exception {
//        logger.debug("Attempting to bind to {}", localAddress);
//        long startTime = System.nanoTime();
//        super.doBind(localAddress);
//        long duration = System.nanoTime() - startTime;
//        logger.info("Successfully bound to {} in {} ns", localAddress, duration);
//    }
//
//    @Override
//    protected void doClose() throws Exception {
//        logger.debug("Closing channel (open={})", isOpen());
//        if (isOpen()) {
//            logger.debug("Current local address: {}", localAddress());
//        }
//        super.doClose();
//        logger.info("Channel closed");
//    }
//
//    // Add more overridden methods as needed for debugging
//
//    @Override
//    public KQueueServerSocketChannelConfig config() {
//        return config;
//    }
//
//    @Override
//    public ChannelMetadata metadata() {
//        return METADATA;
//    }
//
//    // Add this method to debug event loop registration
//    @Override
//    protected void doRegister() throws Exception {
//        logger.debug("Registering channel to event loop");
//        super.doRegister();
//        logger.debug("Registration complete. EventLoop: {}", eventLoop());
//    }
//}