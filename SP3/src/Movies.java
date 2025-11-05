import java.io.*;
import java.util.ArrayList;

public class DataHandler {

    public static ArrayList<Movie> hentMovieListe() {
        ArrayList<Movie> movieListe = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("filmdata_SP3.txt"));
            String linje;

            while ((linje = reader.readLine()) != null) {
                // Splitter linjen på semikolon
                String[] data = linje.split(";");
                if (data.length == 4) {
                    String title = data[0].trim();
                    int year = Integer.parseInt(data[1].trim());
                    String category = data[2].trim();
                    double rating = Double.parseDouble(data[3].trim());

                    // Tilføjer filmen som Movie-objekt
                    movieListe.add(new Movie(title, year, category, rating));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Fejl ved indlæsning af filmdata: " + e.getMessage());
        }

        return movieListe;
    }
}
