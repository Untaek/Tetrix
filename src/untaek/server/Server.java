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

  private DB db;
  private ChannelFuture channelFuture;

  private EventLoopGroup boss;
  private EventLoopGroup worker;

  private Hashtable<Integer, User> users;
  private Hashtable<Integer, Room> rooms;

  private final int MAXIMUM = 6;

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

  private Object onPacket(BasePacket packet, Channel ch) {
    System.out.println(packet.toString());

    switch (packet.getType()) {
      case "login": return this.login((Login) packet, ch);
      case "start": return this.startGame((StartGame) packet);
      case "snapshot": return this.broadcastSnapshot((Snapshot) packet);
      case "pop": return this.pop((Pop) packet);
      case "chat": return this.chat((Chat) packet);
      case "join": return this.join((Join) packet);
      case "lose": return this.lose((Lose) packet);
      case "leave": return this.leave((Leave) packet);
      default:
    }

    return null;
  }

  private void broadcast(BasePacket packet, int gameId) {
    this.rooms.get(gameId).getUsers().forEach(u -> u.getChannel().writeAndFlush(packet));
  }

  private void write(BasePacket packet, Channel channel) {
    channel.writeAndFlush(packet);
  }

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

      c.close();

      Room room = this.rooms.values()
          .stream()
          .filter((a) -> a.getUsers().size() < MAXIMUM)
          .findFirst()
          .orElse(new Room((int)(Math.random() * 100000), id));

      User user = new User(name, id, room.getId(), ch, ch.id().asShortText(), wins, loses);

      room.getUsers().add(user);
      this.users.put(user.getId(), user);
      this.rooms.put(room.getId(), room);

      write(
          PacketManager.getInstance()
              .loginResult(user.getUserStatus(), room.getUsersStatus().toArray(new UserStatus[0]), LoginResult.SUCCESS, room.getId(), room.getOwner()),
          user.getChannel());

      broadcast(
          PacketManager.getInstance()
              .join(user.getUserStatus()), room.getId());

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

  private int join(Join p) {
    broadcast(p, p.gameId);
    return 0;
  }

  private int startGame(StartGame p) {
    Room room = this.rooms.get(p.gameId);
    room.setStatus(Room.START);
    room.getLosers().clear();
    broadcast(p, p.gameId);
    return Room.START;
  }

  private int broadcastSnapshot(Snapshot p) {
    broadcast(p, p.gameId);
    return 0;
  }

  private int pop(Pop p) {
    int amount = 0;
    switch (p.amount) {
      case 1: break;
      case 2: amount = 1; break;
      case 3: amount = 2; break;
      case 4: amount = 4; break;
    }

    broadcast(PacketManager.getInstance().attack(p.id, amount), p.gameId);

    return 0;
  }

  private int chat(Chat p) {
    broadcast(p, p.gameId);
    return 0;
  }

  private int lose(Lose p) {
    Room room = rooms.get(p.gameId);
    room.getLosers().add(p.id);
    broadcast(p, p.gameId);

    if(room.getUsers().size() == room.getLosers().size() - 1) {
      broadcast(PacketManager.getInstance().finishGame(), p.gameId);
    }

    return 0;
  }

  private int leave(Leave p) {
    Room room = rooms.get(p.gameId);
    int prevOwner = room.getOwner();

    room.getUsers().forEach(u -> {
      if(p.id == u.getId()) {
        room.getUsers().remove(u);

        if(room.getUsers().size() == 0) {
          rooms.remove(p.gameId);
          return;
        }

        if(p.id == prevOwner) {
          room.setOwner(room.getUsers().get(0).getId());
          p.setNextOwner(room.getOwner());
        }
      }
    });

    broadcast(p, p.gameId);

    return 0;
  }

  private class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      System.out.println(ctx.channel().id().asShortText());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
      System.out.println(ctx.channel().id().asShortText());
      users.values()
          .stream()
          .filter(u -> u.getChannel().id().asShortText().equals(ctx.channel().id().asShortText()))
          .findFirst()
          .ifPresent(u -> leave(PacketManager.getInstance().leave(u.getId(), u.getGameId(), 0)));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      onPacket((BasePacket) msg, ctx.channel());
    }
  }
}
