package untaek;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine().trim();
        s.close();

        switch (input) {
          case "s": new Server(); break;
          case "c": new Client(); break;
          default: break;
        }
    }
}
