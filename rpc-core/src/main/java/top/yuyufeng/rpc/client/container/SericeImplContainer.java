package top.yuyufeng.rpc.client.container;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import top.yuyufeng.rpc.client.proxy.RemoteServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuyufeng on 2017/8/29.
 */
public class SericeImplContainer {
//    private static Map<Class, Object> services = new HashMap<>();

    public SericeImplContainer(Map<String, Class> serviceMap) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        for (String key : serviceMap.keySet()) {
//            services.put(serviceMap.get(key), RemoteServiceImpl.newRemoteProxyObject(serviceMap.get(key)));
            context.getBeanFactory().autowireBean(RemoteServiceImpl.newRemoteProxyObject(serviceMap.get(key)));
        }
//        ApplicationContext context =


    }

}
