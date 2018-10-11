package untaek.server;

import com.google.gson.Gson;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {

  DB db;
  ChannelFuture channelFuture;

  EventLoopGroup boss;
  EventLoopGroup worker;



  public Server() {
    init();
  }

  private void init() {
    db = new DB();

    boss = new NioEventLoopGroup();
    worker = new NioEventLoopGroup();

    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(boss, worker)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(
                new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())),
                new ObjectEncoder(),
                new ServerHandler()
            );
          }
        })
        .option(ChannelOption.SO_BACKLOG, 128)
        .childOption(ChannelOption.SO_KEEPALIVE, true);

    try {
      channelFuture = bootstrap.bind(8765).sync();

      System.out.println("Server is bound");

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void finish() {
    try {
      channelFuture.channel().close().sync();
      boss.shutdownGracefully().sync();
      worker.shutdownGracefully().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void setScore(int[] ids, int winner){
    try {
      Connection c = db.getConnection();
      PreparedStatement st = c.prepareStatement("SELECT 1 + 1 AS so");
      ResultSet rs = st.executeQuery();
      rs.next();

      System.out.println(rs.getInt("so"));

      st.close();
      c.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void onPacket(Packet packet) {
    System.out.println(packet.toString());
    Object object = packet.getObject();

    switch (packet.getType()) {
      case "login": this.login((Packet.Login) object); break;
      case "start": this.startGame((Packet.StartGame) object); break;
      //case "finish": this.finishGame((Packet.FinishGame) object); break;
      case "snapshot": this.broadcastSnapshot((Packet.Snapshot) object); break;
      case "pop": this.pop((Packet.Pop) object); break;
      case "chat": this.chat((Packet.Chat) object); break;
      case "lose": this.lose((Packet.Lose) object); break;
      default:
    }
  }

  private void startGame(Packet.StartGame p) {

  }

  private void broadcastSnapshot(Packet.Snapshot p) {

  }

  private void pop(Packet.Pop p) {

  }

  private void chat(Packet.Chat p) {

  }

  private void lose(Packet.Lose p) {

  }

  private void finishGame(Packet.FinishGame p) {}

  public User login(Packet.Login p) {
    String name = p.name;
    String password = p.password;

    try {
      Connection c = db.getConnection();

      PreparedStatement st = c.prepareStatement("SELECT * FROM tbl_user WHERE name = ? AND password = ?");
      st.setString(0, name);
      st.setString(1, password);
      ResultSet rs = st.executeQuery();

      if(rs.next()) {
        System.out.println(String.format("Login success name: %s", name));

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    System.out.println(String.format("Login failed name: %s, password: %s", name, password));
    return null;
  }

  private class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      System.out.println(ctx.channel().id().asShortText());
      super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      onPacket((Packet) msg);
    }
  }
}
