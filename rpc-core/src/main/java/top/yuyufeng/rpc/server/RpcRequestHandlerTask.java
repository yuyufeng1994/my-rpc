package top.yuyufeng.rpc.server;


import top.yuyufeng.rpc.RpcContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Random;

/**
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
            long time = 500 * new Random().nextInt(3);
            is = new ObjectInputStream(socket.getInputStream());
            //读取第一部分数据
            RpcContext context = (RpcContext) is.readObject();
            System.out.println("执行任务需要消耗：" + time + " " + context);
            Thread.sleep(time);
            Class clazz = RegisterServicesCenter.getRegisterServices().get(context.getServiceName());
            Method method = clazz.getMethod(context.getMethodName(), context.getParameterTypes());
            //对于实例都不是单利模式的，每次都clazz.newInstance()，有待改进
            Object result = method.invoke(clazz.newInstance(), context.getArguments());
            os = new ObjectOutputStream(socket.getOutputStream());
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
