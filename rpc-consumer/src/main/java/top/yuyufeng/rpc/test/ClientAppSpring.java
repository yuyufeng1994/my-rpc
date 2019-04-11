package top.yuyufeng.rpc.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import top.yuyufeng.rpc.client.proxy.RemoteServiceImpl;
import top.yuyufeng.rpc.service.CalService;
import top.yuyufeng.rpc.service.HelloService;
import top.yuyufeng.rpc.service.top.yuyufeng.dto.MyResult;

/**
 * 服务调用 （客户端）
 *
 * @author yuyufeng
 * @date 2017/8/19.
 */
public class ClientAppSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"my-rpc-consumer.xml"});

        HelloService helloService = context.getBean(HelloService.class);
        String result = helloService.sayHello("yyf");
        System.out.println(result);

        CalService calService = context.getBean(CalService.class);
        MyResult myResult = calService.getResult(1, 2);
        System.out.println(myResult);

        myResult = calService.getResult(3, 2);
        System.out.println(myResult);
    }
}
