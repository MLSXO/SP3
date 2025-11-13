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
            System.out.println("Ingen brugerfil fundet. Starter med tom liste.");
            return users;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue; //Man bruger "trim", til at fjerne alle mellemrum eller usynlige mellemrum, ved usertext, når vi prøver at scanne den.

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

    public static void saveFavoriteMovie(User currentUser, Movie movie) {
        File file = new File(FILE_NAME);
        List<String> updatedLines = new ArrayList<>();
        boolean alreadyFavorite = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");

                if (parts.length >= 2 && parts[0].equalsIgnoreCase(currentUser.getUsername())) {
                    // Check if movie is already in favorites
                    for (int i = 2; i < parts.length; i++) {
                        if (parts[i].equals(movie.toString())) {
                            alreadyFavorite = true;
                            break;
                        }
                    }

                    if (!alreadyFavorite) {
                        line += ";" + movie.toString(); // add movie only if not already there
                    }
                }

                updatedLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found - " + e.getMessage());
            return;
        }

        // Write back updated lines
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
            System.out.println("Fejl: " + e.getMessage());
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
                    // Rebuild the line without the selected movie
                    StringBuilder newLine = new StringBuilder(parts[0] + ";" + parts[1]);

                    for (int i = 2; i < parts.length; i++) {
                        String[] movieParts = parts[i].split(",");
                        if (movieParts.length >= 2) {
                            String title = movieParts[0].trim();
                            int year = Integer.parseInt(movieParts[1].trim());

                            // Only keep movies that are NOT the one to remove
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

        // Write changes back to the file
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

