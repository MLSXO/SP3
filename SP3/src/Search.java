import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {

    public static void searchMovieByTitle(User currentUser, Scanner scanner) {
        System.out.print("Enter Movie title ");
        String searchTerm = scanner.nextLine().trim();
        if (searchTerm.isEmpty()) {
            System.out.println(".");
            return;
        }

        MovieReader reader = new MovieReader();
        reader.loadMovies("MovieData.csv");
        List<Movie> allMovies = reader.getAllMovies();

        List<Movie> results = new ArrayList<>();
        for (Movie m : allMovies) {
            if (m.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(m);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No movie found with this title " + searchTerm);
        } else {
            System.out.println("\nFound " + results.size() + " Movie:");
            MenuSystem.MovieOption(results, currentUser, true);
        }
    }

    public static void searchMoviesByCategory(User currentUser, Scanner scanner) {
        MovieReader reader = new MovieReader();
        reader.loadMovies("MovieData.csv");
        List<Movie> allMovies = reader.getAllMovies();

        List<String> genres = new ArrayList<>();
        for (Movie m : allMovies) {
            for (String g : m.getGenres()) {
                if (!genres.contains(g)) genres.add(g);
            }
        }

        System.out.println("\nAvailable categories:");
        for (int i = 0; i < genres.size(); i++) {
            System.out.println((i + 1) + ") " + genres.get(i));
        }

        System.out.print("Select category number or write name: ");
        String input = scanner.nextLine().trim();

        String selectedGenre = null;
        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < genres.size()) selectedGenre = genres.get(index);
        } catch (NumberFormatException e) {
            for (String g : genres) {
                if (g.equalsIgnoreCase(input)) selectedGenre = g;
            }
        }

        if (selectedGenre == null) {
            System.out.println("No category found: " + input);
            return;
        }

        List<Movie> results = new ArrayList<>();
        for (Movie m : allMovies) {
            for (String g : m.getGenres()) {
                if (g.equalsIgnoreCase(selectedGenre)) results.add(m);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No movies found in category: " + selectedGenre);
        } else {
            System.out.println("\nMovies in category'" + selectedGenre + "':");
            MenuSystem.MovieOption(results, currentUser, true);
        }
    }
}
