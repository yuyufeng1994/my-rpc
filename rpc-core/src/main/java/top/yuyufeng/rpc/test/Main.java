package top.yuyufeng.rpc.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future future = new FutureTask(new Callable() {

            @Override
            public Object call() throws Exception {
                return "success";
            }
        });

        System.out.println(future.get());
    }
}
