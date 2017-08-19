package top.yuyufeng.rpc;


import top.yuyufeng.rpc.server.RpcServer;
import top.yuyufeng.rpc.server.RpcServerImpl;
import top.yuyufeng.rpc.service.HelloService;
import top.yuyufeng.rpc.service.impl.HelloServiceImpl;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class ServerApp {
    public static void main(String[] args) throws Exception {
        RpcServer rpcServer = new RpcServerImpl(8989,5);
        //暴露HelloService接口，具体实现为HelloServiceImpl
        rpcServer.register(HelloService.class.getName(),HelloServiceImpl.class);
        //启动rpc服务
        rpcServer.start();


    }
}
