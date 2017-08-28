package top.yuyufeng.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import top.yuyufeng.rpc.utils.ProtostuffUtil;

import java.util.List;

/**
 * Created by yuyufeng on 2017/8/28.
 */
public class MyDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;

    public MyDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Object obj = ProtostuffUtil.deserializer(data, genericClass);
        out.add(obj);
    }
}
