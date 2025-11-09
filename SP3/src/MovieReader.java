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
                String[] parts = scanner.nextLine().split(",");

                String title = parts[0];
                int year = Integer.parseInt(parts[1]);

                String[] genreArray = parts[2].split(";");
                List<String> genres = new ArrayList<>();
                for (String g : genreArray) genres.add(g.trim());

                double rating = Double.parseDouble(parts[3]);

                movieList.add(new Movie(title, year, genres, rating));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    // Hent film ud fra nummer (indeks i listen)

    public List<Movie> getAllMovies() {
        return movieList;
    }

    public Movie getMovie(int number) { //Her henter den, den film du vælger, når den spørg dig hvilken film du gerne vil se
        return movieList.get(number);
    }

}
