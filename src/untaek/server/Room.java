package untaek.server;

import java.util.ArrayList;

public class Room {
  public static final int WAITING = 817;
  public static final int FULL = 631;
  public static final int START = 901;

  private ArrayList<User> users;
  private ArrayList<Integer> losers;
  private int status = WAITING;

  public Room(){
    this.users = new ArrayList<>();
    this.losers = new ArrayList<>();
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public ArrayList<Integer> getLosers() {
    return losers;
  }
}