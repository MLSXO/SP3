import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class MenuSystem {
    private int movieId;

    public static void option(User currentUser) {
        boolean option = true;
        Scanner scanner = new Scanner(System.in);
        while (option) {
            System.out.println("\n=== Menu ===");
            System.out.println("1) Movies");
            System.out.println("2) Series");
            System.out.println("4) Favorites");
            System.out.println("0) Afslut");
            System.out.print("Vælg: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    MovieReader reader = new MovieReader();       // opretter reader-objekt
                    reader.loadMovies("MovieData.csv");           // loader movies til reader
                    List<Movie> movies = reader.getAllMovies();   // henter listen fra reader

                    MovieOption(movies, currentUser, true);
                    break;
                }
                case "2": {
                    // printer alle series ud
                    List<Series> seriesListe = SeriesData.hentSerieListe();
                    System.out.println("\nSerie Bibliotek:");
                    int i = 1;
                    for (Series s : seriesListe) {
                        System.out.print(i + " ");
                        System.out.println(s.getInfo());
                        i++;
                    }
                    option = false;
                    break;

                }
                case "4": {
                    List<Movie> favs = currentUser.getFavoriteMovies();

                    if (favs.isEmpty()) {
                        System.out.println("Du har ingen favoritfilm endnu.");
                    } else {
                        MovieOption(favs, currentUser, false); // her kan man ikke tilade at gemme, da allowSave er falsk
                    }
                    break;
                }


                case "0": {
                    System.out.println("Farvel!");
                    System.exit(0);

                }
            }
        }
    }

    public static void MovieOption(List<Movie> movies, User currentUser, boolean allowSave) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Film Liste ===");
            int i = 1;
            for (Movie m : movies) {
                System.out.println(i + ") " + m.getInfo());
                i++;
            }
            System.out.println("0) Tilbage");
            System.out.print("Vælg: ");

            String input = scanner.nextLine();

            if (input.equals("0")) {
                running = false;
                break;
            }

            try {
                int choice = Integer.parseInt(input) - 1;
                if (choice < 0 || choice >= movies.size()) {
                    System.out.println("Ugyldigt valg. Prøv igen.");
                    continue;
                }

                Movie movie = movies.get(choice);
                System.out.println("\n=== Movie Details ===");
                System.out.println(movie.getInfo());
                System.out.println(movie.getTitle() + " is playing now...");

                // 👇 kun tilbyd at gemme hvis vi er i almindelig "Movies"-visning
                if (allowSave) {
                    System.out.println("\nVil du gemme denne som favorit? (Y/N)");
                    String saveChoice = scanner.nextLine();
                    if (saveChoice.equalsIgnoreCase("Y")) {
                        User.saveFavoriteMovie(currentUser, movie);
                        System.out.println(movie.getTitle() + " er gemt som favorit.");
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Indtast venligst et tal.");
            }
        }

    }

    public static void saveMoviesToFavorites(User user, Movie movie) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Wanna save this to favorite movies? ===");
        System.out.println("Y/N?");
        System.out.print("Vælg: ");

        String input = scanner.nextLine();
        switch (input) {
            case ("Y"): {
                User.saveFavoriteMovie(user, movie);
                System.out.print(movie.getTitle() + " is saved to your favorite movies");
                break;
            }

            case ("N"): {
                System.out.println("Fair nok");

                break;
            }

        }
    }
}
