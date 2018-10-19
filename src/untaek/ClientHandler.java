package untaek;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ClientHandler {
  private static final String HOST = "localhost";
  private static final int PORT = 8765;
  private Handler h;
  private Callback callback;

  interface Callback {
    void onConnected(Channel channel);
  }

  ClientHandler(Callback callback){
    this.callback = callback;
    this.init();
  }

  public Handler getHandler() {
    return this.h;
  }

  private void init() {
    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(group)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel socketChannel) throws Exception {
            h = new Handler();

            socketChannel.pipeline().addLast(
                new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())),
                new ObjectEncoder(),
                h
            );
          }
        });
    try {
      ChannelFuture f = bootstrap.connect(HOST, PORT).sync();

      callback.onConnected(f.channel());

      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  class Handler extends ChannelInboundHandlerAdapter {

    public ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      this.context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      System.out.println(msg.getClass().getName());
    }
  }
}
