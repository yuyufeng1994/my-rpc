package top.yuyufeng.rpc.server.spring;

/**
 * @author yuyufeng
 * @date 2019/4/10.
 */
public class MyRpcConfig {
    private Integer port;
    private Integer nThreads;
    private String zookeeperAddress;

    public MyRpcConfig() {

    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getnThreads() {
        return nThreads;
    }

    public void setnThreads(Integer nThreads) {
        this.nThreads = nThreads;
    }

    public String getZookeeperAddress() {
        return zookeeperAddress;
    }

    public void setZookeeperAddress(String zookeeperAddress) {
        this.zookeeperAddress = zookeeperAddress;
    }

}
