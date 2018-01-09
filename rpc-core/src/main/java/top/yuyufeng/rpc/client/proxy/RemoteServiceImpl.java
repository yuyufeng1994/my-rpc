package top.yuyufeng.rpc.client.proxy;

import top.yuyufeng.rpc.client.DiscoverService;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuyufeng
 * @date 2017/8/19.
 */
public class RemoteServiceImpl<T> {
    /**
     * 动态代理的真实对象的实现
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T newRemoteProxyObject(final Class<?> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new ProxyHandler(service));
    }


    /**
     * 设置Zookeeper的地址，默认为本机2181接口
     * @param address
     */
    public static void setZookeeperAddress(String address) {
        DiscoverService.setZookeeperHost(address);
    }
}
