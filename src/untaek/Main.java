package untaek;

import untaek.server.DB;
import untaek.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static Client client;
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine().trim();
        s.close();

        switch (input) {
            case "s": new Server(); break;
            case "c": client = new Client(); break;
            case "t3":
                Server server = new Server();
                server.setScore(null, 0);
                server.finish();
                break;
            case "db":
                new DB(); break;
            default: break;
        }
    }
}