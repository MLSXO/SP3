public class Season {
    private int number;
    private int episodes;

    public Season(int number, int episodes) {
        this.number = number;
        this.episodes = episodes;
    }

    public int getNumber() { return number; }
    public int getEpisodes() { return episodes; }

    @Override
    public String toString() {
        return "Sæson " + number + " (" + episodes + " episoder)";
    }
}