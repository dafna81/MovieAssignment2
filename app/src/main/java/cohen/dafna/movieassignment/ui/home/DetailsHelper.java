package cohen.dafna.movieassignment.ui.home;

public class DetailsHelper {
    private static DetailsHelper instance;
    private String movieTitle;

    private DetailsHelper() {
    }

    public DetailsHelper(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public static DetailsHelper getInstance() {
        if (instance == null) {
            instance = new DetailsHelper();
        }
        return instance;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

}
