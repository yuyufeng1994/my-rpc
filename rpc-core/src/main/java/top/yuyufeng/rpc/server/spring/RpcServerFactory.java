package top.yuyufeng.rpc.server.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import top.yuyufeng.rpc.server.RpcServer;
import top.yuyufeng.rpc.server.RpcServerImpl;

import java.util.Map;

/**
 * 用户读取xml中的接口
 *
 * @author yuyufeng
 * @date 2019/4/10.
 */

public class RpcServerFactory implements InitializingBean, DisposableBean {

    private MyRpcConfig myRpcConfig;
    private RpcServer rpcServer;
    private Map<String, Class> services;

    public RpcServerFactory() {
    }

    public void setMyRpcConfig(MyRpcConfig myRpcConfig) {
        this.myRpcConfig = myRpcConfig;
    }


    public void setServices(Map<String, Class> services) {
        this.services = services;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.build(myRpcConfig);
    }

    public void build(MyRpcConfig config) {
        this.rpcServer = new RpcServerImpl(config.getPort(), config.getnThreads(), config.getZookeeperAddress(), false);
        for (String key : services.keySet()) {
            try {
                rpcServer.register(key,services.get(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //启动rpc服务
        rpcServer.start();
    }

    @Override
    public void destroy() throws Exception {
        this.rpcServer.stop();
    }
}
