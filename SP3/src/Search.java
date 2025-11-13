import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {

    public static void searchMovieByTitle(User currentUser, Scanner scanner) {
        System.out.print("Indtast filmtitel (eller del af titel): ");
        String searchTerm = scanner.nextLine().trim();
        if (searchTerm.isEmpty()) {
            System.out.println("Søgeterm kan ikke være tom.");
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
            System.out.println("Ingen film fundet med titlen: " + searchTerm);
        } else {
            System.out.println("\nFandt " + results.size() + " film:");
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

        System.out.println("\nTilgængelige kategorier:");
        for (int i = 0; i < genres.size(); i++) {
            System.out.println((i + 1) + ") " + genres.get(i));
        }

        System.out.print("Vælg kategori nummer eller skriv navn: ");
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
            System.out.println("Ingen kategori fundet: " + input);
            return;
        }

        List<Movie> results = new ArrayList<>();
        for (Movie m : allMovies) {
            for (String g : m.getGenres()) {
                if (g.equalsIgnoreCase(selectedGenre)) results.add(m);
            }
        }

        if (results.isEmpty()) {
            System.out.println("Ingen film fundet i kategorien: " + selectedGenre);
        } else {
            System.out.println("\nFilm i kategorien '" + selectedGenre + "':");
            MenuSystem.MovieOption(results, currentUser, true);
        }
    }
}
