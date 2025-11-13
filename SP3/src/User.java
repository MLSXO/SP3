import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
            System.out.println("No usersfile found. Starting with empty list.");
            return users;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue; // Man bruger "trim", til at fjerne alle mellemrum eller usynlige mellemrum, ved usertext, når vi prøver at scanne den.

                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    users.add(new User(username, password));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load users: " + e.getMessage());
        }

        return users;
    }

    public static void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User u : users) {
                pw.println(u.getUsername() + ";" + u.getPassword());
            }
        } catch (IOException e) {
            System.out.println("Error in user registration: " + e.getMessage());
        }
    }

    public static User login(List<User> users, Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)
                    && u.getPassword().equals(password)) {
                System.out.println("Logged in as: " + username + ". Welcome back");
                return u;
            }
        }

        System.out.println("Wrong username or password.");
        return null;
    }

    public static void createUser(List<User> users, Scanner scanner) {
        System.out.print("Choose username: ");
        String username = scanner.nextLine();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already in use.");
                return;
            }
        }

        System.out.print("Choose password: ");
        String password = scanner.nextLine();

        users.add(new User(username, password));
        saveUsers(users);
        System.out.println("New user registred!");

    }

    public static void saveFavoriteMovie(User currentUser, Movie movie) {
        File file = new File(FILE_NAME);
        List<String> updatedLines = new ArrayList<>();
        boolean alreadyFavorite = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");

                if (parts.length >= 2 && parts[0].equalsIgnoreCase(currentUser.getUsername())) {
                    // Tjek om filmen allerede er i favoritter
                    for (int i = 2; i < parts.length; i++) {
                        if (parts[i].equals(movie.toString())) {
                            alreadyFavorite = true;
                            break;
                        }
                    }

                    if (!alreadyFavorite) {
                        line += ";" + movie.toString(); // tilføj film kun hvis den ikke allerede er der
                    }
                }

                updatedLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found - " + e.getMessage());
            return;
        }

        // Skriv opdaterede linjer tilbage
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String updatedLine : updatedLines) {
                pw.println(updatedLine);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return;
        }

        if (alreadyFavorite) {
            System.out.println(movie.getTitle() + " is already in favorites for " + currentUser.getUsername() + ".");
        } else {
            System.out.println(movie.getTitle() + " has been saved as a favorite for " + currentUser.getUsername() + ".");
        }
    }

    public List<Movie> getFavoriteMovies() {
        List<Movie> favs = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts[0].equalsIgnoreCase(this.username)) {
                    for (int i = 2; i < parts.length; i++) {
                        if (!parts[i].contains(",")) continue; // spring ugyldige entries over

                        String[] movieParts = parts[i].split(",");
                        if (movieParts.length < 4) continue; // tjek for korrekt format

                        String title = movieParts[0];
                        int date = Integer.parseInt(movieParts[1]);
                        double rating = Double.parseDouble(movieParts[2]);
                        List<String> genres = Arrays.asList(movieParts[3].split("-"));

                        favs.add(new Movie(title, date, genres, rating));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return favs;
    }
    public static void removeFavoriteMovie(User currentUser, Movie movieToRemove) {
        File file = new File(FILE_NAME);
        List<String> updatedLines = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");

                if (parts.length >= 2 && parts[0].equalsIgnoreCase(currentUser.getUsername())) {
                    // Genopbyg linjen uden den valgte film
                    StringBuilder newLine = new StringBuilder(parts[0] + ";" + parts[1]);

                    for (int i = 2; i < parts.length; i++) {
                        String[] movieParts = parts[i].split(",");
                        if (movieParts.length >= 2) {
                            String title = movieParts[0].trim();
                            int year = Integer.parseInt(movieParts[1].trim());

                            // Behold kun film som IKKE er den der skal fjernes
                            if (!(title.equalsIgnoreCase(movieToRemove.getTitle()) && year == movieToRemove.getDate())) {
                                newLine.append(";").append(parts[i]);
                            }
                        }
                    }

                    updatedLines.add(newLine.toString());
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        }

        // Skriv ændringer tilbage til filen
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String updatedLine : updatedLines) {
                pw.println(updatedLine);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return;
        }

        System.out.println(movieToRemove.getTitle() + " has been removed from your favorites.");
    }
}