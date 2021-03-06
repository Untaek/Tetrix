package untaek.server;

import io.netty.channel.Channel;


public class User {
  private String name;
  private int id;
  private Channel channel;
  private String networkId;
  private int wins, loses;

  public User(String name, int id, Channel channel, String networkId) {
    this.name = name;
    this.id = id;
    this.channel = channel;
    this.networkId = networkId;
  }

  public User(String name, int id, Channel channel, String networkId, int wins, int loses) {
    this.name = name;
    this.id = id;
    this.channel = channel;
    this.networkId = networkId;
    this.wins = wins;
    this.loses = loses;
  }

  public int getId() {
    return id;
  }

  public Packet.UserStatus getUserStatus() {
    return PacketManager.getInstance().userStatus(id, name, wins, loses);
  }

  public Channel getChannel() {
    return channel;
  }

  @Override
  public String toString() {
    return String.format("%d %s %s %d %d", id, networkId, name, wins, loses);
  }
}
