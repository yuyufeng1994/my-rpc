package top.yuyufeng.rpc.test.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 客户端
 * created by yuyufeng on 2017/8/18.
 */
public class TestTcpClient {

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
//        send();
        for (int i = 0; i < 6; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            send();
                            Thread.sleep(100);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }


    }

    private static void send() throws ClassNotFoundException {
        Socket socket = null;
        ObjectOutputStream os = null;
        ObjectInputStream is = null;
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 8989);
            os = new ObjectOutputStream(socket.getOutputStream());
            //发送两次数据
            os.writeObject(new TransportObject(2, "我是客户端1！"));
            os.writeObject(new TransportObject(3, "我是客户端2！"));
            // shutdownOutput():执行此方法，显示的告诉服务端发送完毕
            socket.shutdownOutput();
            //阻塞等待服务器响应
            is = new ObjectInputStream(socket.getInputStream());
            TransportObject transportObject = (TransportObject) is.readObject();
            System.out.println(transportObject.toString());
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
    }

}
