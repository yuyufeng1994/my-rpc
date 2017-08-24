package top.yuyufeng.rpc.server;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class RegisterServicesCenter {
    private static String zookeeperHost;
    private static ZooKeeper zookeeper;
    private static int TIME_OUT = 10000;
    private static String localIp;

    public static void init(String zookeeperHost, int localPort) {
        RegisterServicesCenter.zookeeperHost = zookeeperHost;
        try {
            zookeeper = new ZooKeeper(zookeeperHost, TIME_OUT, null);
            localIp = InetAddress.getLocalHost().getHostAddress() + ":" + localPort;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //暴露接口的实现类存放容器
    private static ConcurrentHashMap<String, Class> registerServices = new ConcurrentHashMap<>();

    public static void addServices(String className, Class clazz) throws InterruptedException {
        registerServices.put(className, clazz);
        //存入zookeeper节点
        try {
            if (zookeeper.exists("/myrpc", false) == null) {
                zookeeper.create("/myrpc", "true".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (zookeeper.exists("/myrpc/" + className, false) != null) {
                zookeeper.delete("/myrpc/" + className, -1);
            }
            //把当前服务的ip地址存如zookeeper中,供消费者发现
            zookeeper.create("/myrpc/" + className, localIp.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } finally {
//            zookeeper.close();
        }
    }

    public static Class getService(String className) {
        return registerServices.get(className);
    }
}
