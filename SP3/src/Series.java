import java.util.ArrayList;

public class Series extends Media {
    private ArrayList<Seasons> seasons;

    public Series(String title, int year, String category, double rating) {
        super(title, year, category, rating);
        this.seasons = new ArrayList<>();
    }


    public void addSeason(Seasons season) {
        seasons.add(season);
    }


    public ArrayList<Seasons> getSeasons() {
        return seasons;
    }


    public int getNumberOfSeasons() {
        return seasons.size();
    }

    @Override
    public String getInfo() {
        return "Serie: " + getTitle() + " (" + getYear() + ") - " + getCategory() +
                " | Rating: " + getRating() + " | Sæsoner: " + getNumberOfSeasons();
    }
}