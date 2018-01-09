package top.yuyufeng.rpc.client;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负载，分配ip
 *
 * @author yuyufeng
 * @date 2018/1/9
 */
public class ClientCluster {


    /**
     * 当有多台服务器的时候，随机分配一台。规则可自定义
     *
     * @param serviceName
     * @return
     */
    public static InetSocketAddress getServerIPByRandom(String serviceName) {
        List<InetSocketAddress> inetSocketAddressList = DiscoverService.discoverServices(serviceName);
        int length = inetSocketAddressList.size();

        return inetSocketAddressList.get(new Random().nextInt(length));
    }
}
