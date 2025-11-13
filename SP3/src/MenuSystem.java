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
            System.out.println("3) Search for Movie");
            System.out.println("4) Favorites");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

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
                        System.out.println("You don't have any favorite movies.");
                        break;
                    }
                    System.out.println("\n=== Your FavoriteMovies ===");
                    int i = 1;
                    for (Movie m : favs) {
                        System.out.println(i + ") " + m.getTitle() + " (" + m.getDate() + ")");
                        i++;
                    }
                    System.out.println("Press Enter to go back.");
                    scanner.nextLine();
                    break;
                }
                case "0":
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }


    public static void SeriesOption(List<Series> seriesList) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Series Library ===");
            for (int i = 0; i < seriesList.size(); i++) {
                System.out.println((i + 1) + ") " + seriesList.get(i).getInfo());
            }
            System.out.println("0) Return");
            System.out.print("Pick a series: ");

            String input = scanner.nextLine();
            if (input.equals("0")) break;

            try {
                int seriesChoice = Integer.parseInt(input) - 1;
                if (seriesChoice < 0 || seriesChoice >= seriesList.size()) {
                    System.out.println("Invalid choice.");
                    continue;
                }

                Series selectedSeries = seriesList.get(seriesChoice);
                List<Season> seasons = selectedSeries.getSeasons();
                if (seasons.isEmpty()) {
                    System.out.println("No seasons available.");
                    continue;
                }

                System.out.println("\nSelect season:");
                for (int j = 0; j < seasons.size(); j++) {
                    System.out.println((j + 1) + ") " + seasons.get(j));
                }
                System.out.print("Season: ");
                int seasonChoice = Integer.parseInt(scanner.nextLine()) - 1;
                if (seasonChoice < 0 || seasonChoice >= seasons.size()) {
                    System.out.println("Invalid season selection.");
                    continue;
                }
                Season selectedSeason = seasons.get(seasonChoice);

                System.out.println("Choose episode:");
                for (int ep = 1; ep <= selectedSeason.getEpisodes(); ep++) {
                    System.out.println(ep + ") Episode " + ep);
                }
                System.out.print("Episode: ");
                int episodeChoice = Integer.parseInt(scanner.nextLine());
                if (episodeChoice < 1 || episodeChoice > selectedSeason.getEpisodes()) {
                    System.out.println("Invalid season selection.");
                    continue;
                }

                System.out.println("Now playing: " + selectedSeries.getTitle() +
                        " - Season " + selectedSeason.getNumber() + ", Episode " + episodeChoice);
                System.out.println("Press Enter to go back.");
                scanner.nextLine();

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }


    public static void MovieOption(List<Movie> movies, User currentUser, boolean allowSave) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Movie List ===");
            for (int i = 0; i < movies.size(); i++) {
                System.out.println((i + 1) + ") " + movies.get(i).getInfo());
            }
            System.out.println("0) Return");
            System.out.print("Choose: ");

            String input = scanner.nextLine();
            if (input.equals("0")) {
                running = false;
                break;
            }

            try {
                int choice = Integer.parseInt(input) - 1;
                if (choice < 0 || choice >= movies.size()) {
                    System.out.println("Invalid choice");
                    continue;
                }

                Movie movie = movies.get(choice);
                System.out.println("\n=== Movie Details ===");
                System.out.println(movie.getInfo());
                System.out.println(movie.getTitle() + " is playing now...");

                if (allowSave) {
                    System.out.println("\nDo you want to save this as a favorite? (Y/N)");
                    String saveChoice = scanner.nextLine();
                    if (saveChoice.equalsIgnoreCase("Y")) {
                        User.saveFavoriteMovie(currentUser, movie);
                        System.out.println(movie.getTitle() + " is saved as a favorite.");
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }


    public static void showSearchMenu(User currentUser, Scanner scanner) {
        boolean searchRunning = true;

        while (searchRunning) {
            System.out.println("\n=== Search for movies ===");
            System.out.println("1) Search for title");
            System.out.println("2) Search for category");
            System.out.println("0) Return to main menu");
            System.out.print("Choose: ");

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
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
