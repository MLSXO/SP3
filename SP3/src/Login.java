import java.util.List;
import java.util.Scanner;

public class Login {

    public static User showLoginMenu() {
        Scanner scanner = new Scanner(System.in);
        List<User> users = User.loadUsers();

        while (true) {
            System.out.println("\n=== LOGIN MENU ===");
            System.out.println("1) Log ind");
            System.out.println("2) Opret bruger");
            System.out.println("0) Afslut");
            System.out.print("Vælg: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    User loggedIn = User.login(users, scanner);
                    if (loggedIn != null) {
                        return loggedIn;
                    }
                }
                case "2" -> User.createUser(users, scanner);
                case "0" -> {
                    System.out.println("Farvel!");
                    System.exit(0);
                }
                default -> System.out.println("Ugyldigt valg.");
            }
        }
    }
}


