import java.util.ArrayList;
import java.util.List;

public class Series {
    private String title;
    private int year;
    private String genre;
    private double rating;
    private List<Season> seasons;

    public Series(String title, int year, String genre, double rating) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.seasons = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void addSeason(Season season) {
        this.seasons.add(season);
    }

    public String getInfo() {
        return title + " (" + year + ") - Rating: " + rating;
    }
}
