import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SeriesReader {
    private List<Series> allSeries = new ArrayList<>();

    public void loadSeries(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // skip header
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length < 6) continue;

                String title = parts[0].trim();
                int year = Integer.parseInt(parts[1].trim());
                String genre = parts[2].trim();
                double rating = Double.parseDouble(parts[3].trim());
                int seasonNumber = Integer.parseInt(parts[4].trim());
                int episodes = Integer.parseInt(parts[5].trim());

                // Find eksisterende serie i listen
                Series series = null;
                for (Series s : allSeries) {
                    if (s.getTitle().equalsIgnoreCase(title)) {
                        series = s;
                        break;
                    }
                }

                // Hvis serien ikke findes, lav en ny
                if (series == null) {
                    series = new Series(title, year, genre, rating);
                    allSeries.add(series);
                }

                // Tilføj sæson til serien
                series.addSeason(new Season(seasonNumber, episodes));
            }

            System.out.println("Loaded " + allSeries.size() + " series from " + fileName);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading series data: " + e.getMessage());
        }
    }

    public List<Series> getAllSeries() {
        return allSeries;
    }
}
