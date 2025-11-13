import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SeriesReader {
    private List<Series> seriesList = new ArrayList<>();

    public void loadSeries(String filename) {
        seriesList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true; // spring header over
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; } // skip header

                line = line.trim();
                if (line.isEmpty()) continue;

                // Split på kommaer, men ignorer kommaer indenfor citationstegn
                String[] parts = parseCSVLine(line);
                if (parts.length >= 4) {
                    String title = parts[0];
                    int year = Integer.parseInt(parts[1]);
                    String genre = parts[2];
                    double rating = Double.parseDouble(parts[3]);
                    seriesList.add(new Series(title, year, genre, rating));
                }
            }
        } catch (IOException e) {
            System.out.println("Fejl ved læsning af series: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Fejl i filformat: " + e.getMessage());
        }
    }

    public List<Series> getAllSeries() {
        return seriesList;
    }

    // Simpel CSV-parser, håndterer citationstegn
    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes; // skift status
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString().trim());
        return result.toArray(new String[0]);
    }
}
