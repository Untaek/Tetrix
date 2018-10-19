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

  public static class UserStatus {
    int id;
    String name;
    Score score;

    public UserStatus(int id, String name, Score score) {
      this.id = id;
      this.name = name;
      this.score = score;
    }
  }

  public static class Score {
    int wins;
    int loses;

    public Score(int wins, int loses) {
      this.wins = wins;
      this.loses = loses;
    }
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
