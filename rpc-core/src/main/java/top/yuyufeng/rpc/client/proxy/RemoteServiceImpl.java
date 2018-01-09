package top.yuyufeng.rpc.client.proxy;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @date 2017/8/19.
 * @author yuyufeng
 */
public class RemoteServiceImpl<T> {
    /**
     * 动态代理的真实对象的实现
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T newRemoteProxyObject(final Class<?> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new ProxyHandler(service));
    }
}
