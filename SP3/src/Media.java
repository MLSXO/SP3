public abstract class Media {
    private String title;
    private int year;
    private String category;
    private double rating;

    public Media(String title, int year, String category, double rating) {
        this.title = title;
        this.year = year;
        this.category = category;
        this.rating = rating;
    }

    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getCategory() { return category; }
    public double getRating() { return rating; }

    public abstract String getInfo();
}