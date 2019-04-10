package top.yuyufeng.rpc.test;

import top.yuyufeng.rpc.client.proxy.RemoteServiceImpl;
import top.yuyufeng.rpc.service.CalService;
import top.yuyufeng.rpc.service.HelloService;
import top.yuyufeng.rpc.service.top.yuyufeng.dto.MyResult;

/**
 * 服务调用 （客户端）
 * @date 2017/8/19.
 * @author yuyufeng
 */
public class ClientApp {
    public static void main(String[] args) {
        //设置Zookeeper的地址
        RemoteServiceImpl.setZookeeperAddress("127.0.0.1:2181");

        //获取动态代理的HelloService的“真实对象（其实内部不是真实的，被换成了调用远程方法）”
        HelloService helloService = RemoteServiceImpl.newRemoteProxyObject(HelloService.class);
        String result = helloService.sayHello("yyf");
        System.out.println(result);

        CalService calService = RemoteServiceImpl.newRemoteProxyObject(CalService.class);
        MyResult myResult = calService.getResult(1, 2);
        System.out.println(myResult);

        myResult = calService.getResult(3, 2);
        System.out.println(myResult);

        /*//启动10个线程去请求
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        String result = helloService.sayHello("yyf");
                        System.out.println(result);
                        MyResult myResult = calService.getResult(1,2);
                        System.out.println(myResult);
                    }
                }
            }.start();
        }*/
    }
}
