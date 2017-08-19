package top.yuyufeng.rpc.test;

import top.yuyufeng.rpc.client.proxy.RemoteServiceImpl;
import top.yuyufeng.rpc.service.HelloService;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class ClientApp {
    public static void main(String[] args) {
        HelloService helloService = RemoteServiceImpl.newRemoteProxyObject(HelloService.class);
        String result = helloService.sayHello("yyf");
        System.out.println(result);
        //启动10个线程去请求
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        String result = helloService.sayHello("yyf");
                        System.out.println(result);
                    }
                }
            }.start();

        }

    }
}
