package untaek;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import untaek.server.BasePacket;
import untaek.server.PacketManager;

public class ClientHandler {

  private static final String HOST = "localhost";
  private static final int PORT = 8765;
  private static Handler h;
  private Callback callback;

  interface Callback {
    void onConnected(Channel channel);
  }

  ClientHandler(Callback callback){
    this.callback = callback;
    this.init();
  }

  private Handler getHandler() {
    return h;
  }

  public static String networkId() {
    return h.context.channel().id().asShortText();
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

  /**
   *
   * 서버로 전송하시오
   *
   */

  void login(String name, String password) {
    getHandler().context.channel().write(PacketManager.getInstance().login(name, password));
  }

  void pop(int amount) {
    getHandler().context.channel().write(PacketManager.getInstance().pop(amount));
  }

  void start() {
    getHandler().context.channel().write(PacketManager.getInstance().startGame());
  }

  void snapshot(final int field[][], final int color[][]) {
    getHandler().context.channel().write(PacketManager.getInstance().snapshot(field, color));
  }

  void chat(String text) {
    getHandler().context.channel().write(PacketManager.getInstance().chat(text));
  }
  /**
   *
   * 클라이언트 기능을 넣으시오
   *
   */
  void onPacket(BasePacket packet) {
    System.out.println(packet.toString());
    switch (packet.getType()) {
      // 로그입
      case "login": break;

      // 다른 사람 접속
      case "join": break;

      // 누가 나갔을 때
      case "leave": break;

      // 공격 받았을 때
      case "attack": break;

      // 누군가 죽었을 때
      case "lose": break;

      // 게임이 끝났을 때
      case "finish": break;

      // 게임이 시작할 때
      case "start": break;

      // 채팅을 할 때
      case "chat": break;
        default:
          System.out.println("[Unknown Packet in onPacket, ClientHandler class]" + packet.toString());
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
      onPacket((BasePacket) msg);
    }
  }
}
