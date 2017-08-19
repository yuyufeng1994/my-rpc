package top.yuyufeng.rpc.test.sample;

/**
 * created by yuyufeng on 2017/8/18.
 */
public class RealSubject implements Subject
{
    public void doSomething()
    {
        System.out.println( "call doSomething()" );
    }
}