package top.yuyufeng.rpc.exception;

/**
 * 异常处理类
 * @date 2017/8/19.
 * @author yuyufeng
 */
public class RpcException extends Exception {
    public RpcException() {
        super();
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

    protected RpcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
