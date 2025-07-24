package tokenbucket;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class TokenBucket {

    public static void main(String[] args) throws InterruptedException {
        Bucket bucket = new Bucket(100L);
        Router router = new Router(bucket);
        router.start();
        int numOfThreads = 2;
        Thread[] arr = new Thread[numOfThreads];
        for (int i = 0; i < 2; i++) {
            Thread requestThread = new Thread(() -> {
                simulateSendingRequest(router);
            });
            requestThread.start();
            arr[i] = requestThread;
        }
        for (int i = 0; i < args.length; i++) {
            arr[i].join();
        }
    }

    private static void simulateSendingRequest(Router router) {
        while (true) {
            try {
                router.processRequest("request");
                Thread.sleep(2);
            } catch (Exception ex) {
                log.error("unexpected error", ex);
            }
        }
    }

    @Slf4j
    static class Router {

        private Bucket bucket;
        private ScheduledExecutorService executor;

        private static final int TokenRate = 50;
        private static final int RequestToken = 1;

        public Router(Bucket bucket) {
            this.bucket = bucket;
        }

        public void start() {
            this.executor = Executors.newScheduledThreadPool(1);

            this.executor.scheduleAtFixedRate(
                    this::feedTokens,
                    0,
                    100,
                    TimeUnit.MILLISECONDS
            );
        }

        public void feedTokens() {
            if (!bucket.receiveTokens(TokenRate)) {
                log.warn("token is full capacity");
            }
        }

        public void processRequest(String request) {
            boolean success = bucket.retrieveTokens(RequestToken);
            if (!success) {
                log.warn("limit is reached, ignore request");
            }
        }
    }



    static class Bucket {
        Long tokens;
        Long maxCapacity;

        public Bucket(Long maxCapacity) {
            this.tokens = 0L;
            this.maxCapacity = maxCapacity;
        }

        public synchronized boolean retrieveTokens(int size) {
            if (tokens < size) {
                return false;
            }
            this.tokens -= size;
            return true;
        }

        public synchronized boolean receiveTokens(int size) {
            long l = tokens + size;
            if (l > maxCapacity) {
                return false;
            }
            this.tokens = l;
            return true;
        }
    }
}
