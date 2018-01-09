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
    private MyServer myServer;

    public RpcServerImpl() {
        init();
    }


    public RpcServerImpl(int port, int nThreads, String zookeeperHost, Boolean isStart) {
        this.port = port;
        this.nThreads = nThreads;
        RegisterServicesCenter.init(zookeeperHost, port);
        init();
        if (isStart.equals(Boolean.TRUE)) {
            start();
        }
    }

    public void init() {

    }

    @Override
    public void start() {
        System.out.println("开始启动Rpc服务");
        new Thread() {
            @Override
            public void run() {
                isAlive = true;
                myServer = new MyServer(port, nThreads);
                myServer.start();
            }
        }.start();

    }

    @Override
    public void stop() {
        isAlive = false;
        myServer.shutdown();
    }

    @Override
    public void register(String className, Class clazz) throws Exception {
        RegisterServicesCenter.addServices(className, clazz);
        System.out.println(className + " " + clazz);
        System.out.println("注册服务:" + className);
    }

    @Override
    public boolean isAlive() {
        String status = (this.isAlive == true) ? "RPC服务已经启动" : "RPC服务已经关闭";
        System.out.println(status);
        return this.isAlive;
    }
}
