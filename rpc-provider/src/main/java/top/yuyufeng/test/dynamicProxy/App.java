package top.yuyufeng.test.dynamicProxy;

import java.lang.reflect.Proxy;

/**
 * created by yuyufeng on 2017/8/18.
 */
public class App {
    public static void main(String[] args) {
        /**
         * ①ClassLoader loader ： 定义代理类的类加载器，可获得已知的加载器。
         * ②Class<?> interfaces : 希望代理代理实现的接口列表，它可以是多个接口。
         * ③InvocationHandler h ： InvocationHandler接口的一个具体实现类的对象的引用（需传入被代理类实例对象的引用）。
         */
        HelloServiceImpl helloServiceImpl = new HelloServiceImpl();
        HelloService helloService = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(), new Class[]{HelloService.class}, new ProxyHandler(helloServiceImpl));
        helloService.hello("yyf");
    }
}
