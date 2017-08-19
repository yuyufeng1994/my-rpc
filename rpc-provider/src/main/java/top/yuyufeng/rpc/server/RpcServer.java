package top.yuyufeng.rpc.server;

/**
 * created by yuyufeng on 2017/8/19.
 */
public interface RpcServer {
    /**
     * 启动rpc服务
     */
    void start();

    /**
     * 停止rpc服务
     */
    void stop();

    /**
     * 把服务注册进rpc
     */
    void  register(String className, Class clazz) throws Exception;

    /**
     * rpc 服务是否存活
     * @return
     */
    boolean isAlive();
}
