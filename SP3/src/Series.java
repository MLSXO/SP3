public class Series {
    private String title;
    private int year;
    private String genre;
    private double rating;

    public Series(String title, int year, String genre, double rating) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }

    // Denne metode skal være til stede
    public String getInfo() {
        return title + " (" + year + ") - " + genre + " - Rating: " + rating;
    }
}
