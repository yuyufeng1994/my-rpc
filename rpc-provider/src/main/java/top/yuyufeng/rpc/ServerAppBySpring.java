package top.yuyufeng.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 通过Spring方式启动
 * @author yuyufeng
 */
public class ServerAppBySpring {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"my-rpc-provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
