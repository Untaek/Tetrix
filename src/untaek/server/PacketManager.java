package untaek.server;

import com.google.gson.Gson;
import untaek.ClientHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PacketManager {

  static private String networkId;
  static private PacketManager instance;
  private PacketManager(){}

  static public PacketManager getInstance(){
    if(instance == null) {
      instance = new PacketManager();
    }
    networkId = ClientHandler.networkId();
    return instance;
  }

  String currentNetworkId() {
    return networkId;
  }

  public UserStatus userStatus(String name, int wins, int loses) {
    return new UserStatus(name, wins, loses);
  }

  public Login login(String name, String password) {
    return new Login(name, password);
  }

  public FinishGame finishGame() {
    return new FinishGame();
  }

  public Attack attack(int amount){
    return new Attack(amount);
  }

  public Pop pop(int amount) {
    return new Pop(amount);
  }

  public StartGame startGame() {
    return new StartGame();
  }

  public Snapshot snapshot(int field[][], int color[][]) {
    return new Snapshot(field, color);
  }

  public Chat chat(String text) {
    return new Chat(text);
  }

  public Users users(UserStatus users[]) {
    return new Users(users);
  }

  public Fail fail(String reason) {
    return new Fail(reason);
  }

  public Join join(UserStatus user) {
    return new Join(user);
  }
}

class UserStatus extends BasePacket {
  private String name;
  private int wins;
  private int loses;
  private int status;

  public UserStatus(String name, int wins, int loses){
    super("user_status", PacketManager.getInstance().currentNetworkId());
    this.name = name;
    this.wins = wins;
    this.loses = loses;
  }
}

class StartGame extends BasePacket {
  public StartGame() {
    super("start_game", PacketManager.getInstance().currentNetworkId());
  }
}
class Snapshot extends BasePacket {
  int field[][];
  int color[][];

  public Snapshot(int field[][], int color[][]) {
    super("snapshot", PacketManager.getInstance().currentNetworkId());
  }
}

class Pop extends BasePacket {
  int amount;

  public Pop(int amount) {
    super("pop", PacketManager.getInstance().currentNetworkId());
    this.amount = amount;
  }
}

class Join extends BasePacket {
  UserStatus user;
  public Join(UserStatus user) {
    super("join", PacketManager.getInstance().currentNetworkId());
    this.user = user;
  }
}

class Attack extends BasePacket {
  int amount;

  public Attack(int amount) {
    super("attack", PacketManager.getInstance().currentNetworkId());
    this.amount = amount;
  }
}

class Login extends BasePacket {
  String name;
  String password;

  public Login(String name, String password) {
    super("login", PacketManager.getInstance().currentNetworkId());
    this.name = name;
    this.password = password;
  }
}

class Lose extends BasePacket {
  public Lose() {
    super("lose", PacketManager.getInstance().currentNetworkId());
  }
}

class Chat extends BasePacket {
  String text;

  public Chat(String text) {
    super("chat", PacketManager.getInstance().currentNetworkId());
    this.text = text;
  }
}

class FinishGame extends BasePacket {
  FinishGame() {
    super("finish", PacketManager.getInstance().currentNetworkId());
  }
}

class Users extends BasePacket {
  private UserStatus users[];
  Users(UserStatus users[]) {
    super("users", PacketManager.getInstance().currentNetworkId());
    this.users = users;
  }
}

class Fail extends BasePacket {
  String reason;
  public Fail(String reason) {
    super("fail", PacketManager.getInstance().currentNetworkId());
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}