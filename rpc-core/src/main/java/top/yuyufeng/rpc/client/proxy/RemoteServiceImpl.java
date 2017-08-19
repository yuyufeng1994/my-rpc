package top.yuyufeng.rpc.client.proxy;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class RemoteServiceImpl<T> {
    public static <T> T newRemoteProxyObject(final Class<?> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new ProxyHandler(service));
    }
}
