import java.util.List;
import java.util.Scanner;

public class Login {

    public static User showLoginMenu() {
        Scanner scanner = new Scanner(System.in);
        List<User> users = User.loadUsers();

        while (true) {
            System.out.println("\n=== LOGIN MENU ===");
            System.out.println("1) Login");
            System.out.println("2) Create User");
            System.out.println("0) End");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> { // bruger -> istedet for :. Da den enlig har en usynlig break, så man ikke behøver at skrive break. De nyeste versioner efter jave 12+ kan bruge dem.
                    User loggedIn = User.login(users, scanner);
                    if (loggedIn != null) {
                        return loggedIn;
                    }
                }
                case "2" -> User.createUser(users, scanner);
                case "0" -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }
}


