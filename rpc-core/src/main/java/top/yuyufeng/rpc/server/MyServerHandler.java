package top.yuyufeng.rpc.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import top.yuyufeng.rpc.RpcRequest;
import top.yuyufeng.rpc.RpcResponse;
import top.yuyufeng.rpc.exception.RpcException;
import top.yuyufeng.rpc.utils.ProtostuffUtil;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuyufeng
 * @date 2017/8/28
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 单利存放实例化之后的服务
     */
    private static Map<String, Object> serviceObjects = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) msg;
        Class clazz = RegisterServicesCenter.getService(rpcRequest.getServiceName());
        if (clazz == null) {
            throw new RpcException("没有找到类 " + rpcRequest.getServiceName());
        }
        Method method = clazz.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
        if (method == null) {
            throw new RpcException("没有找到相应方法 " + rpcRequest.getMethodName());
        }
        //执行真正要调用的方法。
        Object object = serviceObjects.get(clazz.getName());
        if (object == null) {
            object = clazz.newInstance();
            serviceObjects.put(clazz.getName(), object);
        }
        Object result = method.invoke(clazz.newInstance(), rpcRequest.getArguments());
        //返回执行结果给客户端
        RpcResponse rpcResponse = new RpcResponse(result);
        ctx.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
