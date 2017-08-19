package top.yuyufeng.rpc.client.proxy;


import top.yuyufeng.rpc.RpcContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * created by yuyufeng on 2017/8/18.
 */
public class ProxyHandler implements InvocationHandler {
    private  Class<?> service;
    private InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1", 8989);

    public ProxyHandler(Class<?> service) {
        this.service = service;
    }

    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        RpcContext rpcContext = new RpcContext();
        rpcContext.setServiceName(service.getName());
        rpcContext.setMethodName(method.getName());
        rpcContext.setRemoteAddress(remoteAddress);
        rpcContext.setArguments(args);
        rpcContext.setParameterTypes(method.getParameterTypes());

        return this.request(rpcContext);
    }

    private Object request(RpcContext rpcContext) throws ClassNotFoundException {
        Object result = null;
        Socket socket = null;
        ObjectOutputStream os = null;
        ObjectInputStream is = null;
        try {
            socket = new Socket(remoteAddress.getAddress(), remoteAddress.getPort());
            os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(rpcContext);
//             shutdownOutput():执行此方法，显示的告诉服务端发送完毕
            socket.shutdownOutput();
            //阻塞等待服务器响应
            is = new ObjectInputStream(socket.getInputStream());
            result = is.readObject();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
