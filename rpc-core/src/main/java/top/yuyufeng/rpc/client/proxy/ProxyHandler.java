package top.yuyufeng.rpc.client.proxy;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import top.yuyufeng.rpc.RpcRequest;
import top.yuyufeng.rpc.RpcResponse;
import top.yuyufeng.rpc.client.ClientCluster;
import top.yuyufeng.rpc.client.DiscoverService;
import top.yuyufeng.rpc.client.MyNettyClient;
import top.yuyufeng.rpc.utils.ProtostuffUtil;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 动态代理处理程序
 * @date 2017/8/18.
 * @author yuyufeng
 */
public class ProxyHandler implements InvocationHandler {
    private long timeout = 1000; //超时等待时间
    private Class<?> service;
    //远程调用地址
    private InetSocketAddress remoteAddress;


    public ProxyHandler(Class<?> service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        //准备传输的对象
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceName(service.getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setArguments(args);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setReturnType(method.getReturnType());
        return this.request(rpcRequest);
    }

    private Object request(RpcRequest rpcRequest) throws ClassNotFoundException {
        //获取需要请求的地址
        remoteAddress = ClientCluster.getServerIPByRandom(rpcRequest.getServiceName());
        if (remoteAddress == null) {
            return null;
        }
        Object result;
        RpcResponse rpcResponse = (RpcResponse) MyNettyClient.send(rpcRequest,remoteAddress);
        result = rpcResponse.getResult();
        return result;
    }
}
