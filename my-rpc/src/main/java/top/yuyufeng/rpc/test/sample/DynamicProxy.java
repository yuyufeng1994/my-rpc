package top.yuyufeng.rpc.test.sample;

/**
 * created by yuyufeng on 2017/8/18.
 */

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Proxy;

public class DynamicProxy {
    public static void main(String args[]) {
        RealSubject real = new RealSubject();
        Subject proxySubject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(),  new Class[]{Subject.class},new ProxyHandler(real));

        proxySubject.doSomething();

        //write proxySubject class binary data to file
//        createProxyClassFile();
    }

    public static void createProxyClassFile() {
        String name = "ProxySubject";
        byte[] data = ProxyGenerator.generateProxyClass(name, new Class[]{Subject.class});
        try {
            FileOutputStream out = new FileOutputStream(name + ".class");
            out.write(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}