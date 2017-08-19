package top.yuyufeng.rpc.common;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class RpcContext implements Serializable{
    private String serviceName;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    private InetSocketAddress localAddress;

    private InetSocketAddress remoteAddress;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
    }

    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public String toString() {
        return "RpcContext{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", arguments=" + Arrays.toString(arguments) +
                ", localAddress=" + localAddress +
                ", remoteAddress=" + remoteAddress +
                '}';
    }
}
