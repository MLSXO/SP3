import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class MenuSystem {

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

                    System.out.println("Film Bibliotek:");
                    int i = 1;
                    for (Movie m : movies) {
                        System.out.print(i + " ");
                        System.out.println(m.getInfo());
                        i++;
                    }

                    MovieOption(reader, currentUser); //kalder på MovieOption

                    option = false;
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
                    List<String> favs = currentUser.getFavoriteMovies();
                    System.out.println("Dine favoritfilm:");
                    for (String f : favs) {
                        System.out.println("- " + f);
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

    public static void MovieOption(MovieReader reader, User currentUser) {
        Scanner scanner = new Scanner(System.in);
        boolean MovieOption = true;
        while (MovieOption) {
            System.out.println("\n=== Movie Option ===");
            System.out.println("type the number from the list you wonna see?");
            System.out.print("Vælg: ");

            String input = scanner.nextLine();

            int movieNumber = Integer.parseInt(input) - 1; //-1 fordi index starter med 0
            Movie movie = reader.getMovie(movieNumber);
            System.out.println("\n=== Movie Details ===");
            System.out.println(movie.getInfo());
            System.out.println(movie.getTitle() + " is playing now...");


            saveMoviesToFavorites(currentUser,movie);
            System.exit(0);
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
                User.saveFavoriteMovie(user, movie.getTitle());
                break;
            }

            case ("N"): {
                System.out.println("Fair nok");

                break;
            }

        }
    }
}
