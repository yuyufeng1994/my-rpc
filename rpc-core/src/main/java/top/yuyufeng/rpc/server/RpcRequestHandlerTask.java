package top.yuyufeng.rpc.server;


import top.yuyufeng.rpc.RpcContext;
import top.yuyufeng.rpc.exception.RpcException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Random;

/**
 * 接受远程调用并调用真实方法后响应
 * created by yuyufeng on 2017/8/19.
 */
public class RpcRequestHandlerTask implements Runnable {
    private Socket socket;

    public RpcRequestHandlerTask(Socket accept) {
        this.socket = accept;
    }

    @Override
    public void run() {
        acceptAndResponce();
    }

    private void acceptAndResponce() {
        //这里使用ObjectOutputStream ObjectInputStream，直接通过对象传输，所以传输的对象必须实现了序列化接口，序列化这块可以优化
        ObjectOutputStream os = null;
        ObjectInputStream is = null;
        try {
            //模拟作业时间
            long time = 100 * new Random().nextInt(10);
            is = new ObjectInputStream(socket.getInputStream());
            //读取第一部分数据
            RpcContext context = (RpcContext) is.readObject();
            System.out.println("执行任务需要消耗：" + time + " " + context);
            Thread.sleep(time);
            //从容易中得到已经注册的类
            Class clazz = RegisterServicesCenter.getService(context.getServiceName());
            if (clazz == null) {
                throw new RpcException("没有找到类 " + context.getServiceName());
            }
            Method method = clazz.getMethod(context.getMethodName(), context.getParameterTypes());
            if (method == null) {
                throw new RpcException("没有找到相应方法 " + context.getMethodName());
            }
            //执行真正要调用的方法。对于实例都不是单利模式的，每次都clazz.newInstance()，有待改进
            Object result = method.invoke(clazz.newInstance(), context.getArguments());
            os = new ObjectOutputStream(socket.getOutputStream());
            //返回执行结果给客户端
            os.writeObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
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
}
