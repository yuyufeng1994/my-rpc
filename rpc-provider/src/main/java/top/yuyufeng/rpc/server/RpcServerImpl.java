package top.yuyufeng.rpc.server;

import top.yuyufeng.rpc.exception.RPCException;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
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


    public RpcServerImpl(int port, int nThreads) {
        this.port = port;
        this.nThreads = nThreads;
        init();
    }

    public void init() {
        executor = Executors.newFixedThreadPool(nThreads);
    }

    @Override
    public void start() {
        isAlive = true;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                executor.execute(new RpcRequestHandler(serverSocket.accept()));
                System.out.println("执行一次响应.." + new Date());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void stop() {
        isAlive = false;
        executor.shutdown();
    }

    @Override
    public void register(String className, Class clazz) throws Exception {
        if (RegisterServicesCenter.getRegisterServices() != null) {
            RegisterServicesCenter.getRegisterServices().put(className, clazz);
        } else {
            throw new RPCException("RPC服务未初始化");
        }
    }

    @Override
    public boolean isAlive() {
        String status = (this.isAlive == true) ? "RPC服务已经启动" : "RPC服务已经关闭";
        System.out.println(status);
        return this.isAlive;
    }
}
