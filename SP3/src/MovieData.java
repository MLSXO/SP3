import java.util.ArrayList;

public class MovieData {

    public static ArrayList<Movie> hentMovieListe() {
        ArrayList<Movie> movieListe = new ArrayList<>();
                    /*
                    Title - Årstal - Genre - Rating
                     */

        movieListe.add(new Movie("The Godfather", 1972, "Crime, Drama", 9.2));
        movieListe.add(new Movie("The Shawshank Redemption", 1994, "Drama", 9.3));
        movieListe.add(new Movie("Schindler's List", 1993, "Biography, Drama, History", 8.9));
        movieListe.add(new Movie("Raging Bull", 1980, "Biography, Drama, Sport", 8.2));
        movieListe.add(new Movie("Casablanca", 1942, "Drama, Romance, War", 8.5));
        movieListe.add(new Movie("Citizen Kane", 1941, "Drama, Mystery", 8.4));
        movieListe.add(new Movie("Gone With The Wind", 1939, "Drama, History, Romance", 8.2));
        movieListe.add(new Movie("The Wizard Of Oz", 1939, "Adventure, Family, Fantasy", 8.0));
        movieListe.add(new Movie("One Flew Over The Cuckoo's Nest", 1975, "Drama", 8.7));
        movieListe.add(new Movie("Lawrence Of Arabia", 1962, "Adventure, Biography, Drama", 8.3));
        movieListe.add(new Movie("Vertigo", 1958, "Mystery, Romance, Thriller", 8.3));
        movieListe.add(new Movie("Psycho", 1960, "Horror, Mystery, Thriller", 8.5));
        movieListe.add(new Movie("The Godfather Part II", 1974, "Crime, Drama", 9.0));
        movieListe.add(new Movie("On The Waterfront", 1954, "Crime, Drama, Thriller", 8.2));
        movieListe.add(new Movie("Sunset Boulevard", 1950, "Drama, Film-Noir", 8.4));
        movieListe.add(new Movie("Forrest Gump", 1994, "Drama, Romance", 8.8));
        movieListe.add(new Movie("12 Angry Men", 1957, "Crime, Drama", 8.9));
        movieListe.add(new Movie("Star Wars", 1977, "Action, Adventure, Family", 8.6));
        movieListe.add(new Movie("Titanic", 1997, "Drama, Romance", 7.8));
        movieListe.add(new Movie("Pulp Fiction", 1994, "Crime, Drama", 8.9));
        movieListe.add(new Movie("The Green Mile", 1999, "Crime, Drama, Fantasy", 8.5));
        movieListe.add(new Movie("Good Will Hunting", 1997, "Drama, Romance", 8.3));
        movieListe.add(new Movie("Goodfellas", 1990, "Crime, Drama", 8.7));
        movieListe.add(new Movie("Braveheart", 1995, "Biography, Drama, History", 8.4));
        movieListe.add(new Movie("Gladiator", 2000, "Action, Adventure, Drama", 8.5));
        movieListe.add(new Movie("The Lord Of The Rings - The Return Of The King", 2003, "Action, Adventure, Drama", 8.9));
        movieListe.add(new Movie("The Pianist", 2002, "Biography, Drama, Music", 8.5));
        movieListe.add(new Movie("The Great Dictator", 1940, "Comedy, Drama, War", 8.5));
        movieListe.add(new Movie("Rear Window", 1954, "Mystery, Thriller", 8.5));
        movieListe.add(new Movie("The Good, The Bad And The Ugly", 1966, "Western", 8.9));

        return movieListe;
    }
}
