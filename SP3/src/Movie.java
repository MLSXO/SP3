import java.util.List;

public class Movie extends Media {
    private List<String> genres;

    public Movie(String title, int date, List<String> genres, double rating) {
        super(title, date, String.join(", ", genres), rating); // gem genre som kategori
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }

    @Override
    public String getInfo() {
        return getTitle() + " (" + getDate() + ") - Genres: " + genres + " - Rating: " + getRating();
    }
}