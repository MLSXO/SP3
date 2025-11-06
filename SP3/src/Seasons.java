public class Seasons {
    private int number;
    private int episodes;

    public Seasons(int number, int episodes) {
        this.number = number;
        this.episodes = episodes;
    }


    public int getNumber() {
        return number;
    }

    public int getEpisodes() {
        return episodes;
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }


    public String getInfo() {
        return "Sæson " + number + " (" + episodes + " episoder)";
    }

    @Override
    public String toString() {
        return getInfo();
    }
}