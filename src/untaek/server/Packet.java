package untaek.server;

import com.google.gson.Gson;

import java.io.Serializable;

public class Packet {
  private String type;
  private Object packet;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Object getPacket() {
    return packet;
  }

  public void setPacket(Object packet) {
    this.packet = packet;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
