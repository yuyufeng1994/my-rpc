package top.yuyufeng.rpc.test.dynamicProxy;

/**
 * created by yuyufeng on 2017/8/18.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String words) {
        System.out.println("Hello:" + words);
        return "Hello:" + words;
    }
}
