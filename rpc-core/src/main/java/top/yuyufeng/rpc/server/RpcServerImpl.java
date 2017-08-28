package top.yuyufeng.rpc.server;

import top.yuyufeng.rpc.exception.RpcException;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class RpcServerImpl implements RpcServer {
    private int nThreads = 10;
    private boolean isAlive = false;
    private int port = 8989;
    private ExecutorService executor;

    public RpcServerImpl() {
        init();
    }


    public RpcServerImpl(int port, int nThreads, String zookeeperHost) {
        this.port = port;
        this.nThreads = nThreads;
        RegisterServicesCenter.init(zookeeperHost, port);
        init();
    }

    public void init() {
        executor = Executors.newFixedThreadPool(nThreads);
    }

    @Override
    public void start() {
        isAlive = true;
        new MyServer(port).start();
    }

    @Override
    public void stop() {
        isAlive = false;
        executor.shutdown();
    }

    @Override
    public void register(String className, Class clazz) throws Exception {
        RegisterServicesCenter.addServices(className, clazz);
    }

    @Override
    public boolean isAlive() {
        String status = (this.isAlive == true) ? "RPC服务已经启动" : "RPC服务已经关闭";
        System.out.println(status);
        return this.isAlive;
    }
}
