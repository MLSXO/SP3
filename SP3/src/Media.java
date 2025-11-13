public abstract class Media {
    private String title;
    private int date;
    private String category;
    private double rating;

    public Media(String title, int date, String category, double rating) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.rating = rating;
    }

    public String getTitle() { return title; }
    public int getDate() { return date; }
    public String getCategory() { return category; }
    public double getRating() { return rating; }

    public abstract String getInfo();

    @Override
    public String toString() {
        String genreString = String.join(",", category);
        return title + "," + date + "," + rating + "," + genreString;
    }
}
