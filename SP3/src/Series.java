import java.util.ArrayList;

public class Series extends Media {
    private ArrayList<Season> seasons;

    public Series(String title, int date, String category, double rating) {
        super(title, date, category, rating);
        this.seasons = new ArrayList<>();
    }

    public void addSeason(Season season) {
        seasons.add(season);
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public int getNumberOfSeasons() {
        return seasons.size();
    }

    @Override
    public String getInfo() {
        return "Serie: " + getTitle() + " (" + getDate() + ") - " + getCategory() +
                " | Rating: " + getRating() + " | Sæsoner: " + getNumberOfSeasons();
    }
}