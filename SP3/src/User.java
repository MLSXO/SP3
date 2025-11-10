import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private String username;
    private String password;

    public static final String FILE_NAME = "users.txt";

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //
    // Her kommer de metoder, der håndterer login + opret bruger
    //

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_NAME);


        if (!file.exists()) {
            System.out.println("Ingen brugerfil fundet. Starter med tom liste.");
            return users;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; //Man bruger "trim", til at fjerne alle mellemrum eller usynlige mellemrum, ved usertext, når vi prøver at scanne den.

                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    users.add(new User(username, password));
                }
            }
        } catch (IOException e) {
            System.out.println("Fejl ved indlæsning af brugere: " + e.getMessage());
        }

        return users;
    }

    public static void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User u : users) {
                pw.println(u.getUsername() + ";" + u.getPassword());
            }
        } catch (IOException e) {
            System.out.println("Fejl ved oprettelse af brugere: " + e.getMessage());
        }
    }

    public static User login(List<User> users, Scanner scanner) {
        System.out.print("Brugernavn: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)
                    && u.getPassword().equals(password)) {
                System.out.println("Logget ind som: " + username + ". Velkommen tilbage");
                return u;
            }
        }

        System.out.println("Forkert brugernavn eller password.");
        return null;
    }

    public static void createUser(List<User> users, Scanner scanner) {
        System.out.print("Vælg brugernavn: ");
        String username = scanner.nextLine();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Brugernavnet findes allerede.");
                return;
            }
        }

        System.out.print("Vælg password: ");
        String password = scanner.nextLine();

        users.add(new User(username, password));
        saveUsers(users);
        System.out.println("Bruger oprettet!");

    }

    public static void saveFavoriteMovie(User currentUser, String movieTitle) {
        File file = new File(FILE_NAME);
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2 && parts[0].equalsIgnoreCase(currentUser.getUsername())) {
                    // Tilføj film til brugerens linje
                    line += ";" + movieTitle;
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Fejl ved læsning: " + e.getMessage());
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String updatedLine : updatedLines) {
                pw.println(updatedLine);
            }
        } catch (IOException e) {
            System.out.println("Fejl ved skrivning: " + e.getMessage());
        }

        System.out.println(movieTitle + " er gemt som favorit for " + currentUser.getUsername());
    }

    public List<String> getFavoriteMovies() {
        List<String> favs = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts[0].equalsIgnoreCase(this.username)) {
                    for (int i = 2; i < parts.length; i++) { //i er blevet instanseret til 2, da idex 0 indeholder brugernavn og 1 med adgagnskode
                        favs.add(parts[i]);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Fejl: " + e.getMessage());
        }
        return favs;
    }
}

