package top.yuyufeng.rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by yuyufeng on 2017/8/28.
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {
    private Object monitor;

    private Object result;

    public MyClientHandler(Object monitor) {
        this.monitor = monitor;
    }

    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("MyClientHandler.channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("read Message:"+msg);
        result = msg;
        synchronized (monitor) {
            monitor.notifyAll(); // 收到响应，唤醒线程
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
