import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SeriesReader {
    private List<Series> allSeries = new ArrayList<>();

    public void loadSeries(String fileName) {
        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // spring header-linje over
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty()) continue;

                // Brug regex split ligesom før for at håndtere kommaer i citationstegn
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (parts.length < 4) continue;

                String title = parts[0].trim();
                int year = Integer.parseInt(parts[1].trim());
                String genre = parts[2].replace("\"", "").trim();
                double rating = Double.parseDouble(parts[3].trim());

                Series s = new Series(title, year, genre, rating);
                allSeries.add(s);
            }

            System.out.println("Loaded " + allSeries.size() + " series from " + fileName);
        } catch (IOException e) {
            System.out.println("Fejl ved indlæsning af seriedata: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Fejl ved parsing af tal i seriedata: " + e.getMessage());
        }
    }

    public List<Series> getAllSeries() {
        return allSeries;
    }
}
