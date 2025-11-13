import java.util.ArrayList;
import java.util.List;

public class Season {
    private int number;
    private int episodes;

    public Season(int number, int episodes) {
        this.number = number;
        this.episodes = episodes;
    }

    public int getNumber() {
        return number;
    }

    public int getEpisodes() {
        return episodes;
    }

    public List<Integer> getEpisodeList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= episodes; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public String toString() {
        return "Season " + number + " (" + episodes + " Episodes)";
    }
}
