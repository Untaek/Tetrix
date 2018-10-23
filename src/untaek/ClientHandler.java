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
import untaek.server.Packet.*;
import untaek.server.PacketManager;

public class ClientHandler {

  private static final String HOST = "localhost";
  private static final int PORT = 8765;
  private static Handler h;

  private Thread packetReceiverThread;

  private ClientHandler(){
    this.init();
  }

  static private ClientHandler instance;

  static public ClientHandler getInstance() {
    if (instance == null) {
      instance = new ClientHandler();
    }
    return instance;
  }

  private Handler getHandler() {
    return h;
  }

  public static String networkId() {
    //return h.context.channel().id().asShortText();
    return "asd";
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
                new ObjectEncoder(),
                new ObjectDecoder(ClassResolvers.softCachingResolver(getClass().getClassLoader())),
                h
            );
          }
        });
    packetReceiverThread = new Thread(() -> {
      ChannelFuture f = bootstrap.connect(HOST, PORT);

      try {
        f.channel().closeFuture().sync();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    packetReceiverThread.start();
  }

  /**
   *
   * 서버로 전송하시오
   *
   */

  void login(String name, String password) {
    System.out.println(String.format("network id: %s", networkId()));
    System.out.println(String.format("Try to login: %s, %s", name, password));
    getHandler().context.writeAndFlush(PacketManager.getInstance().login(name, password));
  }

  void pop(int id, int amount) {
    getHandler().context.writeAndFlush(PacketManager.getInstance().pop(id, amount));
  }

  void start(int id, int gameId) {
    getHandler().context.writeAndFlush(PacketManager.getInstance().startGame(id, gameId));
  }

  void snapshot(int id, final int field[][], final int color[][]) {
    getHandler().context.writeAndFlush(PacketManager.getInstance().snapshot(id, field, color));
  }

  void chat(int id, String text) {
    getHandler().context.writeAndFlush(PacketManager.getInstance().chat(id, text));
  }

  /**
   * 받은 패킷을 처리하고자 한다면
   * 아래의 리스너를 사용하시오
   */

  private interface OnLoginResultListener{ void on(LoginResult packet); }
  private OnLoginResultListener onLoginResultListener;
  public ClientHandler addOnLoginResultListener(OnLoginResultListener listener) { this.onLoginResultListener = listener; return this; }

  private interface OnJoinListener{ void on(Join packet); }
  private OnJoinListener onJoinListener;
  public ClientHandler addOnJoinListener(OnJoinListener listener) { this.onJoinListener = listener; return this; }

  private interface OnLeaveListener{ void on(Leave packet); }
  private OnLeaveListener onLeaveListener;
  public ClientHandler addOnLeaveListener(OnLeaveListener listener) { this.onLeaveListener = listener; return this; }

  private interface OnAttackListener{ void on(Attack packet); }
  private OnAttackListener onAttackListener;
  public ClientHandler addOnAttackListener(OnAttackListener listener) { this.onAttackListener = listener; return this; }

  private interface OnLoseListener{ void on(Lose packet); }
  private OnLoseListener onLoseListener;
  public ClientHandler addOnLoseListener(OnLoseListener listener) { this.onLoseListener = listener; return this; }

  private interface OnFinishListener{ void on(FinishGame packet); }
  private OnFinishListener onFinishListener;
  public ClientHandler addOnFinishListener(OnFinishListener listener) { this.onFinishListener = listener; return this; }

  private interface OnStartListener{ void on(StartGame packet); }
  private OnStartListener onStartListener;
  public ClientHandler addOnStartListener(OnStartListener listener) { this.onStartListener = listener; return this; }

  private interface OnChatListener{ void on(Chat packet); }
  private OnChatListener onChatListener;
  public ClientHandler addOnChatListener(OnChatListener listener) { this.onChatListener = listener; return this; }

  private interface OnSnapshotListener{ void on(Snapshot packet); }
  private OnSnapshotListener onSnapshotListener;
  public ClientHandler addOnSnapshotListener(OnSnapshotListener listener) { this.onSnapshotListener = listener; return this; }

  private void onPacket(BasePacket packet) {
    System.out.println(packet.toString());

    /*
     * 패킷 처리
     */

    switch (packet.getType()) {
      // 로그인
      case "login_result": onLoginResultListener.on((LoginResult) packet); break;

      // 다른 사람 접속
      case "join": onJoinListener.on((Join) packet); break;

      // 누가 나갔을 때
      case "leave": onLeaveListener.on((Leave) packet); break;

      // 공격 받았을 때
      case "attack": onAttackListener.on((Attack) packet); break;

      // 누군가 죽었을 때
      case "lose": onLoseListener.on((Lose) packet); break;

      // 게임이 끝났을 때
      case "finish": onFinishListener.on((FinishGame) packet); break;

      // 게임이 시작할 때
      case "start": onStartListener.on((StartGame) packet); break;

      // 채팅을 할 때
      case "chat": onChatListener.on((Chat) packet); break;

      // 다른 유저의 스냅샷 받음
      case "snapshot": onSnapshotListener.on((Snapshot) packet); break;
        default:
          System.out.println("[Unknown Packet in onPacket, ClientHandler class]" + packet.toString());
    }
  }

  class Handler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      this.context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      onPacket((BasePacket) msg);
    }
  }
}
