package untaek.server;
import untaek.ClientHandler;
import untaek.server.Packet.*;

public class PacketManager {

  static private PacketManager instance;
  private PacketManager(){}

  static public PacketManager getInstance(){
    if(instance == null) {
      instance = new PacketManager();
    }
    return instance;
  }

  public UserStatus userStatus(int id, String name, int wins, int loses) {
    return new UserStatus(id, name, wins, loses);
  }

  public Login login(String name, String password) {
    return new Login(name, password);
  }

  public FinishGame finishGame() {
    return new FinishGame();
  }

  public Attack attack(int id, int amount){
    return new Attack(id, amount);
  }

  public Pop pop(int id, int amount) {
    return new Pop(id, amount);
  }

  public StartGame startGame(int id, int gameId) {
    return new StartGame(id, gameId);
  }

  public Snapshot snapshot(int id, int field[][], int color[][]) {
    return new Snapshot(id, field, color);
  }

  public Chat chat(int id, String text) {
    return new Chat(id, text);
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

  public LoginResult loginResult(UserStatus me, UserStatus users[], int status, int gameId, int owner){
    return new LoginResult(me, users, status, gameId, owner);
  }

  public Leave leave(int id, int gameId, int nextOwner) {
    return new Leave(id, gameId, nextOwner);
  }
}