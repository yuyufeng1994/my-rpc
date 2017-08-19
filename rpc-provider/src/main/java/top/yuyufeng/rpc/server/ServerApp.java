package top.yuyufeng.rpc.server;


import top.yuyufeng.rpc.service.HelloService;
import top.yuyufeng.rpc.service.impl.HelloServiceImpl;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class ServerApp {
    public static void main(String[] args) throws Exception {
        RpcServer rpcServer = new RpcServerImpl(8989,5);
        rpcServer.register(HelloService.class.getName(),HelloServiceImpl.class);
        rpcServer.start();

    }
}
