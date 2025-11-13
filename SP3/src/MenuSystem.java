import java.util.List;
import java.util.Scanner;

public class MenuSystem {

    public static void option(User currentUser) {
        Scanner scanner = new Scanner(System.in);
        boolean option = true;

        while (option) {
            System.out.println("\n=== Menu ===");
            System.out.println("1) Movies");
            System.out.println("2) Series");
            System.out.println("3) Søg efter film");
            System.out.println("4) Favorites");
            System.out.println("0) Afslut");
            System.out.print("Vælg: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": {
                    MovieReader reader = new MovieReader();
                    reader.loadMovies("MovieData.csv");
                    List<Movie> movies = reader.getAllMovies();
                    MovieOption(movies, currentUser, true);
                    break;
                }
                case "2": {
                    SeriesReader seriesReader = new SeriesReader();
                    seriesReader.loadSeries("SeriesSeasons.csv");
                    List<Series> seriesList = seriesReader.getAllSeries();
                    SeriesOption(seriesList);
                    break;
                }
                case "3": {
                    showSearchMenu(currentUser, scanner);
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
                    System.out.println("Tryk Enter for at gå tilbage.");
                    scanner.nextLine();
                    break;
                }
                case "0":
                    System.out.println("Farvel!");
                    System.exit(0);
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    // ---------------------- SERIES ----------------------
    public static void SeriesOption(List<Series> seriesList) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Serie Bibliotek ===");
            for (int i = 0; i < seriesList.size(); i++) {
                System.out.println((i + 1) + ") " + seriesList.get(i).getInfo());
            }
            System.out.println("0) Tilbage");
            System.out.print("Vælg en serie: ");

            String input = scanner.nextLine();
            if (input.equals("0")) break;

            try {
                int seriesChoice = Integer.parseInt(input) - 1;
                if (seriesChoice < 0 || seriesChoice >= seriesList.size()) {
                    System.out.println("Ugyldigt valg.");
                    continue;
                }

                Series selectedSeries = seriesList.get(seriesChoice);
                List<Season> seasons = selectedSeries.getSeasons();
                if (seasons.isEmpty()) {
                    System.out.println("Ingen sæsoner tilgængelige.");
                    continue;
                }

                System.out.println("\nVælg sæson:");
                for (int j = 0; j < seasons.size(); j++) {
                    System.out.println((j + 1) + ") " + seasons.get(j));
                }
                System.out.print("Sæson: ");
                int seasonChoice = Integer.parseInt(scanner.nextLine()) - 1;
                if (seasonChoice < 0 || seasonChoice >= seasons.size()) {
                    System.out.println("Ugyldigt valg af sæson.");
                    continue;
                }
                Season selectedSeason = seasons.get(seasonChoice);

                System.out.println("Vælg episode:");
                for (int ep = 1; ep <= selectedSeason.getEpisodes(); ep++) {
                    System.out.println(ep + ") Episode " + ep);
                }
                System.out.print("Episode: ");
                int episodeChoice = Integer.parseInt(scanner.nextLine());
                if (episodeChoice < 1 || episodeChoice > selectedSeason.getEpisodes()) {
                    System.out.println("Ugyldigt valg af episode.");
                    continue;
                }

                System.out.println("Nu spiller: " + selectedSeries.getTitle() +
                        " - Sæson " + selectedSeason.getNumber() + ", Episode " + episodeChoice);
                System.out.println("Tryk Enter for at gå tilbage.");
                scanner.nextLine();

            } catch (NumberFormatException e) {
                System.out.println("Indtast venligst et tal.");
            }
        }
    }

    // ---------------------- MOVIES ----------------------
    public static void MovieOption(List<Movie> movies, User currentUser, boolean allowSave) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Film Liste ===");
            for (int i = 0; i < movies.size(); i++) {
                System.out.println((i + 1) + ") " + movies.get(i).getInfo());
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
                    System.out.println("Ugyldigt valg.");
                    continue;
                }

                Movie movie = movies.get(choice);
                System.out.println("\n=== Movie Details ===");
                System.out.println(movie.getInfo());
                System.out.println(movie.getTitle() + " is playing now...");

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

    // ---------------------- SEARCH ----------------------
    public static void showSearchMenu(User currentUser, Scanner scanner) {
        boolean searchRunning = true;

        while (searchRunning) {
            System.out.println("\n=== Søg efter film ===");
            System.out.println("1) Søg efter titel");
            System.out.println("2) Søg efter kategori");
            System.out.println("0) Tilbage til hovedmenu");
            System.out.print("Vælg: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Search.searchMovieByTitle(currentUser, scanner);
                    break;
                case "2":
                    Search.searchMoviesByCategory(currentUser, scanner);
                    break;
                case "0":
                    searchRunning = false;
                    break;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }
}
