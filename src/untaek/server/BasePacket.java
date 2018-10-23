package untaek.server;

import com.google.gson.Gson;

import java.io.Serializable;

public class BasePacket implements Serializable {
  private String type;
  private String networkId;
  protected int id;
  protected int gameId;

  public BasePacket(String type, int id) {
    this.type = type;
    this.id = id;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
