package top.yuyufeng.rpc;

/**
 * Created by yuyufeng on 2017/8/28.
 */
public class RpcResponse {
    public RpcResponse() {
    }

    public RpcResponse(Object result) {
        this.result = result;
    }

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
