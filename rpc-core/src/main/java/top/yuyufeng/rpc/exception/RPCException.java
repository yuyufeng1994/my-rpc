package top.yuyufeng.rpc.exception;

/**
 * created by yuyufeng on 2017/8/19.
 */
public class RPCException extends Exception {
    public RPCException() {
        super();
    }

    public RPCException(String message) {
        super(message);
    }

    public RPCException(String message, Throwable cause) {
        super(message, cause);
    }

    public RPCException(Throwable cause) {
        super(cause);
    }

    protected RPCException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
