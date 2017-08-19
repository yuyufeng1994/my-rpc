package top.yuyufeng.rpc.client;

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
    }
}
