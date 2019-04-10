package top.yuyufeng.rpc.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import top.yuyufeng.rpc.client.proxy.RemoteServiceImpl;
import top.yuyufeng.rpc.service.CalService;
import top.yuyufeng.rpc.service.HelloService;
import top.yuyufeng.rpc.service.top.yuyufeng.dto.MyResult;

/**
 * 服务调用 （客户端）
 * @date 2017/8/19.
 * @author yuyufeng
 */
public class ClientAppSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"my-rpc-consumer.xml"});
    }
}
