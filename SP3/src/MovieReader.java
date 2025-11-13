import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieReader {

    private List<Movie> movieList = new ArrayList<>();

    // Load movies fra fil og gem i feltet movieList
    public void loadMovies(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // skip header

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                // Split på komma, men håndter at der kan være mellemrum efter komma
                String[] parts = line.split(",\\s*");

                if (parts.length < 4) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String title = parts[0].trim();
                int year;
                try {
                    year = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line with invalid year: " + line);
                    continue;
                }

                // Håndter genres der er separeret med semikolon
                String[] genreArray = parts[2].split(";");
                List<String> genres = new ArrayList<>();
                for (String g : genreArray) {
                    genres.add(g.trim());
                }

                double rating;
                try {
                    rating = Double.parseDouble(parts[3].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line with invalid rating: " + line);
                    continue;
                }

                movieList.add(new Movie(title, year, genres, rating));
            }

            System.out.println("Loaded " + movieList.size() + " movies from " + filePath);

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    // Hent alle film
    public List<Movie> getAllMovies() {
        return movieList;
    }

    // Hent film ud fra nummer (indeks i listen)
    public Movie getMovie(int number) {
        if (number >= 0 && number < movieList.size()) {
            return movieList.get(number);
        }
        return null;
    }

    // Søg efter film baseret på titel
    public List<Movie> searchMoviesByTitle(String searchTerm) {
        List<Movie> results = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();

        for (Movie movie : movieList) {
            if (movie.getTitle().toLowerCase().contains(lowerSearchTerm)) {
                results.add(movie);
            }
        }

        return results;
    }

    // Søg efter film baseret på genre
    public List<Movie> searchMoviesByGenre(String genre) {
        List<Movie> results = new ArrayList<>();
        String lowerGenre = genre.toLowerCase();

        for (Movie movie : movieList) {
            for (String movieGenre : movie.getGenres()) {
                if (movieGenre.toLowerCase().contains(lowerGenre)) {
                    results.add(movie);
                    break; // Undgå dubletter
                }
            }
        }

        return results;
    }

    // Hent alle unikke genrer
    public List<String> getAllGenres() {
        List<String> allGenres = new ArrayList<>();

        for (Movie movie : movieList) {
            for (String genre : movie.getGenres()) {
                String trimmedGenre = genre.trim();
                if (!allGenres.contains(trimmedGenre)) {
                    allGenres.add(trimmedGenre);
                }
            }
        }

        return allGenres;
    }

    // Legacy metode for compatibility
    public Movie loadFavorites(int favorite) {
        return getMovie(favorite);
    }
}