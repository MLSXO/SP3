import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Java leder efter filen her: " + System.getProperty("user.dir"));
        // Her indlæser vi MovieData.csv og gemmer i en liste
        List<Movie> movies = MovieReader.loadMovies("MovieData.csv");

        // Print for at teste, at filen blev indlæst korrekt
        System.out.println("Film Bibliotek:");
        for (Movie m : movies) {
            System.out.println(m.getInfo());
        }

        // Eksempel på serier
        List<Series> seriesListe = SeriesData.hentSerieListe();
        System.out.println("\nSerie Bibliotek:");
        for (Series s : seriesListe) {
            System.out.println(s.getInfo());
        }
    }
}