package top.yuyufeng.rpc.client.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import top.yuyufeng.rpc.client.proxy.RemoteServiceImpl;

import java.util.Map;

/**
 * @author yuyufeng
 * @date 2019/4/10.
 */
public class MyRpcReference implements ApplicationContextAware, InitializingBean, DisposableBean {

    private DefaultListableBeanFactory beanFactory;
    /**
     * 远程接口
     */
    private Map<String, Class> references;

    public void setReferences(Map<String, Class> references) {
        this.references = references;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (String key : references.keySet()) {
            Class clazz = references.get(key);
            Object object = RemoteServiceImpl.newRemoteProxyObject(clazz);
            //注入Spring容器
            beanFactory.registerSingleton(key, object);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }
}
