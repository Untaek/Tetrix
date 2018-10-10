package untaek.server;

import java.io.*;
import java.net.Socket;
import java.rmi.UnexpectedException;

public class User {

  User(Socket socket) throws UnexpectedException {
    if(socket == null) throw new UnexpectedException("Socket is closed");

    try {
      os = socket.getOutputStream();
      oos = new ObjectOutputStream(os);

      is = socket.getInputStream();
      ois = new ObjectInputStream(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Socket socket;
  private String name;
  private String id;

  private OutputStream os;
  private ObjectOutputStream oos;

  private InputStream is;
  private ObjectInputStream ois;

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public OutputStream getOs() {
    return os;
  }

  public void setOs(OutputStream os) {
    this.os = os;
  }

  public ObjectOutputStream getOos() {
    return oos;
  }

  public void setOos(ObjectOutputStream oos) {
    this.oos = oos;
  }

  public InputStream getIs() {
    return is;
  }

  public void setIs(InputStream is) {
    this.is = is;
  }

  public ObjectInputStream getOis() {
    return ois;
  }

  public void setOis(ObjectInputStream ois) {
    this.ois = ois;
  }
}
