package top.yuyufeng.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import top.yuyufeng.rpc.utils.ProtostuffUtil;

/**
 * Created by yuyufeng on 2017/8/28.
 */
public class MyEncoder  extends MessageToByteEncoder {
    private Class<?> genericClass;

    public MyEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = ProtostuffUtil.serializer(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
