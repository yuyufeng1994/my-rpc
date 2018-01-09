package top.yuyufeng.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import top.yuyufeng.rpc.MyDecoder;
import top.yuyufeng.rpc.MyEncoder;
import top.yuyufeng.rpc.RpcRequest;
import top.yuyufeng.rpc.RpcResponse;

import java.net.InetSocketAddress;


/**
 * 客户端Netty实现
 * @author yuyufeng
 * @date 2017/8/28
 */
public class MyNettyClient {

    public static Object send(RpcRequest rpcRequest,InetSocketAddress inetSocketAddress){
        MyClientHandler myClientHandler = new MyClientHandler();
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            ch.pipeline().addLast(new MyEncoder(RpcRequest.class));
                            ch.pipeline().addLast(new MyDecoder(RpcResponse.class));
                            ch.pipeline().addLast(myClientHandler);
                        }
                    });

            ChannelFuture future = b.connect(inetSocketAddress.getAddress(), inetSocketAddress.getPort()).sync();
            future.channel().writeAndFlush(rpcRequest);

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return myClientHandler.getResult();
    }


}
