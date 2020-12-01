package actor;

import actor.ActorsAwards;
import data.Data;
import entertainment.Show;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Information about an actor
 */
public class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private List<Show> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    public Actor(final String name, final String careerDescription,
                          final List<Show> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Show> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<Show> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public Double getAvgRating() {
        if (filmography.isEmpty()) {
            return -1d;
        }
        Double totalRatings = 0d;
        int numberOfRatings = 0;

        for (Show show:filmography) {
            Double currentRating = show.getAverageRating();
            if (currentRating != 0d) {
                totalRatings += currentRating;
                numberOfRatings++;
            }
        }

        if (numberOfRatings == 0)
            return -1d;
        return totalRatings / numberOfRatings;
    }

    public int getNumOfAwards() {
        int totalNum = 0;
        for(Map.Entry<ActorsAwards, Integer> award : awards.entrySet()) {
            totalNum += award.getValue();
        }

          return totalNum;
    }

    @Override
    public String toString() {
        return "Actor{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
