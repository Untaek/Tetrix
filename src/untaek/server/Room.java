package untaek.server;

import java.util.ArrayList;
import untaek.server.Packet.*;

public class Room {
  public static final int WAITING = 817;
  public static final int FULL = 631;
  public static final int START = 901;

  private ArrayList<User> users;
  private ArrayList<Integer> losers;
  private int owner;
  private int status = WAITING;
  private int id;

  public Room(int id, int owner){
    this.users = new ArrayList<>();
    this.losers = new ArrayList<>();
    this.id = id;
    this.owner = owner;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

  public int getId() {
    return id;
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public ArrayList<UserStatus> getUsersStatus() {
    ArrayList<UserStatus> l = new ArrayList<>();
    this.users.forEach(u -> l.add(u.getUserStatus()));
    return l;
  }

  public ArrayList<Integer> getLosers() {
    return losers;
  }

  public void setOwner(int owner) {
    this.owner = owner;
  }

  public int getOwner() {
    return owner;
  }
}