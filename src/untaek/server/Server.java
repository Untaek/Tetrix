package untaek.server;

import io.netty.bootstrap.ServerBootstrap;
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
import java.util.Hashtable;
import untaek.server.Packet.*;

public class Server {

  DB db;
  ChannelFuture channelFuture;

  EventLoopGroup boss;
  EventLoopGroup worker;

  Hashtable<Integer, User> users;
  Hashtable<Integer, Room> rooms;

  final int MAXIMUM = 6;

  public Server() {
    init();
  }

  private void init() {
    users = new Hashtable<>();
    rooms = new Hashtable<>();

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
                new ObjectEncoder(),
                new ObjectDecoder(ClassResolvers.softCachingResolver(getClass().getClassLoader())),
                new ServerHandler()
            );
          }
        })
        .option(ChannelOption.SO_BACKLOG, 128)
        .childOption(ChannelOption.SO_KEEPALIVE, true);

      channelFuture = bootstrap.bind(8765);

      System.out.println("Server is bound");

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

  private Object onPacket(BasePacket packet, Channel ch) {
    System.out.println(packet.toString());

    switch (packet.getType()) {
      case "login": return this.login((Login) packet, ch);
      case "start": return this.startGame((StartGame) packet);
      case "ready": return this.ready((Ready) packet);
      case "snapshot": return this.broadcastSnapshot((Snapshot) packet);
      case "pop": return this.pop((Pop) packet);
      case "chat": return this.chat((Chat) packet);
      case "join": return this.join((Join) packet);
      default:
    }

    return null;
  }

  private void broadcast(BasePacket packet, Room room) {
    room.getUsers().forEach(u -> u.getChannel().writeAndFlush(packet));
  }

  private void write(BasePacket packet, Channel channel) {
    channel.writeAndFlush(packet);
  }

  // 나 로긘
  private int login(Login p, Channel ch) {
    String name = p.name;
    String password = p.password;

    PreparedStatement st;
    ResultSet findResult;

    String selectSQL = "" +
        "SELECT * FROM tbl_user " +
        "INNER JOIN tbl_score " +
        "ON tbl_user.id = tbl_score.user_id " +
        "WHERE tbl_user.name = ? AND tbl_user.password = ?";

    String insertSQL = "" +
        "INSERT INTO tbl_user " +
        "VALUES (default, ?, ?)";

    int id;
    int wins;
    int loses;

    try {
      Connection c = db.getConnection();

      st = c.prepareStatement(selectSQL);
      st.setString(1, name);
      st.setString(2, password);
      findResult = st.executeQuery();

      if(!findResult.next()) {
        st = c.prepareStatement(insertSQL);
        st.setString(1, name);
        st.setString(2, password);

        if(st.execute()) {
          st = c.prepareStatement(selectSQL);
          st.setString(1, name);
          st.setString(2, password);
          findResult = st.executeQuery();
          findResult.next();
        }
      }

      id = findResult.getInt("id");
      wins = findResult.getInt("win");
      loses = findResult.getInt("lose");

      User user = new User(name, id, ch, ch.id().asShortText(), wins, loses);

      users.put(user.getId(), user);

      c.close();

      Room room;

      room = this.rooms.values()
          .stream()
          .filter((a) -> a.getUsers().size() < MAXIMUM)
          .findFirst()
          .orElse(null);

      if(room == null) {
        room = new Room((int)(Math.random() * 100000));
        room.getUsers().add(user);
        this.rooms.put(room.getId(), room);
      }

      write(
          PacketManager.getInstance()
              .loginResult(user.getUserStatus(), room.getUsersStatus().toArray(new UserStatus[0]), LoginResult.SUCCESS, room.getId()),
          user.getChannel());

      broadcast(
          PacketManager.getInstance()
              .join(user.getUserStatus()), room);

      System.out.println(String.format("Login success name: %s", name));
      System.out.println(user.toString());

      return 1;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    write(PacketManager.getInstance().fail("login failed"), ch);
    System.out.println(String.format("Login failed name: %s, password: %s", name, password));
    return 0;
  }

  // 다른사람 들어옴
  private UserStatus join(Join p) {
    this.rooms.get(p.gameId)
        .getUsers()
        .forEach(u -> u.getChannel().write(p));

    return users.get(p.id).getUserStatus();
  }

  // 겜시작
  private int startGame(StartGame startGame) {
    Room room = this.rooms.get(startGame.gameId);
    room.setStatus(Room.START);
    room.getLosers().clear();
    room.getUsers().forEach(u -> u.getChannel().write(startGame));
    return Room.START;
  }
  // ready packet (client > server ) >> result send method
  private int ready(Ready ready) {


    int[] readyUser = new int[6];
    //readyUser.
    //room.getUsers().
    return 1;
  }

  // 방안에 잇는 사람들 field
  private int broadcastSnapshot(Snapshot p) {
    Room room = this.rooms.get(p.gameId);
    room.getUsers().forEach((u) -> u.getChannel().write(p));
    return 0;
  }

  // 부시는거
  private int pop(Pop p) {
    Room room = this.rooms.get(p.gameId);

    room.getUsers().forEach(u -> {
      int amount = 0;
      switch (p.amount) {
        case 1: break;
        case 2: amount = 1; break;
        case 3: amount = 2; break;
        case 4: amount = 4; break;
      }

      if(amount > 0 && p.id != u.getId()) {
        u.getChannel().write(PacketManager.getInstance().attack(p.id, amount));
      }
    });

    return 0;
  }

  private int chat(Chat p) {
    rooms.get(p.gameId).getUsers().forEach(u -> u.getChannel().write(p));
    return 0;
  }

  // 누구 죽음
  private int lose(Lose p) {
    Room room = rooms.get(p.gameId);
    room.getUsers().forEach(u -> u.getChannel().write(p));
    room.getLosers().add(p.id);

    if(room.getUsers().size() == room.getLosers().size() - 1) {
      room.getUsers().forEach(u -> u.getChannel().write(PacketManager.getInstance().finishGame()));
    }

    return 0;
  }


  private class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      // 로그인 처리, 최초 접속
      System.out.println(ctx.channel().id().asShortText());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      onPacket((BasePacket) msg, ctx.channel());
    }
  }
}
