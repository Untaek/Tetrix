package untaek.server;

public class Packet {
  public static class UserStatus extends BasePacket {
    private static final int OWNER = 524;
    private static final int PARTICIPANT = 329;
    
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
    private int owner;

    public LoginResult(UserStatus me, UserStatus users[], int status, int gameId, int owner) {
      super("login_result", me.id);
      this.users = users;
      this.status = status;
      this.gameId = gameId;
      this.owner = owner;
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

    public int getOwner() {
      return owner;
    }
  }

  public static class Leave extends BasePacket {
    private int nextOwner;
    public Leave(int id, int gameId, int nextOwner) {
      super("leave", id);
      this.gameId = gameId;
      this.nextOwner = nextOwner;
    }

    public int getNextOwner() {
      return nextOwner;
    }

    public void setNextOwner(int nextOwner) {
      this.nextOwner = nextOwner;
    }
  }
}
