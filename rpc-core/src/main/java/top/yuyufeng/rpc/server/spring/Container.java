package top.yuyufeng.rpc.server.spring;

import top.yuyufeng.rpc.server.RpcServer;

import java.util.Map;

/**
 * Created by yuyufeng on 2017/8/29.
 */
public class Container {
    private RpcServer rpcServer;
    public Map<String,Class> services;

    public Container(RpcServer rpcServer,Map<String,Class> services){
        System.out.println("使用Spring方式加载成功,开始注册服务");
        this.rpcServer = rpcServer;
        this.services = services;
        for (String key : services.keySet()) {
            try {
                rpcServer.register(key,services.get(key));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
