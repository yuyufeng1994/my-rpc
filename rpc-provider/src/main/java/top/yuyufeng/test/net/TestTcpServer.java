package top.yuyufeng.test.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务端
 * created by yuyufeng on 2017/8/18.
 */
public class TestTcpServer {
    private static ExecutorService executor = Executors.newFixedThreadPool(5);
    private static AtomicInteger count = new AtomicInteger();

    // 服务端
    public static void main(String[] args) throws ClassNotFoundException {
        ServerSocket ss = null;
        Socket s = null;
        try {
            ss = new ServerSocket(8989);
            System.out.println("服务端已经启动...");
            while (true) {
                int id = count.getAndAdd(1);
                executor.execute(new ServerTask(id, ss.accept()));
                System.out.println("执行一次响应.." + id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}

class ServerTask implements Runnable {
    private Integer id;//任务标记
    private Socket socket;

    public ServerTask(Integer id, Socket socket) {
        this.id = id;
        this.socket = socket;
    }

    public void run() {
        ObjectOutputStream os = null;
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(socket.getInputStream());
            //读取第一部分数据
            TransportObject transportObject = (TransportObject) is.readObject();
            System.out.println(transportObject.toString() + "任务标记:" + id);
            //读取第二部分数据
            transportObject = (TransportObject) is.readObject();
            System.out.println(transportObject.toString() + "任务标记:" + id);
            os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(new TransportObject(1, "我是客户端！"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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