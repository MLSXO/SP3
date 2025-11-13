import java.util.List;
import java.util.Scanner;

public class MenuSystem {

    public static void option(User user) {
        boolean optionActive = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hej " + user.getUsername() + ", velkommen til menuen!");

        while (optionActive) {
            System.out.println("\n=== Menu ===");
            System.out.println("1) Movies");
            System.out.println("2) Series");
            System.out.println("0) Afslut");
            System.out.print("Vælg: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    MovieReader reader = new MovieReader();
                    reader.loadMovies("MovieData.csv");
                    List<Movie> movies = reader.getAllMovies();

                    System.out.println("Film Bibliotek:");
                    int i = 1;
                    for (Movie m : movies) {
                        System.out.print(i + " ");
                        System.out.println(m.getInfo());
                        i++;
                    }

                    MovieOption(reader, user);
                    break;
                }
                case "2": {
                    SeriesReader seriesReader = new SeriesReader();
                    seriesReader.loadSeries("SeriesData.csv");
                    List<Series> seriesList = seriesReader.getAllSeries();

                    System.out.println("Serie Bibliotek:");
                    int i = 1;
                    for (Series s : seriesList) {
                        System.out.print(i + " ");
                        System.out.println(s.getInfo());
                        i++;
                    }

                    SeriesOption(seriesReader, user);
                    break;
                }
                case "0":
                    optionActive = false;
                    System.out.println("Program afsluttes. Farvel!");
                    break;
                default:
                    System.out.println("Ugyldigt valg! Prøv igen.");
            }
        }
    }

    public static void MovieOption(MovieReader reader, User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Vælg nummer på favoritfilm: ");
        String input = scanner.nextLine();

        try {
            int index = Integer.parseInt(input) - 1;
            List<Movie> movies = reader.getAllMovies();
            if (index >= 0 && index < movies.size()) {
                Movie selected = movies.get(index);
                System.out.println("Du valgte: " + selected.getInfo());

                User.saveFavoriteMovie(user, selected.getTitle());
            } else {
                System.out.println("Ugyldigt nummer.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Indtast venligst et tal.");
        }
    }

    public static void SeriesOption(SeriesReader reader, User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Vælg nummer på favoritserie: ");
        String input = scanner.nextLine();

        try {
            int index = Integer.parseInt(input) - 1;
            List<Series> seriesList = reader.getAllSeries();
            if (index >= 0 && index < seriesList.size()) {
                Series selected = seriesList.get(index);
                System.out.println("Du valgte: " + selected.getInfo());

                User.saveFavoriteMovie(user, selected.getTitle()); // gemmer også i samme users.txt
            } else {
                System.out.println("Ugyldigt nummer.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Indtast venligst et tal.");
        }
    }
}
