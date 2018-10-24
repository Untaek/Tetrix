package untaek.server;

public class Packet {
  public static class UserStatus extends BasePacket {
    private String name;
    private int wins;
    private int loses;

    public UserStatus(int id, String name, int wins, int loses){
      super("user_status", id);
      this.name = name;
      this.wins = wins;
      this.loses = loses;
    }

    public String getName() {
      return name;
    }

    public int getWins() {
      return wins;
    }

    public int getLoses() {
      return loses;
    }
  }

  public static class StartGame extends BasePacket {
    public StartGame(int id, int gameId) {
      super("start_game", id);
      this.gameId = gameId;
    }
  }
  public static class Ready extends BasePacket {
    public Ready(int id, int gameId) {
      super("ready", id);
      this.gameId = gameId;

      //
      //조건문 다 래디햇어
      // start packet 보내는 메소드를 여기에
      // startPacketSend();   ( server > client)
    }
  }

  public static class Snapshot extends BasePacket {
    int field[][];
    int color[][];

    public Snapshot(int id, int field[][], int color[][]) {
      super("snapshot", id);
      this.field = field;
      this.color = color;
    }

    public int[][] getField() {
      return field;
    }

    public int[][] getColor() {
      return color;
    }
  }

  public static class Pop extends BasePacket {
    int amount;

    public Pop(int id, int amount) {
      super("pop", id);
      this.amount = amount;
    }

    public int getAmount() {
      return amount;
    }
  }

  public static class Join extends BasePacket {
    UserStatus user;
    public Join(UserStatus user) {
      super("join", user.id);
      this.user = user;
    }

    public UserStatus getUser() {
      return user;
    }
  }

  public static class Attack extends BasePacket {
    int amount;

    public Attack(int id, int amount) {
      super("attack", id);
      this.amount = amount;
    }

    public int getAmount() {
      return amount;
    }
  }

  public static class Login extends BasePacket {
    String name;
    String password;

    public Login(String name, String password) {
      super("login", 0);
      this.name = name;
      this.password = password;
    }

    public String getName() {
      return name;
    }

    public String getPassword() {
      return password;
    }
  }

  public static class Lose extends BasePacket {
    public Lose(int id) {
      super("lose", id);
    }
  }

  public static class Chat extends BasePacket {
    String text;

    public Chat(int id, String text) {
      super("chat", id);
      this.text = text;
    }

    public String getText() {
      return text;
    }
  }

  public static class FinishGame extends BasePacket {
    FinishGame() {
      super("finish", 0);
    }
  }

  public static class Users extends BasePacket {
    private UserStatus users[];
    Users(UserStatus users[]) {
      super("users", 0);
      this.users = users;
    }

    public UserStatus[] getUsers() {
      return users;
    }
  }

  public static class Fail extends BasePacket {
    String reason;
    public Fail(String reason) {
      super("fail", 0);
      this.reason = reason;
    }

    public String getReason() {
      return reason;
    }
  }

  public static class LoginResult extends BasePacket {
    public static final int SUCCESS = 1;
    public static final int FAILED = 2;

    private UserStatus me;
    private UserStatus users[];
    private int status;

    public LoginResult(UserStatus me, UserStatus users[], int status, int gameId) {
      super("login_result", me.id);
      this.users = users;
      this.status = status;
      this.gameId = gameId;
    }

    public UserStatus getMe() {
      return me;
    }

    public UserStatus[] getUsers() {
      return users;
    }

    public int getStatus() {
      return status;
    }
  }

  public static class Leave extends BasePacket {
    public Leave(int id, int gameId) {
      super("leave", id);
      this.gameId = gameId;
    }
  }
}
