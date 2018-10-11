package untaek.server;

import com.google.gson.Gson;

import java.io.Serializable;

public class Packet {
  private String type;
  private Object object;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public class StartGame {
    int gameId;
    int ids[];
  }

  public class FinishGame {
    int gameId;
    int winnerId;
  }

  public class Snapshot {
    int gameId;
    int fields[][];
    int colors[][];
  }

  public class Pop {
    int gameId;
    int id;
    int amount;
  }

  public class Chat {
    int gameId;
    int id;
    String message;
  }

  public class Lose {
    int gameId;
    int id;
  }

  public class Login {
    String name;
    String password;
  }
}
