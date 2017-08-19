package top.yuyufeng.test.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * created by yuyufeng on 2017/8/18.
 */
public class ProxyHandler implements InvocationHandler {
    private Object proxied;

    public ProxyHandler(Object object) {
        this.proxied = object;
    }


    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        /**
         * ①Object proxy：代理对象引用。
         * ②Method method：代理类中接受的接口方法。
         * ③Object[] args：向代理对象的方法中传递的参数。
         */
        System.out.println("<执行方法之前>");
        Object result = method.invoke(proxied, args);
        System.out.println("<执行方法之后>");
        return result;
    }
}
