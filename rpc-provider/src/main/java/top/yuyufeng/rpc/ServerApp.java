package top.yuyufeng.rpc;


import top.yuyufeng.rpc.server.RpcServer;
import top.yuyufeng.rpc.server.RpcServerImpl;
import top.yuyufeng.rpc.service.CalService;
import top.yuyufeng.rpc.service.HelloService;
import top.yuyufeng.rpc.service.impl.CalServiceImpl;
import top.yuyufeng.rpc.service.impl.HelloServiceImpl;

/**
 * 服务启动
 * @date 2017/8/19.
 * @author yuyufeng
 */
public class ServerApp {
    public static void main(String[] args) throws Exception {
        RpcServer rpcServer = new RpcServerImpl(7878,5,"127.0.0.1:2181",true);
        //暴露HelloService接口，具体实现为HelloServiceImpl
        rpcServer.register(HelloService.class.getName(),HelloServiceImpl.class);
        rpcServer.register(CalService.class.getName(),CalServiceImpl.class);
        //启动rpc服务
        rpcServer.start();
    }
}
