package top.yuyufeng.rpc.test;

import java.util.concurrent.*;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future future = executor.submit(new Callable(){
            @Override
            public Object call() throws Exception {
                Thread.sleep(2000);
                System.out.println("Main.call");
                return "success";
            }
        });

        try {
            System.out.println(future.get(1000,TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
            System.out.println("超时...");
            future.cancel(true);
        }
        executor.shutdown();;
    }
}
