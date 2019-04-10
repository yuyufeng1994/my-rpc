package top.yuyufeng.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import top.yuyufeng.rpc.MyDecoder;
import top.yuyufeng.rpc.MyEncoder;
import top.yuyufeng.rpc.RpcRequest;
import top.yuyufeng.rpc.RpcResponse;

import java.net.InetSocketAddress;

/**
 * 服务
 * @author yuyufeng
 * @date 2017/8/28
 */
public class MyServer {
    private int port;
    private int threadSize = 5;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public MyServer(int port,int threadSize) {
        this.port = port;
        this.threadSize = threadSize;
    }

    public void start() {
         bossGroup = new NioEventLoopGroup(threadSize);
         workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            ch.pipeline().addLast(new MyDecoder(RpcRequest.class));
                            ch.pipeline().addLast(new MyEncoder(RpcResponse.class));
                            ch.pipeline().addLast(new MyServerHandler());
                        }

                        ;

                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = sbs.bind(port).sync();
            System.out.println("Rpc服务启动成功 " + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void shutdown(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
