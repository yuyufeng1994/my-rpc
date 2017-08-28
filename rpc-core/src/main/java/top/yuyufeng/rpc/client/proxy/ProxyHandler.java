package top.yuyufeng.rpc.client.proxy;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import top.yuyufeng.rpc.RpcRequest;
import top.yuyufeng.rpc.RpcResponse;
import top.yuyufeng.rpc.client.MyNettyClient;
import top.yuyufeng.rpc.utils.ProtostuffUtil;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.*;

/**
 * 动态代理处理程序
 * created by yuyufeng on 2017/8/18.
 */
public class ProxyHandler implements InvocationHandler {
    private long timeout = 1000; //超时等待时间
    private Class<?> service;
    //远程调用地址
    private InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1", 8989);
    private static final int TIME_OUT = 10000;
    private static String ZOOKEEPER_HOST = "localhost:2181"; //zookeeper地址

    public ProxyHandler(Class<?> service) {
        this.service = service;
    }

    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        //准备传输的对象
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceName(service.getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setRemoteAddress(remoteAddress.getAddress() + ":" + remoteAddress.getPort());
        rpcRequest.setArguments(args);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setReturnType(method.getReturnType());
        return this.request(rpcRequest);
    }

    private Object request(RpcRequest rpcRequest) throws ClassNotFoundException {
        //去zookeeper发现服务
        boolean isDiscover = discoverServices(rpcRequest.getServiceName(), remoteAddress);
        if (!isDiscover) {
            return null;
        }
        Object result = null;
        result = MyNettyClient.send(rpcRequest,remoteAddress);

       /* //使用线程池，主要是为了下面使用Future，异步得到结果，来做超时放弃处理
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;


        Future future = executor.submit(new Callable() {
            public Object call() throws Exception {
                //执行并返回远程调用结果
                return request(rpcRequest, socket, os, is);
            }
        });

        try {
            result = future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("请求响应超时放弃..");
            release(socket, os, is);
            future.cancel(true);
        }
        executor.shutdown();*/
        return result;
    }

    private boolean discoverServices(String serviceName, InetSocketAddress remoteAddress) {
        ZooKeeper zookeeper = null;
        try {
            zookeeper = new ZooKeeper(ZOOKEEPER_HOST, TIME_OUT, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] res = zookeeper.getData("/myrpc/" + serviceName, true, null);
            if (res == null) {
                System.err.println("服务没有发现...");
            }
            String resIp = new String(res);
            remoteAddress = new InetSocketAddress(resIp.split(":")[0], Integer.valueOf(resIp.split(":")[1]));
            System.out.println("发现服务 " + serviceName + " " + remoteAddress.getAddress());
            return true;
        } catch (KeeperException e) {
            System.err.println("服务没有发现...");
        } catch (InterruptedException e) {
            System.err.println("服务没有发现...");
        } finally {
            try {
                zookeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 远程调用请求
     *
     * @param rpcRequest
     * @param socket
     * @param os
     * @param is
     * @return
     * @throws ClassNotFoundException
     */
    private Object request(RpcRequest rpcRequest, Socket socket, OutputStream os, InputStream is) throws ClassNotFoundException {
        Object result = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            socket = new Socket(remoteAddress.getAddress(), remoteAddress.getPort());
            /*os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(rpcRequest);
//             shutdownOutput():执行此方法，显示的告诉服务端发送完毕
            socket.shutdownOutput();
            //阻塞等待服务器响应
            is = new ObjectInputStream(socket.getInputStream());
            result = is.readObject();*/
            os = socket.getOutputStream();
            byte[] bytes = ProtostuffUtil.serializer(rpcRequest);
            os.write(bytes);
            socket.shutdownOutput();
            is = socket.getInputStream();

            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                byteArrayOutputStream.write(b, 0, len);
            }

            RpcResponse rpcResponse = ProtostuffUtil.deserializer(byteArrayOutputStream.toByteArray(),RpcResponse.class);
            result =  rpcResponse.getResult();


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            release(socket, os, is);
        }
        return result;
    }

    private void release(Socket socket, OutputStream os, InputStream is) {
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
}
