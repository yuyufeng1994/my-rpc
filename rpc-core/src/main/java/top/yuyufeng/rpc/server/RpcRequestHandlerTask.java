package top.yuyufeng.rpc.server;


import top.yuyufeng.rpc.RpcContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

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
        ObjectOutputStream os = null;
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(socket.getInputStream());
            //读取第一部分数据
            RpcContext context = (RpcContext) is.readObject();
            System.out.println(context);
            Class clazz = RegisterServicesCenter.getRegisterServices().get(context.getServiceName());
            Method method = clazz.getMethod(context.getMethodName(), context.getParameterTypes());
            Object result = method.invoke(clazz.newInstance(),context.getArguments());

            os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(result);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
