package concurrency;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {

        final ThreadPoolTest threadPoolTest = new ThreadPoolTest();

        // 创建8个线程

        for (int i = 0; i < 16; i++) {

            new Thread(() -> {

                while (true) {

                    // 获取一个任务

                    Future<String> future = threadPoolTest.submit();

                    try {

                        String s = future.get();

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    } catch (ExecutionException e) {

                        e.printStackTrace();

                    } catch (Error e) {

                        e.printStackTrace();

                    }

                }

            }).start();

        }


        //子线程不停gc，模拟偶发的gc

        new Thread(() -> {

            while (true) {

                System.gc();

            }

        }).start();

    }


    /**
     * 异步执行任务
     *
     * @return
     */

    public Future<String> submit() {

        //关键点，通过Executors.newSingleThreadExecutor创建一个单线程的线程池

        // PS：注意是单线程的线程池

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        FutureTask<String> futureTask = new FutureTask<>(() -> {

            Thread.sleep(50);

            return System.currentTimeMillis() + "";

        });

        // 执行异步任务

        executorService.execute(futureTask);

        return futureTask;

    }
}
