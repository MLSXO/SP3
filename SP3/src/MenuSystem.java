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
                    // Henter serier fra SeriesData.csv via SeriesReader
                    SeriesReader seriesReader = new SeriesReader();
                    seriesReader.loadSeries("SeriesData.csv"); // sørg for at filen ligger korrekt
                    List<Series> seriesListe = seriesReader.getAllSeries();

                    System.out.println("\n=== Serie Bibliotek ===");
                    int i = 1;
                    for (Series s : seriesListe) {
                        System.out.print(i + ") ");
                        System.out.println(s.getInfo());
                        i++;
                    }
                    break;
                }
                case "4": {
                    List<Movie> favs = currentUser.getFavoriteMovies();

                    if (favs.isEmpty()) {
                        System.out.println("Du har ingen favoritfilm endnu.");
                        break;
                    }

                    System.out.println("\n=== Dine Favoritfilm ===");
                    int i = 1;
                    for (Movie m : favs) {
                        System.out.println(i + ") " + m.getTitle() + " (" + m.getDate() + ")");
                        i++;
                    }
                    System.out.println("Type number to see the movie, or Type number + R to remove the movie from favorite list. 0 for going back:");
                    System.out.print("Choose: ");

                    String input = scanner.nextLine().trim();

                    if (input.equals("0")) break;

                    try {
                        boolean remove = input.toLowerCase().endsWith("r"); // tjek om input slutter med 'm'
                        int removeMovie = Integer.parseInt(remove ? input.substring(0, input.length() - 1) : input) - 1;

                        if (removeMovie >= 0 && removeMovie < favs.size()) {
                            Movie selected = favs.get(removeMovie);

                            if (remove) {
                                User.removeFavoriteMovie(currentUser, selected);
                            } else {
                                // brug MovieOption til at vise info og evt. play
                                MenuSystem.MovieOptionFromFavorites(selected, currentUser);
                            }
                        } else {
                            System.out.println("Ugyldigt valg.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Indtast et gyldigt tal.");
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


                if (allowSave) { //Hvis metoden bliver kald med en sand aloowSave, vil den spørge om man vil adde den til favorites.
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

    public static void MovieOptionFromFavorites(Movie movie, User currentUser) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Movie Details ===");
        System.out.println(movie.getInfo());
        System.out.println(movie.getTitle() + " is playing now...");


        System.out.println("Press Enter to go back to menu..");
        scanner.nextLine();
    }
    public static void SeriesOption(List<Series> series, User currentUser, boolean allowSave) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Film Liste ===");
            int i = 1;
            for (Series m : series) {
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
                if (choice < 0 || choice >= series.size()) {
                    System.out.println("Ugyldigt valg. Prøv igen.");
                    continue;
                }

                Series serie = series.get(choice);
                System.out.println("\n=== Movie Details ===");
                System.out.println(serie.getInfo());
                System.out.println(serie.getTitle() + " is playing now...");

            } catch (NumberFormatException e) {
                System.out.println("Indtast venligst et tal.");
            }
        }
    }
}
