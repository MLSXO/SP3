import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieReader {
    public static List<Movie> loadMovies(String filePath) {
        List<Movie> movieList = new ArrayList<>();

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

        return movieList;
    }
}