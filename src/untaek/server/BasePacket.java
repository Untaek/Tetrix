package untaek.server;

import com.google.gson.Gson;

import java.io.Serializable;

public class BasePacket implements Serializable {
  private String type;
  private String networkId;
  protected int id;
  protected int gameId;

  public BasePacket(String type, String networkId) {
    this.type = type;
    this.networkId = networkId;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
